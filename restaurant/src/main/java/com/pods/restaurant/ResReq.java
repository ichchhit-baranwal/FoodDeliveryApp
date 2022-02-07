package com.pods.restaurant;
/**
 * The class to which is used 
 * for data transfer restaurant 
 * requests
 */
public class ResReq {
	private long restId;
	private long itemId;
	private int qty;
	
	
	public ResReq() {
		super();
	}
	
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
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
	
	/**
	 * @return restId of current object
	 */
	public long getRestId() {
		return restId;
	}
	/**
	 * set restId of current Restaurant
	 * @param restId
	 */
	public void setRestId(long restId) {
		this.restId = restId;
	}
	
	/**
	 * @return itemId 
	 */
	public long getItemId() {
		return itemId;
	}
	
	/**
	 * set set itemId
	 * @param itemId
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * @return qty
	 */
	public int getQty() {
		return qty;
	}
	
	/**
	 * sets the qty field
	 * @param qty
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
