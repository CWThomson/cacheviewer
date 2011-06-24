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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import uk.co.christhomson.sibyl.sample.objects.PriceSource;
import uk.co.christhomson.sibyl.sample.objects.TestKey1;
import uk.co.christhomson.sibyl.sample.objects.TestKey2;
import uk.co.christhomson.sibyl.sample.objects.TestKey3;
import uk.co.christhomson.sibyl.sample.objects.TestKey5;

public class TestObjectBuilder {
	
	@Test
	public void test1() throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		Map<String,Object> props = new HashMap<String,Object>();
		props.put("k1","3");
		
		ObjectBuilder objBuilder = new ObjectBuilder(TestKey1.class.getName(), props);
		TestKey1 value = (TestKey1) objBuilder.build();
		
		assertEquals("3",value.getK1());
	}
	
	@Test
	public void test2() throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		Map<String,Object> subProps = new HashMap<String,Object>();
		subProps.put("k1","3");
		subProps.put("k2","C");
		subProps.put("k3","4");
		subProps.put("k4","5");
		subProps.put("k5","6.7");
		subProps.put("k6","false");
		subProps.put("k7","8");
		subProps.put("k8","9");
		subProps.put("k9","10");
		subProps.put("k10","11.12");
		subProps.put("k11","true");
		
		Map<String,Object> props = new HashMap<String,Object>();
		props.put("k1",subProps);
		
		ObjectBuilder objBuilder = new ObjectBuilder(TestKey3.class.getName(), props);
		TestKey3 value = (TestKey3) objBuilder.build();
		
		assertEquals(new TestKey2(3,"C",(short)4,5L,6.7,false,8,(short)9,10L,11.12,true),value.getK1());
	}
	
	@Test
	public void test3() throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = double.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("value", 1);
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
	}
	
	@Test
	public void test4() throws SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = Double.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("value", 111);
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
		assertEquals(new Double(111), obj);
	}
	
	@Test
	public void test5() throws ClassNotFoundException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = String.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("value", "abcdefghijk");
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
		assertEquals("abcdefghijk", obj);
		
	}
	
	@Test
	public void test6() throws ClassNotFoundException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = Date.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("", "2011-06-12");
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
		assertEquals(new GregorianCalendar(2011,Calendar.JUNE,12).getTime(), obj);
		
	}
	
	@Test
	public void test7() throws ClassNotFoundException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = TestKey5.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("k1", "2011-06-12");
		props.put("k2", "2011-06-18");
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
		assertEquals(new TestKey5(new GregorianCalendar(2011,Calendar.JUNE,12).getTime(),
				new GregorianCalendar(2011,Calendar.JUNE,18).getTime()), obj);
		
	}
	
	@Test
	public void test8() throws ClassNotFoundException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
		String className = PriceSource.class.getName();
		
		Map<String,Object> props = new HashMap<String, Object>();
		props.put("value", "BLOOMBERG");
		
		ObjectBuilder objBuilder = new ObjectBuilder(className, props);
		Object obj = objBuilder.build();
		
		System.out.println(obj);
		assertNotNull(obj);
		assertEquals(PriceSource.BLOOMBERG, obj);
		
	}
	
	
}
