package uk.co.christhomson.sibyl.exception;

/*
CacheException
 Exception raised from Coherence cache system

Copyright (C) 2011 Chris Thomson
*/
public class CacheException extends Exception {

	private static final long serialVersionUID = 1901346281940275437L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}
