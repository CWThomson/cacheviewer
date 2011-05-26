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

import java.util.HashMap;
import java.util.Map;

/*
 ClassBuilder
  Wrapper class to build an object from a properties file
  and extract value from a cache
 
 Copyright (C) 2011 Chris Thomson
 */
public class ClassBuilder {

	private static Map<String,Class<?>> primitiveClasses = null;
	private static Map<Class<?>,Class<?>> primitiveMappings = null;

	static {
		primitiveClasses = new HashMap<String,Class<?>>();
		
		primitiveClasses.put("byte", byte.class);
	    primitiveClasses.put("short", short.class);
	    primitiveClasses.put("char", char.class);
	    primitiveClasses.put("int", int.class);
	    primitiveClasses.put("long", long.class);
	    primitiveClasses.put("float", float.class);
	    primitiveClasses.put("double", double.class);
	}

	static {
		primitiveMappings = new HashMap<Class<?>,Class<?>>();
		
		primitiveMappings.put(byte.class,Byte.class);
		primitiveMappings.put(short.class,Short.class);
		primitiveMappings.put(char.class,Character.class);
		primitiveMappings.put(int.class,Integer.class);
		primitiveMappings.put(long.class,Long.class);
		primitiveMappings.put(float.class,Float.class);
		primitiveMappings.put(double.class,Double.class);
	}

	public static Class<?> getClassFromName(String className) throws ClassNotFoundException {
		if (className != null) {
			Class<?> cls = primitiveClasses.get(className);
			if (cls == null) {
				cls = Class.forName(className);
			}
			
			return cls;
		}
		
		return null;
	}

	public static Class<?> getClassFromName(String className, boolean allowPrimitives) throws ClassNotFoundException {
		Class<?> cls = primitiveClasses.get(className);
		if (cls == null) {
			cls = Class.forName(className);
		}
		else {
			if (!allowPrimitives)
				cls = convertToObject(cls);
		}
		
		return cls;
	}
	
	public static Class<?> convertToObject(Class<?> primitive) {
		if (primitive != null && primitive.isPrimitive()) {
			return primitiveMappings.get(primitive);
		}
		else {
			return primitive;
		}
	}

	public static String getShortType(String type) {
		if (type.indexOf(".") != -1) {
			return type.substring(type.lastIndexOf(".")+1);
		}
		
		return type;
	}

}
