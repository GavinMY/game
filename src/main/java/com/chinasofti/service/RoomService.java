package com.chinasofti.service;

import java.util.List;
import java.util.Map;

public interface RoomService {
	public Boolean addRoom(String roomNo,String staffId);	
	public List<Map<String, Object>> findRoom(String roomNo,String staffId);
}
