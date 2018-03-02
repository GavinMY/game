package com.chinasofti.controller;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.model.GameConstants;
import com.chinasofti.model.Gift;
import com.chinasofti.model.GiftWinner;
import com.chinasofti.model.Result;
import com.chinasofti.service.GiftService;
import com.chinasofti.service.GiftWinnerService;

@RestController
@RequestMapping("giftWinner")
public class GiftWinnerController {

	private static Logger log = Logger.getLogger(GiftWinnerController.class);

	@Autowired
	private GiftWinnerService giftWinnerService;
	
	@Autowired
    public Properties velocityConf;

	@RequestMapping(value = "getGifttWinner/{roomNo}", method = RequestMethod.GET)
	public Result getGifttWinner(@PathVariable(value = "roomNo") String roomNo) throws Exception {
		log.info("getGiftListByRoomNo method: param roomNo : {"+ roomNo +"} ");
		List<GiftWinner> giftList = giftWinnerService.getGiftWinnerByRoomNo(roomNo);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), giftList);
		return result;
	}
	
	
	
	
	
}
