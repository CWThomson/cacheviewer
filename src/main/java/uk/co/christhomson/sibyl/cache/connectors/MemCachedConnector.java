package uk.co.christhomson.sibyl.cache.connectors;

import java.util.Map;

import uk.co.christhomson.sibyl.exception.CacheException;

/*
 MemCachedConnector
  Connector to retrieve data from a MemCached cache cluster
 
 Copyright (C) 2011 Chris Thomson
 */
public class MemCachedConnector implements CacheConnector {
	
	protected MemCachedConnector() {}
	
	public Object get(String cacheName, Object key) throws CacheException {
		throw new CacheException("Not implemented yet");
	}
	
	public Map<?,?> getAll(String cacheName) throws CacheException {
		throw new CacheException("Not implemented");
	}
	
	public void putAll(String cacheName, Map<?,?> data) throws CacheException {
		throw new CacheException("Not implemented yet");
	}

	public void put(String cacheName, Object key, Object value)
			throws CacheException {
		throw new CacheException("Not implemented yet");
	}

	public void clear(String cacheName) throws CacheException {
		throw new CacheException("Not implemented yet");
	}

	public void clearAll() throws CacheException {
		throw new CacheException("Not implemented yet");
	}	
}
