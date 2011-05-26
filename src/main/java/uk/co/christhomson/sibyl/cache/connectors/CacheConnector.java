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

import java.util.Map;

import uk.co.christhomson.sibyl.exception.CacheException;

public interface CacheConnector {
	public Object get(String cacheName, Object key) throws CacheException;
	public Map<?,?> getAll(String cacheName) throws CacheException;
	
	public void put(String cacheName, Object key, Object value) throws CacheException;
	public void putAll(String cacheName, Map<?,?> data) throws CacheException;
	
	public void clear(String cacheName) throws CacheException;
	public void clearAll() throws CacheException;
	
	public int getCacheSize(String cacheName) throws CacheException;

}
