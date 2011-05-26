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

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.cache.connectors.HashMapConnector;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.TestKey2;
import uk.co.christhomson.sibyl.sample.objects.TestKey3;

public class TestCacheConnectors extends TestCacheBase {
	
	private String cacheName = "TEST_CACHE";
	
	public TestCacheConnectors() {
		super(true);
	}
	
	@Before
	public void setup() throws Exception {
		super.setup();
	}

	private void populateCache(CacheConnector connector) throws CacheException {
		Map<Object,Object> data = new HashMap<Object,Object>();
		
		data.put(new TestKey3(new TestKey2(1,"A",(short)2,3L,4.5,false,6,(short)7,8L,9.10,true)), "A");
		data.put(new TestKey3(new TestKey2(2,"B",(short)3,4L,5.6,true,7,(short)8,9L,10.11,false)), "B");
		data.put(new TestKey3(new TestKey2(3,"C",(short)4,5L,6.7,false,8,(short)9,10L,11.12,true)), "C");
		data.put(new TestKey3(new TestKey2(4,"D",(short)5,6L,7.8,true,9,(short)10,11L,12.13,false)), "D");
		data.put(new TestKey3(new TestKey2(5,"E",(short)6,7L,8.9,false,10,(short)11,12L,13.14,true)), "E");
		
		connector.putAll(cacheName, data);
	}

	public void test(CacheConnector connector) throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, CacheException, ParseException {
		populateCache(connector);
		
		String value = (String) connector.get(cacheName,new TestKey3(new TestKey2(5,"E",(short)6,7L,8.9,false,10,(short)11,12L,13.14,true)));
		assertEquals("E",value);	
	}
	
	@Test
	public void testHashMapConnector() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException, CacheException, ParseException {
		String connectorName = HashMapConnector.class.getName();
		CacheConnector connector = ConnectorBuilder.getConnector(connectorName);
		
		test(connector);
	}
}
