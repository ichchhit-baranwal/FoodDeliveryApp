package com.pods.restaurant;

public class ResReq {
	private long restId;
	private long itemId;
	private int qty;
	
	
	public ResReq() {
		super();
	}
	
	@Override
	public String toString() {
		return "ResReq [restId=" + restId + ", itemId=" + itemId + ", qty=" + qty + "]";
	}

	public ResReq(long restId, long itemId, int qty) {
		super();
		this.restId = restId;
		this.itemId = itemId;
		this.qty = qty;
	}
	public long getRestId() {
		return restId;
	}
	public void setRestId(long restId) {
		this.restId = restId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
