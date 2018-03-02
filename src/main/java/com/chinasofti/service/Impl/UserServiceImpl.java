package com.chinasofti.service.Impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinasofti.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(RoomServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<Map<String, Object>> getMyPrize(String staffId) {
		String sql="SELECT staffId,money,winnerTime from ls_gift_winner WHERE staffId=?";
		return this.jdbcTemplate.queryForList(sql,staffId);
	}
	@Override
	public List<Map<String, Object>> getWinners(String staffId) {
		String sql="SELECT staffId,winTime,gameType,prizeName,money from ls_winingresult WHERE boss=? ORDER BY winTime DESC";
		return this.jdbcTemplate.queryForList(sql,staffId);
	}

}
