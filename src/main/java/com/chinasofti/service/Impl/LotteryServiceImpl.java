package com.chinasofti.service.Impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinasofti.service.LotteryService;
@Service
public class LotteryServiceImpl implements LotteryService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(LotteryServiceImpl.class);
	// 放大倍数
	private static final int mulriple = 1000000;
	@Override
	public Map join(String staffId,String roomNo) {
		Map gift = playGame(roomNo);
		if (null != gift) {

			this.jdbcTemplate.update("INSERT INTO ls_gift_winner(staffId,money,roomNo) VALUES(?,?,?)",
					new Object[] { staffId, gift.get("money"),roomNo });
			gift.remove("version");
			gift.remove("gid");

		}
		return gift;
		
	}

	

	public synchronized Map playGame(String roomNo) {
		String sql = "SELECT g.gid,g.probability,g.count,g.version,g.money from ls_gift g ,ls_room r WHERE r.id=g.roomId and r.roomNo=? and g.status=1 and g.count>0";
		List<Map<String, Object>> gifts = this.jdbcTemplate.queryForList(sql,roomNo);

		int lastScope = 0;
		// 洗牌，打乱奖品次序
		Collections.shuffle(gifts);
		Map<Integer, int[]> prizeScopes = new HashMap<Integer, int[]>();
		Map<Integer, Integer> prizeQuantity = new HashMap<Integer, Integer>();
		Map<Integer, Map> data = new HashMap<Integer, Map>();
		for (Map map : gifts) {
			int prizeId = Integer.valueOf(map.get("gid").toString());
			data.put(prizeId, map);
			// 划分区间
			int currentScope = lastScope
					+ new BigDecimal(map.get("probability").toString()).multiply(new BigDecimal(mulriple)).intValue();
			prizeScopes.put(prizeId, new int[] { lastScope + 1, currentScope });
			prizeQuantity.put(prizeId, Integer.valueOf(map.get("count").toString()));
			lastScope = currentScope;
		}
		// 获取1-1000000之间的一个随机数
		int luckyNumber = new Random().nextInt(mulriple);
		int luckyPrizeId = 0;
		// 查找随机数所在的区间
		if ((null != prizeScopes) && !prizeScopes.isEmpty()) {
			Set<Entry<Integer, int[]>> entrySets = prizeScopes.entrySet();
			for (Map.Entry<Integer, int[]> m : entrySets) {
				int key = m.getKey();
				if (luckyNumber >= m.getValue()[0] && luckyNumber <= m.getValue()[1] && prizeQuantity.get(key) > 0) {
					luckyPrizeId = key;
					break;
				}
			}
		}
		Map mapgift = null;
	if (luckyPrizeId > 0 && luckyPrizeId <1000) {
		
			// 奖品库存减一

			Map result = data.get(luckyPrizeId);
			if (null != result) {
				logger.info("luckyPrizeId:" + luckyPrizeId + "Gift:" + result);
				int status = this.jdbcTemplate.update(
						"update ls_gift set count=count-1,version=version+1 where gid=? and version=?",
						new Object[] { luckyPrizeId, Integer.valueOf(result.get("version").toString()) });
				if (status > 0) {
					mapgift = result;
					result.remove("probability");
					result.remove("count");
				}
				
			}
		}
		return mapgift;
	}
}
