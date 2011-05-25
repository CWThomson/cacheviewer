package uk.co.christhomson.sibyl.sample.objects;

import java.io.Serializable;

/*
InstrumentPrice
 class to represent security price

Copyright (C) 2011 Chris Thomson
*/
public class InstrumentPrice implements Serializable {

	private static final long serialVersionUID = -9041885405688807715L;
	
	private double price;
	
	public InstrumentPrice() {}
	
	public InstrumentPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "InstrumentPrice [price=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentPrice other = (InstrumentPrice) obj;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}
	
}
