package com.chinasofti.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.model.Result;
import com.chinasofti.service.RoomService;

@Controller
@RequestMapping("room")
public class RoomController {
	@Resource(name = "roomServiceImpl")
	private RoomService roomServiceImpl;
	@Autowired
	public Properties velocityConf;
	@ResponseBody
	@RequestMapping(value = "create")
	public Object CreateRoom(String roomNo, String staffId) {								
		Result result = new Result(-1, velocityConf.getProperty("golbal.error"), null);
		if (!StringUtils.isEmpty(roomNo) && !StringUtils.isEmpty(staffId)) {
			// verify if the roomNo is exist
			List<Map<String, Object>> list = roomServiceImpl.findRoom(roomNo, staffId);
			if (null != list && list.size() > 0) {
				result = new Result(-1, velocityConf.getProperty("room.exist"), null);
			} else {
				if (roomServiceImpl.addRoom(roomNo, staffId)) {
					result = new Result(1, velocityConf.getProperty("golbal.success"), null);
				}
			}
		}
		return result;
	}

}
