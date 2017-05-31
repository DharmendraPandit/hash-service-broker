package com.techo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.techo.utility.CustomHashMap;

@Service
public class HashingServiceImpl implements HashingService {

	private Map<String, CustomHashMap<Object, Object>> hashMaps = new HashMap<>();

	@Override
	public void create(String id) {
		hashMaps.put(id, new CustomHashMap<Object, Object>());
	}

	@Override
	public void delete(String id) {
		hashMaps.remove(id);
	}

	@Override
	public void put(String id, Object key, Object value) {
		if(hashMaps.get(id) == null){
			hashMaps.put(id, new CustomHashMap<Object, Object>());
		}
		CustomHashMap<Object, Object> mapInstance = hashMaps.get(id);
		mapInstance.put(key, value);
	}

	@Override
	public Object get(String id, Object key) {
		CustomHashMap<Object, Object> mapInstance = hashMaps.get(id);
		return mapInstance.get(key);
	}

	@Override
	public void delete(String id, Object key) {
		CustomHashMap<Object, Object> mapInstance = hashMaps.get(id);
		mapInstance.remove(key);
	}
}
