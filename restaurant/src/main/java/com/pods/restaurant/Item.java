package com.pods.restaurant;

import java.util.Objects;
/**
 * The class corresponds to item Entity
 * It contains itemId to uniquely
 * identify an item
 */
public class Item {
	
	
	private long itemId;
	private double price;
	private int quantity;
	
	/**
	 * @return itemId of current item
	 */
	public long getItemId() {
		return itemId;
	}
	/**
	 * set itemId
	 * @param id
	 */
	public void setItemId(long id) {
		this.itemId = id;
	}
	
	/**
	 * @return price of the item
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * sets price of the item
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return quantity of the item
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * sets quantity of the item
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * generates the hash code of the
	 * the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}
	
	/**
	 * Compares the object of to another object
	 * uses the item id for comparison
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return itemId == other.itemId;
	}
	
	public Item(long id, double price, int quantity) {
		super();
		this.itemId = id;
		this.price = price;
		this.quantity = quantity;
	}
	
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "Item [id=" + itemId + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
	
	
}
