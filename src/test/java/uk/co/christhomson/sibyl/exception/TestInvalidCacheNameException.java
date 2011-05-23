package uk.co.christhomson.sibyl.exception;

import org.junit.Test;

import uk.co.christhomson.sibyl.exception.InvalidCacheNameException;

public class TestInvalidCacheNameException {
	@Test
	public void test1() {
		InvalidCacheNameException ex = new InvalidCacheNameException();
	}
	
	@Test
	public void test2() {
		InvalidCacheNameException ex = new InvalidCacheNameException("TEST");
	}
	
	@Test
	public void test3() {
		InvalidCacheNameException ex = new InvalidCacheNameException(new Exception("TEST"));
	}
	
	@Test
	public void test4() {
		InvalidCacheNameException ex = new InvalidCacheNameException("TEST",new Exception("TEST"));
	}
}
