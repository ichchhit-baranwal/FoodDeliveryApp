package com.pods.delivery;

/**
 * Class for data transferring
 * of Order data over
 * the http request
 */

public class ReqOrder {
	private int orderId;

	/**
	 * @return orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * sets orderId field
	 * @param orderId
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
