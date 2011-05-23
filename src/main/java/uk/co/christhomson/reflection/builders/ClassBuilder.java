package uk.co.christhomson.reflection.builders;

import java.util.HashMap;
import java.util.Map;

/*
 ClassBuilder
  Wrapper class to build an object from a properties file
  and extract value from a Coherence cace
 
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
