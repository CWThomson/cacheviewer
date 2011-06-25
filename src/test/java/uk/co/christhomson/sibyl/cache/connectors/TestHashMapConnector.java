package uk.co.christhomson.sibyl.cache.connectors;

import org.junit.Before;

import uk.co.christhomson.sibyl.exception.CacheException;

/*
 Copyright (C) 2011 Chris Thomson
 */
public class TestHashMapConnector extends TestConnector {
	
	@Before
	public void setup() throws CacheException {
		connector = new HashMapConnector();
		super.setup();
	}

	@Override
	public String getQuery() {
		return "ticker=='BP.L'";
	}
}
