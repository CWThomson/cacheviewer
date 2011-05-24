package uk.co.christhomson.sibyl.exception;

/*
InvalidCacheNameException
 Exception raised when cache does not exist

Copyright (C) 2011 Chris Thomson
*/
public class InvalidCacheNameException extends CacheException {

	private static final long serialVersionUID = -2196889723341389771L;

	public InvalidCacheNameException() {
		super();
	}

	public InvalidCacheNameException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public InvalidCacheNameException(String msg) {
		super(msg);
	}

	public InvalidCacheNameException(Throwable ex) {
		super(ex);
	}

}
