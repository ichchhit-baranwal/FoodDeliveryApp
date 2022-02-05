package com.pods.delivery;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Restaurant {
	private long restId;
	private ConcurrentHashMap<Long, Item> items;
	public long getRestId() {
		return restId;
	}
	
	public Restaurant() {
		items = new ConcurrentHashMap<>();
	}
	public void setRestId(long id) {
		this.restId = id;
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
		return Objects.hash(restId);
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
		return restId == other.restId;
	}
	@Override
	public String toString() {
		return "Restaurant [id=" + restId + ", items=" + items + "]";
	}
	
	
	
}
