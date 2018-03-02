package com.chinasofti.service.Impl;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.chinasofti.exception.ValidateException;
import com.chinasofti.model.GameConstants;
import com.chinasofti.model.GamesPO;
import com.chinasofti.model.PrizePO;
import com.chinasofti.model.RedpacketPO;
import com.chinasofti.service.GamesService;
import com.chinasofti.util.RandomUtil;


@Service
public class GamesServiceImpl implements GamesService {

	private static Logger log = Logger.getLogger(GamesServiceImpl.class);

	private static final int num = 4;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public GamesPO saveSetting(GamesPO games) throws Exception {

		String fourRoomNo = getRoomNo();
		games.setRoomNo(fourRoomNo);

		String gameType = games.getGameType();
		int gameId = insertGames(games);// return game PKID

		if (StringUtils.equalsIgnoreCase(gameType, GameConstants.GAMETYPE_PRIZE)) {
			List<PrizePO> prizePOList = games.getPrizePOList();
			insertPrizeForBatch(prizePOList, gameId);
		} else {
			// 红包
			games.getRedpacket().setGameId(gameId);
			insertRedPacket(games.getRedpacket());
		}
		return games;
	}

	@Override
	public GamesPO updateSetting(GamesPO parGames) throws Exception {
		Integer gameId = parGames.getId();
		GamesPO dbGamesPO = getGamesById(gameId);
		if(dbGamesPO ==null){
			throw new ValidateException("update method:not found games by id");
		}
		if(dbGamesPO.getStartTime() != null){
			throw new ValidateException("update method:The game has started, can not be modified");
		}
		
		String gameType = parGames.getGameType();
		if(StringUtils.isNotBlank(gameType)){
			dbGamesPO.setGameType(gameType);
		}
		//update games
		updateGames(dbGamesPO);
		
		if(StringUtils.equalsIgnoreCase(gameType,GameConstants.GAMETYPE_PRIZE)){
			//奖品
			delePrizeByGameId(gameId);
			insertPrizeForBatch(parGames.getPrizePOList(),gameId);
		}else{
			//红包
			RedpacketPO dbRedpacketPO = getRedpacketByGameId(gameId);
			if(dbRedpacketPO==null){
				throw new ValidateException("not found Redpacket By Gameid");
			}
			//平分和随机更新的参数不一样
			RedpacketPO parRedpacket = parGames.getRedpacket();
			String type = parRedpacket.getType();
			if(StringUtils.equalsIgnoreCase(type, GameConstants.GAMETYPE_MONEY_AVG)){
				updateRedPackageForAVGByGameId(parRedpacket);
			}else{
				updateRedPackageForRandomByGameId(parRedpacket);
			}
		}
		return parGames;
	}
	

	private String getRoomNo() throws Exception {
		return RandomUtil.getRandoms(num);
	}

	@Override
	public int insertGames(final GamesPO games) throws Exception {

		final String sql = "insert into ls_games(roomNo,gameType,createTime) values(?,?,now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, games.getRoomNo());
				ps.setString(2, games.getGameType());
				return ps;
			}
		}, keyHolder);

		log.info("return games PKid  " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();

	}

	@Override
	public int insertRedPacket(RedpacketPO redPacket) throws Exception {
		String sql = "insert into ls_redpacket(gameId,number,type,amount,minAmount,createTime) values(?,?,?,?,?,now())";
		return jdbcTemplate.update(sql, new Object[] { redPacket.getGameId(), redPacket.getNumber(),
				redPacket.getType(), redPacket.getAmount(), redPacket.getMinAmount() });
	}

	@Override
	public int insertAward(PrizePO prize) throws Exception {
		String sql = "insert into ls_prize(gameId,prizeItem,prizeName,count,createTime) values(?,?,?,?,now())";
		return jdbcTemplate.update(sql,
				new Object[] { prize.getGameId(), prize.getPrizeItem(), prize.getPrizeName(), prize.getCount() });
	}

	public void insertPrizeForBatch(final List<PrizePO> prizelist, final int gameId) throws Exception {
		String sql = "insert into ls_prize(gameId,prizeItem,prizeName,count,createTime) values(?,?,?,?,now())";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public int getBatchSize() {
				return prizelist.size();
			}

			@Override
			public void setValues(PreparedStatement stm, int index) throws SQLException {
				PrizePO prizePO = prizelist.get(index);
				if (prizePO != null) {
					stm.setInt(1, gameId);
					stm.setString(2, prizePO.getPrizeItem());
					stm.setString(3, prizePO.getPrizeName());
					stm.setInt(4, prizePO.getCount());
				}
			}
		});
	}

	@Override
	public int updateGamesStartTime(int gameId) throws Exception {
		final String sql = "update  ls_games set startTime = now() where id = ? ";
		return jdbcTemplate.update(sql, new Object[] { gameId });
	}

	@Override
	public int updateGames(GamesPO games) throws Exception {
		final String sql = "update ls_games set gameType = ?, lastUpdateTime = now() where id = ? ";
		return jdbcTemplate.update(sql, new Object[] { games.getGameType(), games.getId() });
	}

	@Override
	public GamesPO getGamesById(int gameId) throws Exception {
		RowMapper<GamesPO> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GamesPO.class);
		final String sql = "SELECT id,roomNo,gameType,startTime from ls_games where id = ?";
		List<GamesPO> list = this.jdbcTemplate.query(sql, new Object[] { gameId }, rowMapper);
		GamesPO gamesPO = null;
		if (list != null && list.size() > 0) {
			gamesPO = list.get(0);
		}
		return gamesPO;

	}
	
	@Override
	public RedpacketPO getRedpacketByGameId(int gameId) throws Exception {
		RowMapper<RedpacketPO> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(RedpacketPO.class);
		final String sql = "select id,gameid,number,type,minAmount,amount from ls_redpacket WHERE gameId = ?";
		List<RedpacketPO> list = this.jdbcTemplate.query(sql, new Object[] { gameId }, rowMapper);
		RedpacketPO redpacketPO = null;
		if (list != null && list.size() > 0) {
			redpacketPO = list.get(0);
		}
		return redpacketPO;
	}

	public int delePrizeByGameId(int gameId) throws Exception {
		final String sql = "delete from ls_prize where gameId =? ";
		return jdbcTemplate.update(sql, new Object[] { gameId });
	}

	public int updateRedPackageForAVGByGameId(RedpacketPO redpacket) throws Exception {
		final String sql = "update ls_redpacket set amount = ?, number = ?, type = ?,lastUpdateTime = now() where gameId = ?";
		return jdbcTemplate.update(sql, new Object[] {redpacket.getAmount(), redpacket.getNumber(), redpacket.getType(), redpacket.getGameId() });
	}
	
	public int updateRedPackageForRandomByGameId(RedpacketPO redpacket) throws Exception {
		final String sql = "update ls_redpacket set amount = ?, number = ?, type = ?,minAmount = ? , lastUpdateTime = now() where gameId =?";
		return jdbcTemplate.update(sql, new Object[] {redpacket.getAmount(), redpacket.getNumber(), redpacket.getType(),redpacket.getMinAmount(), redpacket.getGameId() });
	}
	
	public static void main(String[] args) {
		double amount =10;
		int count=7;
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.FLOOR);
		String a = df.format(amount/count);
		System.out.println(a);
		System.out.println();
	}
	
}
