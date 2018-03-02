package com.chinasofti.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.exception.ValidateException;
import com.chinasofti.model.Result;
import com.chinasofti.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	public Properties velocityConf;
	@Autowired
	private UserService userServiceImpl;

	@ResponseBody
	@RequestMapping("getMyPrize")
	public Object getMyPrize(String staffId) {
		Result result = new Result(-1, velocityConf.getProperty("golbal.error"), null);
		if (!StringUtils.isEmpty(staffId)) {
			List<Map<String, Object>> list = userServiceImpl.getMyPrize(staffId);
			result = new Result(1, velocityConf.getProperty("golbal.success"), list);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("getWinners")
	public Object getWinners(String bossStaff) throws Exception {
		if (StringUtils.isEmpty(bossStaff)) {
			throw new ValidateException("bossStaff is null");
		}
		Result result = new Result(-1, velocityConf.getProperty("golbal.error"), null);
		List<Map<String, Object>> list = userServiceImpl.getWinners(bossStaff);
		result = new Result(1, velocityConf.getProperty("golbal.success"), list);
		return result;
	}

}
