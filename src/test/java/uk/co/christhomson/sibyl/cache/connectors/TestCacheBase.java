package uk.co.christhomson.sibyl.cache.connectors;

import org.junit.Before;

public class TestCacheBase {
	
	private boolean pofEnabled = false;
	
	public TestCacheBase(boolean pofEnabled) {
		this.pofEnabled = pofEnabled;
	}
	
	@Before
	public void setup() throws Exception {
		System.out.println("Initialising...");

		setupCoherence();
		
	}

	private void setupCoherence() {
		System.setProperty("tangosol.coherence.log", "stdout");
		
		if (pofEnabled) {
			System.setProperty("tangosol.pof.enabled", "true");
			System.setProperty("tangosol.pof.config",
				"src/test/resources/viewer-pof-config.xml");
		}
		
		System.setProperty("tangosol.coherence.distributed.threads", "5");
		System.setProperty("tangosol.coherence.cacheconfig",
				"src/main/resources/viewer-cache-config.xml");
		System.setProperty("tangosol.coherence.guard.timeout", "0");
	}
}
