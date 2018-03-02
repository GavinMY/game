package com.chinasofti.controller;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.exception.ValidateException;
import com.chinasofti.model.GameConstants;
import com.chinasofti.model.GamesPO;
import com.chinasofti.model.Result;
import com.chinasofti.service.GamesService;

@RestController
@RequestMapping("games")
public class GamesController {

	private static Logger log = Logger.getLogger(GamesController.class);

	@Autowired
	private GamesService gamesService;
	
	
	private ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
    public Properties velocityConf;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result saveGames(@RequestBody GamesPO games) throws Exception {
		if(games==null){
			throw new ValidateException("save method: games is null");			
		}
		GamesPO gamesPO = gamesService.saveSetting(games);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), gamesPO);
		return result;
	}
	
	@RequestMapping(value = "startGames/{gameId}", method = RequestMethod.GET)
	public Result startGames(@PathVariable(value = "gameId") int gameId) throws Exception {
		System.out.println(messageSource);
		int res = gamesService.updateGamesStartTime(gameId);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), res);
		return result;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Result updateGames(@RequestBody GamesPO games) throws Exception {
		if(games==null){
			throw new ValidateException("update method: games is null");			
		}
		GamesPO gamesPO = gamesService.updateSetting(games);
		Result result = new Result(GameConstants.RES_SUCESSCODE, velocityConf.getProperty("golbal.success"), gamesPO);
		return result;
	}
	
}
