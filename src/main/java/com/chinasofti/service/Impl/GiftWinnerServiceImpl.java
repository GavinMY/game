package com.chinasofti.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.chinasofti.model.GiftWinner;
import com.chinasofti.service.GiftWinnerService;

@Service
public class GiftWinnerServiceImpl implements  GiftWinnerService{
	
	@Autowired
	private JdbcTemplate  jdbcTemplate;

	@Override
	public List<GiftWinner> getGiftWinnerByRoomNo(String roomNo) {
		RowMapper<GiftWinner> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GiftWinner.class);
		final String sql = "SELECT id,staffId,money,roomNo,winnerTime from ls_gift_winner where roomNo = ? ";
		List<GiftWinner> giftWinnerList = jdbcTemplate.query(sql, new Object[] { roomNo }, rowMapper);
		return giftWinnerList;
	}
	 
	
	
}
