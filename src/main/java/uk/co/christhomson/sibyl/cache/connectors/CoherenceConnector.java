package uk.co.christhomson.sibyl.cache.connectors;

import java.util.Map;

import uk.co.christhomson.sibyl.exception.CacheException;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/*
 CoherenceConnector
  Connector to retrieve data from a Coherence cache cluster
 
 Copyright (C) 2011 Chris Thomson
 */
public class CoherenceConnector implements CacheConnector {
	
	protected CoherenceConnector() {}
	
	public NamedCache getCache(String cacheName) throws CacheException {
		if (cacheName == null || cacheName.length() == 0) {
			throw new CacheException("Cache name was not specified");
		}
		
		NamedCache cache = null;
		
		try {
			cache = CacheFactory.getCache(cacheName);
		} catch (IllegalArgumentException e) {
			throw new CacheException("Cache: '" + cacheName + "' could not be found");
		}
		
		return cache;
	}

	public Object get(String cacheName, Object key) throws CacheException {

		NamedCache cache = getCache(cacheName);
		
		System.out.println(key);

		Object value = cache.get(key);

		return value;
	}
	
	public Map<?,?> getAll(String cacheName) throws CacheException {
		throw new CacheException("Not implemented");
	}
	
	public void putAll(String cacheName, Map<?,?> data) throws CacheException {
		NamedCache cache = getCache(cacheName);
		cache.putAll(data);
	}

	public void put(String cacheName, Object key, Object value)
			throws CacheException {
		NamedCache cache = getCache(cacheName);
		cache.put(key, value);
	}

	public void clear(String cacheName) throws CacheException {
		getCache(cacheName).clear();
	}

	public void clearAll() throws CacheException {
		throw new CacheException("Can not clear all caches");
	}
	
}
