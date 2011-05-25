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
import uk.co.christhomson.sibyl.cache.connectors.TestCacheBase;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

public class TestInstrumentPriceBulkLoader extends TestCacheBase {

	private CacheConnector connector = null;
	private String cacheName = "TEST_PRICE_CACHE";
	
	private String connectorName = HashMapConnector.class.getName();
	
	public TestInstrumentPriceBulkLoader() {
		super(false);
	}
	
	@Before
	public void setup() throws Exception {
		super.setup();
		
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
