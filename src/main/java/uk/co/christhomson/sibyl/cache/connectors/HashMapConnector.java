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

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import uk.co.christhomson.sibyl.exception.CacheException;

/*
 HashMapConnector
 Connector to retrieve data from a static Map of Maps - this has no dependencies on external 
 caching providers 

 Copyright (C) 2011 Chris Thomson
 */
public class HashMapConnector implements CacheConnector {

	public static Map<String, Map<Object, Object>> caches = new HashMap<String, Map<Object, Object>>();

	protected HashMapConnector() {
	}

	public Map<Object, Object> getCache(String cacheName) throws CacheException {
		Map<Object, Object> cache = caches.get(cacheName);
		if (cache == null) {
			cache = new HashMap<Object, Object>();
			caches.put(cacheName, cache);
		}

		return cache;
	}

	public Object get(String cacheName, Object key) throws CacheException {
		Map<Object, Object> cache = getCache(cacheName);
		Object value = cache.get(key);
		return value;
	}

	public Map<?, ?> getAll(String cacheName) throws CacheException {
		return getCache(cacheName);
	}

	public void putAll(String cacheName, Map<?, ?> data) throws CacheException {
		Map<Object, Object> cache = getCache(cacheName);
		cache.putAll(data);
	}

	public void put(String cacheName, Object key, Object value)
			throws CacheException {
		Map<Object, Object> cache = getCache(cacheName);
		cache.put(key, value);
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

	public Map<?, ?> query(String cacheName, String query)
			throws CacheException {
		Map<Object, Object> results = new HashMap<Object, Object>();
		Map<?, ?> data = getCache(cacheName);
		
		ExpressionParser parser = new SpelExpressionParser();
		
		for (Object key : data.keySet()) {
			Object value = data.get(key);
			EvaluationContext keyContext = new StandardEvaluationContext(key);
			
			Expression exp = parser.parseExpression(query);
			boolean result = exp.getValue(keyContext, Boolean.class);
			
			if (result) {
				results.put(key, value);
			}
		}
		
		return results;
	}

	public String getQueryLanguageDescription() {
		return "To query the cache by keys, use Spring Expression Language (SpEL) syntax";
	}
}
