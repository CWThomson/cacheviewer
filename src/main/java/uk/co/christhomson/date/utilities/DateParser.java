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

Copyright (C) 2011 Chris Thomson
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
