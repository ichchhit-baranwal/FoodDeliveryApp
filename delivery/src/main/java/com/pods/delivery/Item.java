package com.pods.delivery;

import java.util.Objects;

public class Item {
	private long itemId;
	private double price;
	private int quantity;
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long id) {
		this.itemId = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}
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
	@Override
	public String toString() {
		return "Item [id=" + itemId + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
	
	
}
