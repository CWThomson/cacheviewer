package uk.co.christhomson.coherence.utilities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import uk.co.christhomson.coherence.utilities.CacheInfo;

import com.tangosol.net.Member;

public class TestCacheInfo extends TestCoherenceBase {
	
	public TestCacheInfo() {
		super(true);
	}
	
	@Test
	public void testGetCacheScheme() {
		String scheme = CacheInfo.getCacheScheme("TEST_CACHE");
		assertNotNull(scheme);
	}
	
	@Test
	public void test() {
		Collection<Member> members = CacheInfo.getMembers();
		assertNotNull(members);
		assertTrue(members.size() > 0);
	}
}
