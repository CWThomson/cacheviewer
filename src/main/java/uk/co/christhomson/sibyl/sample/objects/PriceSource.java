package uk.co.christhomson.sibyl.sample.objects;

/*
PriceSource
 Enum to represent sources of price data

Copyright (C) 2011 Chris Thomson
*/
public enum PriceSource {
	REUTERS(1),
	BLOOMBERG(2),
	UNKNOWN(3);
	
	private int value;
	
	private PriceSource(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static PriceSource find(int value) {
		switch (value) {
		case 1 :
			return REUTERS;
		case 2:
			return BLOOMBERG;
		default : 
			return UNKNOWN;
		
		}
	}
}
