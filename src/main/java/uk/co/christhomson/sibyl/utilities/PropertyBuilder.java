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

package uk.co.christhomson.sibyl.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import uk.co.christhomson.reflection.builders.ClassBuilder;

/*
PropertyBuilder
 Build a set of properties to represent a class and object

Copyright (C) 2011 Chris Thomson
*/
public class PropertyBuilder {

	public static Map<String, Object> processProperties(Map<String, String[]> params) {
		Properties props = extractProperties(params);
		return processProperties(props);
	}
	
	public static Map<String, Object> processProperties(Properties props) {
		Map<String, Object> propMap = new HashMap<String, Object>();

		for (String key : props.stringPropertyNames()) {
			String value = props.getProperty(key);
			addProperty(propMap, key, value);
		}

		return propMap;
	}

	public static Properties extractProperties(Map<String, String[]> params) {
		Properties objParams = new Properties();

		for (String key : params.keySet()) {
			if (key.startsWith("input.")) {
				String cleanKey = key.substring(6);
				while (cleanKey.indexOf(".") == 0) {
					cleanKey = cleanKey.substring(1);
				}

				objParams.put(cleanKey, params.get(key)[0]);
			}
		}
		
		return objParams;
	}

	@SuppressWarnings("unchecked")
	private static void addProperty(Map<String, Object> propMap, String key,
			String value) {
		if (key.indexOf(".") == -1) {
			propMap.put(key, value);
		} else {
			String parentKey = key.substring(0, key.indexOf("."));
			Map<String, Object> subPropMap = (Map<String, Object>) propMap
					.get(parentKey);
			if (subPropMap == null) {
				subPropMap = new HashMap<String, Object>();
			}

			propMap.put(parentKey, subPropMap);
			addProperty(subPropMap, key.substring(key.indexOf(".") + 1), value);
		}
	}

	public static Properties buildPropertiesFromUserInput(String className) throws IOException, ClassNotFoundException {
		Class<?> cls = ClassBuilder.getClassFromName(className);

		Properties props = new Properties();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		buildPropertiesFromUserInput(cls,"",br,props);

		br.close();
		
		return props;
	}
	
	private static void buildPropertiesFromUserInput(Class<?> cls, String prefix, BufferedReader br, Properties props) throws IOException, ClassNotFoundException {
		System.out.println("Generating properties for " + cls.getName());
		for (Field fld : cls.getDeclaredFields()) {
			Class<?> fldType = fld.getType();
			if (fldType.isPrimitive() ||
					fldType.equals(Integer.class) ||
					fldType.equals(Double.class) ||
					fldType.equals(Long.class) ||
					fldType.equals(String.class) ||
					fldType.equals(Short.class) ||
					fldType.equals(Boolean.class) ||
					fldType.equals(Character.class)) {
				System.out.println("Specifiy the value for field: " + prefix + fld.getName() + " ( " + fldType.getName() + " ) ? ");
				String input = br.readLine();			
				props.put(prefix + fld.getName(), input);
			}
			else if (fldType.equals(Date.class)) {
				System.out.println("Specifiy the value for field: " + prefix + fld.getName() + " ( " + fldType.getName() + " ) [yyyy-MM-dd] ? ");
				String input = br.readLine();			
				props.put(prefix + fld.getName(), input);
			}
			else if (fldType.isEnum()) {
				String options = "";
				Object[] constants = fldType.getEnumConstants();
				for (Object constant : constants) {
					options = options + "," + constant;
				}
				
				System.out.println("Specifiy the value for field: " + prefix + fld.getName() + " ( " + fldType.getName() + " ) [" + options.substring(1) + "] ? ");
				String input = br.readLine();			
				props.put(prefix + fld.getName(), input);
			}
			else {
				buildPropertiesFromUserInput(fldType,prefix + fld.getName() + ".",br,props);
			}
		}
	}
}
