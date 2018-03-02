package com.chinasofti.model;

import java.util.Date;
import java.util.List;

public class GamesPO {

	private Integer id;
	private String roomNo;
	private String gameType;
	private Date startTime;
	private Date endTime;
	
	private List<PrizePO> prizePOList;
	private RedpacketPO redpacket;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<PrizePO> getPrizePOList() {
		return prizePOList;
	}

	public void setPrizePOList(List<PrizePO> prizePOList) {
		this.prizePOList = prizePOList;
	}

	public RedpacketPO getRedpacket() {
		return redpacket;
	}

	public void setRedpacket(RedpacketPO redpacket) {
		this.redpacket = redpacket;
	}

	 

}
