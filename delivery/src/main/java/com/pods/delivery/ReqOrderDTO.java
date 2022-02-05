package com.pods.delivery;

public class ReqOrderDTO {
	private long custId;
	private long restId;
	private long itemId;
	private int qty;
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
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
	@Override
	public String toString() {
		return "ReqOrderDTO [custId=" + custId + ", restId=" + restId + ", itemId=" + itemId + ", qty=" + qty + "]";
	}
	
}
