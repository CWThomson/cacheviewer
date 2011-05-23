package uk.co.christhomson.coherence.utilities;

import uk.co.christhomson.sibyl.exception.CacheException;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/*
 CacheExtractor
  Wrapper class to build an object from a properties file
  and extract value from a Coherence cace
 
 Copyright (C) 2011 Chris Thomson
 */
public class CacheExtractor {
	private NamedCache cache = null;

	public CacheExtractor(String cacheName) throws CacheException {
		if (cacheName == null || cacheName.length() == 0) {
			throw new CacheException("Cache name was not specified");
		}
		
		try {
			cache = CacheFactory.getCache(cacheName);
		} catch (IllegalArgumentException e) {
			throw new CacheException("Cache: '" + cacheName + "' could not be found");
		}
	}

	public Object extract(Object key) {

		System.out.println(key);

		Object value = cache.get(key);

		return value;
	}
}
