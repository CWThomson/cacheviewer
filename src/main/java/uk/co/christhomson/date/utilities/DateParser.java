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

package uk.co.christhomson.date.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

/*
DateParser
 Class to parse a date string using a number of different formats
*/
public class DateParser {
	
	private static Logger log = Logger.getLogger(DateParser.class);
	
	private Collection<SimpleDateFormat> sdfs = new ArrayList<SimpleDateFormat>();

	public DateParser() {
		initialise();
	}

	public DateParser(Collection<SimpleDateFormat> sdfs) {
		this.sdfs = sdfs;
	}
	
	private void initialise() {
		sdfs.add(new SimpleDateFormat("yyyy-MM-dd"));
		sdfs.add(new SimpleDateFormat("yyyy/MM/dd"));
		sdfs.add(new SimpleDateFormat("dd/MM/yyyy"));
		sdfs.add(new SimpleDateFormat("dd-MM-yyyy"));
		sdfs.add(new SimpleDateFormat("MM/dd/yyyy"));
		sdfs.add(new SimpleDateFormat("MM-dd-yyyy"));
	}
	
	public Date parse(String dateStr) {
		Date date = null;
		for (SimpleDateFormat sdf : sdfs) {
			try {
				date = sdf.parse(dateStr);
				return date;
			}
			catch (ParseException ex) {
				log.debug(ex,ex);
			}
		}
		
		return date;
	}
}
