package com.chinasofti.service.Impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinasofti.service.RoomService;
@Service
public class RoomServiceImpl implements RoomService {

	private static Logger logger = Logger.getLogger(RoomServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public Boolean addRoom(String roomNo, String staffId) {
		Boolean flg = false;
		String sql = "INSERT INTO ls_room(roomNo,createUserid) VALUES(?,?)";
		int result = this.jdbcTemplate.update(sql, new Object[] { roomNo, staffId });
		if (result > 0) {
			flg = true;
		}
		return flg;
	}
	@Override
	public List<Map<String, Object>> findRoom(String roomNo, String staffId) {
		String sql="SELECT * from ls_room WHERE roomNo='1111' and createUserid='test'";
		List<Map<String, Object>> list=this.jdbcTemplate.queryForList(sql);
		return list;
	}

}
