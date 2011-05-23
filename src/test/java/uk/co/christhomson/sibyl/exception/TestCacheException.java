package uk.co.christhomson.sibyl.exception;

import org.junit.Test;

import uk.co.christhomson.sibyl.exception.CacheException;

public class TestCacheException {
	@Test
	public void test1() {
		CacheException ex = new CacheException();
	}
	
	@Test
	public void test2() {
		CacheException ex = new CacheException("TEST");
	}
	
	@Test
	public void test3() {
		CacheException ex = new CacheException(new Exception("TEST"));
	}
	
	@Test
	public void test4() {
		CacheException ex = new CacheException("TEST",new Exception("TEST"));
	}
}
