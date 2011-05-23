package uk.co.christhomson.reflection.builders;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import uk.co.christhomson.date.utilities.DateParser;

/**
 * Build an object given a set of properties
 * 
 * @author Chris Thomson
 * 
 * Copyright (C) 2011 Chris Thomson
 */
public class ObjectBuilder {

	private static final Logger log = Logger.getLogger(ObjectBuilder.class);

	private Class cls = null;
	private Map<String, Object> properties = null;

	public ObjectBuilder(String className, Map<String, Object> properties)
			throws ClassNotFoundException {
		this(ClassBuilder.convertToObject(ClassBuilder
				.getClassFromName(className)), properties);
	}

	public ObjectBuilder(Class cls, Map<String, Object> properties)
			throws ClassNotFoundException {
		this.cls = cls;
		this.properties = properties;
		System.out.println(properties);
	}

	@SuppressWarnings("unchecked")
	public Object build() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SecurityException,
			NoSuchFieldException, ParseException {

		Object obj = null;

		if (cls.equals(double.class) || cls.equals(Double.class)) {
			obj = new Double(0);
			processFields(obj);
		} else if (cls.equals(int.class) || cls.equals(Integer.class)) {
			obj = new Integer(0);
			processFields(obj);
		} else if (cls.equals(short.class) || cls.equals(Short.class)) {
			obj = new Short((short) 0);
			processFields(obj);
		} else if (cls.equals(byte.class) || cls.equals(Byte.class)) {
			obj = new Byte((byte) 0);
			processFields(obj);
		} else if (cls.equals(long.class) || cls.equals(Long.class)) {
			obj = new Long(0);
			processFields(obj);
		} else if (cls.equals(boolean.class) || cls.equals(Boolean.class)) {
			obj = new Boolean(false);
			processFields(obj);
		} else if (cls.equals(String.class)) {
			if (properties.containsKey("value"))
				obj = new String(properties.get("value").toString());
			else
				obj = "";
		} else if (cls.equals(Date.class)) {
			if (properties.get("") != null) {
				obj = new DateParser().parse(properties.get("")
						.toString());
			}
		} else if (cls.isEnum()) {
			if (properties.get("value") != null) {
				obj = Enum.valueOf(cls, properties.get("value").toString());
			}
		} else {
			obj = cls.newInstance();
			processFields(obj);
		}

//		processFields(obj);

		return obj;
	}

	private void processFields(Object obj) throws IllegalAccessException,
			ClassNotFoundException, InstantiationException, ParseException {
		for (String fieldName : properties.keySet()) {
			try {
				processField(cls, obj, fieldName);
			} catch (NoSuchFieldException e) {
				log.debug(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void processField(Class cls, Object obj, String fieldName)
			throws NoSuchFieldException, IllegalAccessException,
			SecurityException, ClassNotFoundException, InstantiationException,
			ParseException {
		Field field = cls.getDeclaredField(fieldName);
		field.setAccessible(true);

		Class fieldType = field.getType();
		String value = properties.get(fieldName).toString();

		if (fieldType.equals(int.class)) {
			setInt(obj, field, value);
		} else if (fieldType.equals(Integer.class)) {
			setInteger(obj, field, value);
		} else if (fieldType.equals(double.class)) {
			setDoublePrimitive(obj, field, value);
		} else if (fieldType.equals(Double.class)) {
			setDouble(obj, field, value);
		} else if (fieldType.equals(String.class)) {
			setObject(obj, field, value);
		} else if (fieldType.equals(long.class)) {
			setLongPrimitive(obj, field, value);
		} else if (fieldType.equals(Long.class)) {
			setLong(obj, field, value);
		} else if (fieldType.equals(boolean.class)) {
			setBooleanPrimitive(obj, field, value);
		} else if (fieldType.equals(Boolean.class)) {
			setBoolean(obj, field, value);
		} else if (fieldType.equals(short.class)) {
			setShortPrimitive(obj, field, value);
		} else if (fieldType.equals(Short.class)) {
			setShort(obj, field, value);
		} else if (fieldType.equals(Date.class)) {
			setDate(obj, field, value);
		} else if (fieldType.isArray()) {
			// array
		} else if (fieldType.isEnum()) {
			setEnum(obj, field, Enum.valueOf(fieldType, value));
		} else {
			// object is not a prmitive
			Map<String, Object> subProperties = (Map<String, Object>) properties
					.get(fieldName);
			ObjectBuilder builder = new ObjectBuilder(fieldType, subProperties);
			Object subObj = builder.build();
			field.set(obj, subObj);
		}
	}

	private void setEnum(Object obj, Field field, Enum value) throws IllegalArgumentException, IllegalAccessException {
		field.set(obj, value);
	}

	private void setShort(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, new Short(Short.parseShort(value)));
		}
	}

	private void setShortPrimitive(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.setShort(obj, Short.parseShort(value));
		}
	}

	private void setBoolean(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, new Boolean(Boolean.parseBoolean(value)));
		}
	}

	private void setBooleanPrimitive(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.setBoolean(obj, Boolean.parseBoolean(value));
		}
	}

	private void setLong(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, new Long(Long.parseLong(value)));
		}
	}

	private void setLongPrimitive(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.setLong(obj, Long.parseLong(value));
		}
	}

	private void setObject(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, value);
		}
	}

	private void setDouble(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, new Double(Double.parseDouble(value)));
		}
	}

	private void setDoublePrimitive(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.setDouble(obj, Double.parseDouble(value));
		}
	}

	private void setInteger(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.set(obj, new Integer(Integer.parseInt(value)));
		}
	}

	private void setInt(Object obj, Field field, String value)
			throws IllegalAccessException {
		if (value != null && value.length() > 0) {
			field.setInt(obj, Integer.parseInt(value));
		}
	}

	private void setDate(Object obj, Field field, String value)
			throws IllegalAccessException, IllegalArgumentException, ParseException {
		if (value != null && value.length() > 0) {
			field.set(obj, new DateParser().parse(value));
		}
	}

}
