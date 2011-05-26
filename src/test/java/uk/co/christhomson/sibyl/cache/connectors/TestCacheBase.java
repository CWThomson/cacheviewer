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
