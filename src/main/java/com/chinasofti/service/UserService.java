package com.chinasofti.service;

import java.util.List;
import java.util.Map;

public interface UserService {
 public List<Map<String, Object>>  getMyPrize(String staffId);
 public List<Map<String, Object>>  getWinners(String staffId);
	
}
