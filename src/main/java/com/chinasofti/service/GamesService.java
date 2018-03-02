package com.chinasofti.service;

import com.chinasofti.model.GamesPO;
import com.chinasofti.model.PrizePO;
import com.chinasofti.model.RedpacketPO;

public interface GamesService {
	
	GamesPO saveSetting(GamesPO games) throws Exception;
	
	GamesPO updateSetting(GamesPO games) throws Exception;
	
	int insertGames(GamesPO games) throws Exception;
	
	int insertAward(PrizePO award) throws Exception;
	
	int insertRedPacket(RedpacketPO redPacket) throws Exception;
	
	int updateGamesStartTime(int gameId) throws Exception ;
	
	int updateGames(GamesPO games) throws Exception;
	
	GamesPO getGamesById(int gameId) throws Exception;
	
	RedpacketPO getRedpacketByGameId(int gameId) throws Exception;
	
}

