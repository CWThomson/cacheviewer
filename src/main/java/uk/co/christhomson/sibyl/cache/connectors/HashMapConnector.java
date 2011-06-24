/*
	This file is part of Sibyl Cache Viewer.

    Sibyl is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Sibyl is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Sibyl.  If not, see <http://www.gnu.org/licenses/>.

	Copyright (C) 2011 Chris Thomson
*/

package uk.co.christhomson.sibyl.cache.connectors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

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

	public int getCacheSize(String cacheName) throws CacheException {
		return getCache(cacheName).size();
	}

	public Map<?,?> query(String cacheName, String query)
			throws CacheException {
		Map<InstrumentPriceKey, InstrumentPrice> results = new HashMap<InstrumentPriceKey,InstrumentPrice>();
		results.put(new InstrumentPriceKey("VOD.L",new Date(),PriceSource.BLOOMBERG),new InstrumentPrice(10));
		results.put(new InstrumentPriceKey("BP.L",new Date(),PriceSource.BLOOMBERG),new InstrumentPrice(20));
		results.put(new InstrumentPriceKey("RBS.L",new Date(),PriceSource.BLOOMBERG),new InstrumentPrice(30));
		return results;
	}
}
