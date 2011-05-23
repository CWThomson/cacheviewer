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

import uk.co.christhomson.coherence.utilities.TestCoherenceBase;
import uk.co.christhomson.sibyl.sample.loader.InstrumentPriceBulkLoader;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class TestInstrumentPriceBulkLoader extends TestCoherenceBase {

	private NamedCache cache = null;
	
	public TestInstrumentPriceBulkLoader() {
		super(false);
	}
	
	@Before
	public void setup() {
		super.setup();
		
		cache = CacheFactory.getCache("TEST_PRICE_CACHE");

		InstrumentPriceBulkLoader priceLoader = new InstrumentPriceBulkLoader(
				cache);
		priceLoader.generatePrices("VOD.L", PriceSource.BLOOMBERG);
	}
	
	@Test
	public void test() {
		Date date = new GregorianCalendar(2010, Calendar.JANUARY, 4).getTime();
		
		InstrumentPriceKey key = new InstrumentPriceKey("VOD.L", date, PriceSource.BLOOMBERG);
		System.out.println(key);
		
		InstrumentPrice price = (InstrumentPrice) cache.get(key);
		System.out.println(price);
		
		cache.put(key, key);
		
		System.out.println(cache.get(key));
		
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
