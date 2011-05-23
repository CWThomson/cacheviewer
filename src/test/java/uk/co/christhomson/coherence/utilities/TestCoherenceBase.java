package uk.co.christhomson.coherence.utilities;

import org.junit.Before;

import com.tangosol.net.CacheFactory;

public class TestCoherenceBase {
	
	private boolean pofEnabled = false;
	
	public TestCoherenceBase(boolean pofEnabled) {
		this.pofEnabled = pofEnabled;
	}
	
	@Before
	public void setup() {
		System.out.println("Initialising...");
//		System.setProperty("viewer.properties", "src/test/resources/viewer.properties");
		
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
		
		CacheFactory.ensureCluster();
	}
}
