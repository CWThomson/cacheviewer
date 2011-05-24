package uk.co.christhomson.sibyl.cache.connectors;

import java.util.HashMap;
import java.util.Map;

import uk.co.christhomson.sibyl.exception.CacheException;

/*
 HashMapConnector
  Connector to retrieve data from a static Map of Maps - this has no dependencies on external 
  caching providers 
 
 Copyright (C) 2011 Chris Thomson
 */
public class HashMapConnector implements CacheConnector {
	
	public static Map<String,Map<Object,Object>> caches = new HashMap<String,Map<Object,Object>>();
	
	protected HashMapConnector() {}
	
	public Map<Object,Object> getCache(String cacheName) throws CacheException {
		Map<Object,Object> cache = caches.get(cacheName);
		if (cache == null) {
			cache = new HashMap<Object,Object>();
			caches.put(cacheName, cache);
		}
		
		return cache;
	}

	public Object get(String cacheName, Object key) throws CacheException {

		Map<Object,Object> cache = getCache(cacheName);
		
		System.out.println(key);

		Object value = cache.get(key);

		return value;
	}
	
	public Map<?,?> getAll(String cacheName) throws CacheException {
		return getCache(cacheName);
	}

	public void putAll(String cacheName, Map<?, ?> data)
			throws CacheException {
		Map<Object,Object> cache = getCache(cacheName);
		cache.putAll(data);
	}

	public void put(String cacheName, Object key, Object value)
			throws CacheException {
		Map<Object,Object> cache = getCache(cacheName);
		cache.put(key,value);
	}

	public void clear(String cacheName) throws CacheException {
		getCache(cacheName).clear();
	}

	public void clearAll() throws CacheException {
		caches.clear();
	}
}
