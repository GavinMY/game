package com.chinasofti.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.exception.ValidateException;
import com.chinasofti.model.Result;
import com.chinasofti.service.LotteryService;
import com.chinasofti.service.UserService;

@RestController
@RequestMapping("gift")
public class LotteryController {
	
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private Properties velocityConf;
	@Autowired
	private LotteryService giftServiceImpl;
	
	@Autowired
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;	
	
	@ResponseBody
	@RequestMapping("shake")
	public Object join(String staffId,String roomNo) throws Exception
	{
		Result result = new Result(-1, velocityConf.getProperty("golbal.error"), null);
		
		if (StringUtils.isEmpty(staffId)||StringUtils.isEmpty(roomNo)) {
			throw new ValidateException("staffId or roomNo is null");
		}
		if(reloadableResourceBundleMessageSource.getMessage("rooNo", null, null).equals(roomNo))//找到了
		{
			if(!reloadableResourceBundleMessageSource.getMessage("whitelist", null, null).contains(staffId))
			{				
			throw new ValidateException("This is an internal event, you are not on the invitation list");
			}
		}
		List<Map<String, Object>> list=userServiceImpl.getMyPrize(staffId);
		if(null!=list&&list.size()>0)
		{
			throw new ValidateException("you have been winner");
		}
		Map gift =giftServiceImpl.join(staffId,roomNo);		
		result = new Result(1, velocityConf.getProperty("golbal.success"), gift);
		return result;
	}
}
