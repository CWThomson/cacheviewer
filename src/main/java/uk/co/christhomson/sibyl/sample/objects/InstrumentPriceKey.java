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

package uk.co.christhomson.sibyl.sample.objects;

import java.io.Serializable;
import java.util.Date;

/*
InstrumentPriceKey
 key class uniquely representing a security price

Copyright (C) 2011 Chris Thomson
*/
public class InstrumentPriceKey implements Serializable {

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
