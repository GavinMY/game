package com.chinasofti.service;

import java.util.List;
import java.util.Map;

import com.chinasofti.model.Gift;
import com.chinasofti.model.Rooms;

public interface GiftService {
	
	Map<String,Object> getGiftListByRoomNo(String staffId,String roomNo) throws Exception;
	
	Gift getGiftById(int gId) throws Exception;
	
	Rooms getRoomsByRoomNo(String roomNo) throws Exception;
	
	int updateGiftStatusByGid(int gid)throws Exception;
	
	List<Rooms> getRoomsByOwnerStaffid(String staffId)  throws Exception ;
	
	
}
