package uk.co.christhomson.sibyl.utilities;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import uk.co.christhomson.sibyl.utilities.PropertyBuilder;

public class TestPropertyBuilder {
	@Test
	public void test() {
		Properties props = new Properties();
		props.put("f", "1");
		props.put("a.b.a", "3");
		props.put("a.b.c", "6");
		props.put("a.b.d", "8");
		props.put("c", "9");
		props.put("d.b", "10");
		
		Map<String, Object> propMap1 = PropertyBuilder.processProperties(props);
		
		Map<String, Object> propMap2 = new HashMap<String, Object>();
		Map<String, Object> subPropMap1 = new HashMap<String, Object>();
		Map<String, Object> subPropMap2 = new HashMap<String, Object>();
		Map<String, Object> subPropMap3 = new HashMap<String, Object>();
		
		propMap2.put("f", "1");
		propMap2.put("c", "9");
		
		subPropMap2.put("a", "3");
		subPropMap2.put("c", "6");
		subPropMap2.put("d", "8");
		
		subPropMap1.put("b", subPropMap2);
		propMap2.put("a", subPropMap1);
		
		subPropMap3.put("b", "10");
		propMap2.put("d", subPropMap3);
	
		assertEquals(propMap2,propMap1);
	}
}
