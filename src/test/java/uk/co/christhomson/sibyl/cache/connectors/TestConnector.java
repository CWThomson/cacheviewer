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
	
}
