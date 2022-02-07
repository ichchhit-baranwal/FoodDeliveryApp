package com.pods.delivery;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Class for Restaurant POJO
 * contain restId to uniquely identify a restaurant
 * and a collection(Hash map) of items to facilitate
 * quick and easy lookup for items
 */
public class Restaurant {
	private long restId;
	private ConcurrentHashMap<Long, Item> items;
	/**
	 * @return restId - Restaurant id
	 */
	public long getRestId() {
		return restId;
	}
	/**
	 * Default constructor to initialize the hashmap object 
	 */
	public Restaurant() {
		items = new ConcurrentHashMap<>();
	}
	
	/**
	 * @param id
	 */
	public void setRestId(long id) {
		this.restId = id;
	}
	
	
	/**
	 * Add item to the hashmap
	 * with given details
	 * @param itemId
	 * @param price
	 * @param quantity
	 */
	public void addItem(long itemId, double price, int quantity){
		Item newItem = new Item(itemId, price, quantity);
		items.put(itemId, newItem);
	}
	/**
	 * Returns item corresposnding to itemId
	 * @param itemId
	 * @return item
	 */
	public Item getItem(long itemId) {
		return items.get(itemId);
	}
	/**
	 * To get the number of items in restaurant
	 * @return itemCount
	 */
	public long getItemCount() {
		return items.mappingCount();
	}
	/**
	 * generates the hash based on restaurant id
	 * that can be used for hash function
	 */
	@Override
	public int hashCode() {
		return Objects.hash(restId);
	}
	/**
	 * To compare between two restaurant object
	 * based on restaurant id
	 */
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
	/**
	 * For printing purpose to 
	 * so as to change content of the object
	 * to string equivalent
	 */
	@Override
	public String toString() {
		return "Restaurant [id=" + restId + ", items=" + items + "]";
	}
	
	
	
}
