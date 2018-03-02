package com.chinasofti.model;

public class PrizePO {

	private Integer id;
	private Integer gameId;
	private String prizeItem;// 奖项
	private String prizeName;// 奖品名称
	private Integer count;// 数量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getPrizeItem() {
		return prizeItem;
	}

	public void setPrizeItem(String prizeItem) {
		this.prizeItem = prizeItem;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
