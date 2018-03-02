package com.chinasofti.service.Impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.chinasofti.exception.ValidateException;
import com.chinasofti.model.Gift;
import com.chinasofti.model.Rooms;
import com.chinasofti.service.GiftService;

@Service
public class GiftServiceImpl implements GiftService {
	
	private static final int Gift_Status = 1;//开始

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<String,Object> getGiftListByRoomNo(String staffId,String roomNo) throws Exception {
		Map<String,Object> map =new HashMap<String,Object>();
		Boolean isBoss=false;
		
		List<Rooms> roomsList = getRoomsByOwnerStaffid(staffId);
		if(roomsList != null && roomsList.size()>0){
			isBoss=true;
		}
		map.put("isBoss",isBoss);
		
		Rooms dbRooms = getRoomsByRoomNo(roomNo);
		if(dbRooms == null){
			throw new ValidateException("roomNo not exits !"); 
		}
		RowMapper<Gift> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Gift.class);
		final String sql = "SELECT gid,times,money,probability,count,version,status,roomId,startTime FROM ls_gift where roomid = ? ";
		List<Gift> list =  jdbcTemplate.query(sql, new Object[] { dbRooms.getId() }, rowMapper);
		map.put("list", list);
		return map;
	}

	@Override
	public Rooms getRoomsByRoomNo(String roomNo)  throws Exception {
		RowMapper<Rooms> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Rooms.class);
		final String sql = "SELECT id,roomNo,ownerStaffid,createTime from ls_room where roomNo = ? ";
		List<Rooms> list = this.jdbcTemplate.query(sql, new Object[] { roomNo }, rowMapper);
		Rooms room = null;
		if (list != null && list.size() > 0) {
			room = list.get(0);
		}
		return room;
	}
	
	
	@Override
	public List<Rooms> getRoomsByOwnerStaffid(String staffId)  throws Exception {
		RowMapper<Rooms> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Rooms.class);
		final String sql = "SELECT id,roomNo,ownerStaffid,createTime from ls_room where ownerStaffid = ? ";
		return this.jdbcTemplate.query(sql, new Object[] { staffId }, rowMapper);
	}
	
	

	@Override
	public int updateGiftStatusByGid(int gid)throws Exception {
		Gift dbgift = getGiftById(gid);
		if(dbgift==null){
			throw new ValidateException("gid not exits !"); 
		}
		final String sql = "update ls_gift set status = ?, startTime = now() where gid= ? ";
		return jdbcTemplate.update(sql, new Object[] {Gift_Status ,gid});
	}
	
	@Override
	public Gift getGiftById(int gId) throws Exception {
		RowMapper<Gift> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Gift.class);
		final String sql = "SELECT gid,times,money,probability,count,version,status,roomId,startTime FROM ls_gift where gid = ? ";
		List<Gift> list =  jdbcTemplate.query(sql, new Object[] { gId }, rowMapper);
		Gift gift=null;
		if (list != null && list.size() > 0) {
			gift = list.get(0);
		}
		return gift;
	}

}
