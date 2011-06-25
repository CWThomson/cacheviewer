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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

/*
 Copyright (C) 2011 Chris Thomson
 */
public abstract class TestConnector {
	
	protected CacheConnector connector = null;
	
	private String cacheName = null;
	private InstrumentPriceKey baseKey = null;
	private InstrumentPrice baseValue = null;
	private InstrumentPriceKey key = null;
	private InstrumentPrice value = null;
	private Map<InstrumentPriceKey,InstrumentPrice> data = null;
	
	public abstract String getQuery();
	
	@Before
	public void setup() throws CacheException {
		cacheName = "TEST_PRICE_CACHE";
		
		baseKey = new InstrumentPriceKey("BP.L",new Date(),PriceSource.BLOOMBERG);
		baseValue = new InstrumentPrice(10);
		
		key = new InstrumentPriceKey("VOD.L",new Date(),PriceSource.BLOOMBERG);
		value = new InstrumentPrice(100);
		
		data = new HashMap<InstrumentPriceKey,InstrumentPrice>();
		data.put(new InstrumentPriceKey("RBS.L", new Date(), PriceSource.BLOOMBERG), new InstrumentPrice(1));
		data.put(new InstrumentPriceKey("BT.L", new Date(), PriceSource.BLOOMBERG), new InstrumentPrice(4));
		data.put(new InstrumentPriceKey("IBM.N", new Date(), PriceSource.BLOOMBERG), new InstrumentPrice(15));
		
		connector.put(cacheName, baseKey, baseValue);
	}
	
	@Test
	public void testGet() throws CacheException {
		Object obj = connector.get(cacheName, baseKey);
		assertNotNull(obj);
	}
	
	@Test
	public void testGetAll() throws CacheException {
		Map<?,?> data = connector.getAll(cacheName);
		assertNotNull(data);
		assertTrue(data.size() > 0);
	}
	
	@Test
	public void testPutAll() throws CacheException {
		int size1 = connector.getCacheSize(cacheName);
		connector.putAll(cacheName, data);
		int size2 = connector.getCacheSize(cacheName);
		
		assertTrue(size2 - size1 == data.size());
	}

	@Test
	public void testPut()
			throws CacheException {
		
		int size1 = connector.getCacheSize(cacheName);
		connector.put(cacheName, key, value);
		int size2 = connector.getCacheSize(cacheName);
		
		assertTrue(size2 - size1 == 1);
	}

	@Test
	public void testClear() throws CacheException {
		connector.clear(cacheName);
		
		int size = connector.getCacheSize(cacheName);
		assertTrue(size == 0);
	}

	@Test
	public void testClearAll() throws CacheException {
		connector.clearAll();
	}

	@Test
	public void testQuery() throws ClassNotFoundException, InstantiationException, IllegalAccessException, CacheException {
		String query = getQuery();
		Map<?, ?> results = connector.query(cacheName, query);
		System.out.println(results);
		assertTrue(results.size() == 1);
	}
}
