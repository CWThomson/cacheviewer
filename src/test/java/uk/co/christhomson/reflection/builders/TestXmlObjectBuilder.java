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

package uk.co.christhomson.reflection.builders;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.reflection.builders.XmlObjectBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.TestKey3;
import uk.co.christhomson.sibyl.sample.objects.TestKey5;

public class TestXmlObjectBuilder {

	private XMLOutputter xmlOutput = null;

	@Before
	public void setup() {
		xmlOutput = new XMLOutputter(Format.getPrettyFormat());
	}

	@Test
	public void test1() throws IOException, IllegalArgumentException, IllegalAccessException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder(TestKey3.class,null);
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}

	@Test
	public void test2() throws IOException, IllegalArgumentException, IllegalAccessException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder(Double.class,null);
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}

	@Test
	public void test3() throws IOException, IllegalArgumentException, IllegalAccessException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder(double.class,null);
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}

	@Test
	public void test4() throws ClassNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder("double");
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}

	@Test
	public void test5() throws ClassNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder(String.class,null);
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}
	
	@Test
	public void test6() throws IllegalArgumentException, IllegalAccessException, IOException, CacheException {
		XmlObjectBuilder builder = new XmlObjectBuilder(String.class,"dddd");
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}
	
	@Test
	public void test7() throws IllegalArgumentException, IllegalAccessException, IOException, CacheException {
		Date d1 = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime();
		Date d2 = new GregorianCalendar(2011, Calendar.JANUARY, 2).getTime();
		TestKey5 key = new TestKey5(d1,d2);
		XmlObjectBuilder builder = new XmlObjectBuilder(TestKey5.class,key);
		Element elem = builder.createXmlObject();

		xmlOutput.output(elem, System.out);
	}
}
