package uk.co.christhomson.coherence.utilities;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.coherence.utilities.CacheExtractor;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.TestKey2;
import uk.co.christhomson.sibyl.sample.objects.TestKey3;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class TestCacheExtractor extends TestCoherenceBase {
	
	private String cacheName = "TEST_CACHE";
	
	public TestCacheExtractor() {
		super(true);
	}
	
	@Before
	public void setup() {
		super.setup();
		
		NamedCache cache = CacheFactory.getCache(cacheName);
		populateCache(cache);
	}

	private static void populateCache(NamedCache cache) {
		cache.put(new TestKey3(new TestKey2(1,"A",(short)2,3L,4.5,false,6,(short)7,8L,9.10,true)), "A");
		cache.put(new TestKey3(new TestKey2(2,"B",(short)3,4L,5.6,true,7,(short)8,9L,10.11,false)), "B");
		cache.put(new TestKey3(new TestKey2(3,"C",(short)4,5L,6.7,false,8,(short)9,10L,11.12,true)), "C");
		cache.put(new TestKey3(new TestKey2(4,"D",(short)5,6L,7.8,true,9,(short)10,11L,12.13,false)), "D");
		cache.put(new TestKey3(new TestKey2(5,"E",(short)6,7L,8.9,false,10,(short)11,12L,13.14,true)), "E");
	}

	@Test
	public void test() throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, CacheException, ParseException {
		CacheExtractor extractor = new CacheExtractor(cacheName);
		String value = (String) extractor.extract(new TestKey3(new TestKey2(5,"E",(short)6,7L,8.9,false,10,(short)11,12L,13.14,true)));
		assertEquals("E",value);
	}
}
