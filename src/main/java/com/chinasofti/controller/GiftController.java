package com.chinasofti.controller;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.model.GameConstants;
import com.chinasofti.model.Result;
import com.chinasofti.service.GiftService;

@RestController
@RequestMapping("gift")
public class GiftController {

	private static Logger log = Logger.getLogger(GiftController.class);

	@Autowired
	private GiftService giftService;
	
	@Autowired
    public Properties velocityConf;

	@RequestMapping(value = "getGift/{staffId}/{roomNo}", method = RequestMethod.GET)
	public Result getGift(@PathVariable(value = "staffId") String staffId,@PathVariable(value="roomNo") String roomNo) throws Exception {
		log.info("getGiftListByRoomNo method: param staffId : {"+ staffId +"} , roomNo : {"+ roomNo +"} ");
		 Map<String, Object> resMap = giftService.getGiftListByRoomNo(staffId,roomNo);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), resMap);
		return result;
	}
	
	@RequestMapping(value = "startGames/{gid}", method = RequestMethod.GET)
	public Result startGames(@PathVariable(value = "gid") int gid) throws Exception {
		log.info("startGames method: param gid : {"+ gid +"} ");
		int res = giftService.updateGiftStatusByGid(gid);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), res);
		return result;
	}
	
	
}
