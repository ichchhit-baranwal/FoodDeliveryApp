package com.pods.delivery;

/**
 * The class for representing the 
 * parameters passed as request body
 * to /requestOrder endpoint
 */
public class ReqOrderDTO {
	private long custId;
	private long restId;
	private long itemId;
	private int qty;
	/**
	 * @return custId
	 */
	public long getCustId() {
		return custId;
	}
	/**
	 * Sets the custId
	 * @param custId
	 */
	public void setCustId(long custId) {
		this.custId = custId;
	}
	
	/**
	 * @return restaurant id
	 */
	public long getRestId() {
		return restId;
	}
	
	/**
	 * sets the restId field
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
	 * sets the itemId field
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
	
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "ReqOrderDTO [custId=" + custId + ", restId=" + restId + ", itemId=" + itemId + ", qty=" + qty + "]";
	}
	
}
