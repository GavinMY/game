package com.chinasofti.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chinasofti.model.GameConstants;
import com.chinasofti.model.GamesPO;
import com.chinasofti.model.PrizePO;
import com.chinasofti.model.RedpacketPO;
import com.chinasofti.service.GamesService;
import com.chinasofti.util.RandomUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-servlet.xml","classpath:spring/applicationContext-core.xml"})
public class TestGames {
	
	@Autowired
	private GamesService gamesService;

	@Test
	public void TestSaveRadPackageSetting(){
		try {
			String roomNo = RandomUtil.getRandoms(4);
			
			GamesPO gamesPO=new GamesPO();
			gamesPO.setRoomNo(roomNo);
			gamesPO.setGameType(GameConstants.GAMETYPE_MONEY);
			
			RedpacketPO redpacketPO=new RedpacketPO();
			redpacketPO.setAmount(100);
			redpacketPO.setNumber(10);
			redpacketPO.setType(GameConstants.GAMETYPE_MONEY_AVG);//平分
			gamesPO.setRedpacket(redpacketPO);
			
			//redpacketPO.setType(GPConstants.GAMETYPE_MONEY_RANDOM);//随机
			//redpacketPO.setMinAmount(5);
			
			gamesService.saveSetting(gamesPO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//奖品
	@Test
	public void TestSavePrizeSetting(){
		try {
			String roomNo = RandomUtil.getRandoms(4);
			
			GamesPO gamesPO=new GamesPO();
			gamesPO.setRoomNo(roomNo);
			gamesPO.setGameType(GameConstants.GAMETYPE_PRIZE);
			
			List<PrizePO> prizePOList=new  ArrayList<PrizePO>();
			
			PrizePO prizePO1=new PrizePO();
			prizePO1.setPrizeItem("一等奖");
			prizePO1.setPrizeName("iphone1");
			prizePO1.setCount(2);
			
			PrizePO prizePO2=new PrizePO();
			prizePO2.setPrizeItem("二等奖");
			prizePO2.setPrizeName("iphone2");
			prizePO2.setCount(4);
			
			
			PrizePO prizePO3=new PrizePO();
			prizePO3.setPrizeItem("三等奖");
			prizePO3.setPrizeName("iphone3");
			prizePO3.setCount(6);
			
			PrizePO prizePO4=new PrizePO();
			prizePO4.setPrizeItem("四等奖");
			prizePO4.setPrizeName("iphone4");
			prizePO4.setCount(8);
			
			PrizePO prizePO5=new PrizePO();
			prizePO5.setPrizeItem("五等奖");
			prizePO5.setPrizeName("iphone5");
			prizePO5.setCount(10);
		
			prizePOList.add(prizePO1);
			prizePOList.add(prizePO2);
			prizePOList.add(prizePO3);
			prizePOList.add(prizePO4);
			prizePOList.add(prizePO5);
				
			gamesPO.setPrizePOList(prizePOList);
			gamesService.saveSetting(gamesPO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateGamesStartTime()  {
		try {
			int res = gamesService.updateGamesStartTime(13);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
