package com.pods.restaurant;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Restaurant {
	private long id;
	private ConcurrentHashMap<Long, Item> items;
	public long getId() {
		return id;
	}
	
	public Restaurant() {
		items = new ConcurrentHashMap<>();
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void addItem(long itemId, double price, int quantity){
		Item newItem = new Item(itemId, price, quantity);
		items.put(itemId, newItem);
	}
	
	public Item getItem(long itemId) {
		return items.get(itemId);
	}
	public long getItemCount() {
		return items.mappingCount();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Restaurant)) {
			return false;
		}
		Restaurant other = (Restaurant) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", items=" + items + "]";
	}
	
	
	
}
