package com.techo.service;

public interface HashingService {

	public void create(String id);

	public void delete(String id);

	public void put(String id, Object key, Object value);

	public Object get(String id, Object key);

	public void delete(String id, Object key);
}
