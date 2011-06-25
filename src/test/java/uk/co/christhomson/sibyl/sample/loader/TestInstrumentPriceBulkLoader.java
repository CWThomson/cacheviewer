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

package uk.co.christhomson.sibyl.sample.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.cache.connectors.HashMapConnector;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

public class TestInstrumentPriceBulkLoader {

	private CacheConnector connector = null;
	private String cacheName = "TEST_PRICE_CACHE";
	
	private String connectorName = HashMapConnector.class.getName();
	
	public TestInstrumentPriceBulkLoader() {
	}
	
	@Before
	public void setup() throws Exception {
		
		connector = ConnectorBuilder.getConnector(connectorName);
		connector.clearAll();
		
		InstrumentPriceBulkLoader priceLoader = new InstrumentPriceBulkLoader(
				connector);
		priceLoader.generatePrices(cacheName, "VOD.L", PriceSource.BLOOMBERG);
	}
	
	@Test
	public void test() throws CacheException {
		Date date = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		
		InstrumentPriceKey key = new InstrumentPriceKey("VOD.L", date, PriceSource.BLOOMBERG);
		
		InstrumentPrice price = (InstrumentPrice) connector.get(cacheName, key);
		
		connector.put(cacheName, key, key);
		
		assertNotNull(price);
	}
	
	@Test
	public void testEquals() {
		Date date1 = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		InstrumentPriceKey key1 = new InstrumentPriceKey("VOD.L", date1, PriceSource.BLOOMBERG);
		
		Date date2 = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		InstrumentPriceKey key2 = new InstrumentPriceKey("VOD.L", date2, PriceSource.BLOOMBERG);
		
		assertEquals(key1,key2);
	}

	@Test
	public void testWrite1() throws IOException {
		
		Date date1 = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		InstrumentPriceKey key1 = new InstrumentPriceKey("VOD.L", date1, PriceSource.BLOOMBERG);
		
		ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream("C:\\temp\\test1"));
		serializer.writeObject(key1);
		serializer.close();
		
	}

	@Test
	public void testWrite2() throws IOException {
		
		Date date1 = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		InstrumentPriceKey key1 = new InstrumentPriceKey("VOD.L", date1, PriceSource.BLOOMBERG);
		
		ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream("C:\\temp\\test2"));
		serializer.writeObject(key1);
		serializer.close();
	}
}
