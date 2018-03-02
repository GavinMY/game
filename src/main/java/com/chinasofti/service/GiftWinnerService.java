package com.chinasofti.service;

import java.util.List;

import com.chinasofti.model.GiftWinner;

public interface GiftWinnerService {
	 
	List<GiftWinner> getGiftWinnerByRoomNo(String roomNo);
	
}
