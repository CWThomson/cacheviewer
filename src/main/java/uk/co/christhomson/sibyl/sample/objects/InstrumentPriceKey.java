package uk.co.christhomson.sibyl.sample.objects;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

/*
InstrumentPriceKey
 key class uniquely representing a security price

Copyright (C) 2011 Chris Thomson
*/
public class InstrumentPriceKey implements PortableObject, Serializable {

	private static final long serialVersionUID = -3878397878224454476L;

	private String ticker = null;
	private Date date = null;
	private PriceSource source = null;
	
	public InstrumentPriceKey() {}
	
	public InstrumentPriceKey(String ticker, Date date, PriceSource source) {
		this.ticker = ticker;
		this.date = date;
		this.source = source;
	}

	public String getTicker() {
		return ticker;
	}

	public Date getDate() {
		return date;
	}

	public PriceSource getSource() {
		return source;
	}

	public void readExternal(PofReader reader) throws IOException {
		ticker = reader.readString(0);
		date = reader.readDate(1);
		source = PriceSource.find(reader.readInt(2));
	}

	public void writeExternal(PofWriter writer) throws IOException {
		writer.writeString(0, ticker);
		writer.writeDate(1, date);
		writer.writeInt(2, source.getValue());
	}

	@Override
	public String toString() {
		return "InstrumentPriceKey [date=" + date + ", ticker=" + ticker
				+ ", source=" + source + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ticker.hashCode();
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		InstrumentPriceKey other = (InstrumentPriceKey) obj;
		System.out.println(this + "-" + this.getDate().getTime() + " vs " + other + "-" + other.getDate().getTime());
		
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (!ticker.equals(other.ticker))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
		
}
