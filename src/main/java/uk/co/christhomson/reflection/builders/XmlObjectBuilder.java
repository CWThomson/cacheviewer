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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.Element;

import uk.co.christhomson.sibyl.exception.CacheException;


/*
XmlObjectBuilder
 Build an XML object representing a class and an object

Copyright (C) 2011 Chris Thomson
*/
public class XmlObjectBuilder {
	
	private static final Logger log = Logger.getLogger(XmlObjectBuilder.class);
	
	private Class<?> cls = null;
	private Object obj = null;
	
	public XmlObjectBuilder(Class<?> cls, Object obj) throws CacheException {
		this.cls = cls;
		this.obj = obj;
		
		if (cls == null) {
			throw new CacheException("Invalid class name specified");
		}
		
		if (cls.isPrimitive()) {
			this.cls = ClassBuilder.convertToObject(cls);
		}
	}

	public XmlObjectBuilder(String className) throws ClassNotFoundException, CacheException {
		this(className,null);
	}

	public XmlObjectBuilder(String className, Object obj) throws ClassNotFoundException, CacheException {
		this(ClassBuilder.convertToObject(ClassBuilder.getClassFromName(className)),obj);
	}
	
	public Element createXmlObject() throws IllegalArgumentException, IllegalAccessException {
		Set<Class<?>> parentClasses = new HashSet<Class<?>>();
		
		Element elem = processClass(cls,null,parentClasses,obj,true);
		
		return elem;
	}

	private Element processClass(Class<?> cls, String name, Set<Class<?>> parentClasses, Object obj, boolean isRoot) throws IllegalArgumentException, IllegalAccessException {
		
		log.debug(cls + "-" + obj);
		
		Element elem = new Element("Class");
		
		if (name != null) {
			elem.setAttribute("name", name);
		}
		
		elem.setAttribute("type", cls.getName());
		elem.setAttribute("shorttype", ClassBuilder.getShortType(cls.getName()));
		
		Set<Class<?>> subParentClasses = new HashSet<Class<?>>(parentClasses);
		subParentClasses.add(cls);
		
//		if (!cls.equals(String.class) || !isRoot) {
		if (cls.isEnum()) {
			elem.setAttribute("isenum", "true");
			
			if (obj != null) {
				elem.setAttribute("value", obj.toString());
			}
			
			Object[] constants = cls.getEnumConstants();
			for (Object constant : constants) {
				Element option = new Element("Option");
				option.setText(constant.toString());
				elem.addContent(option);
			}
		}
		else if (cls.equals(String.class)) {
			Element fieldElem = createField("value", String.class.getName(), obj);
			if (fieldElem != null) {
				elem.addContent(fieldElem);
			}
		}
		else if (cls.equals(Date.class)) {
//			Element fieldElem = createField("", Date.class.getName(), obj);
//			if (fieldElem != null) {
//				elem.addContent(fieldElem);
//			}
			if (obj != null) {
				elem.setAttribute("value", formatDate((Date)obj));
			}
		}
		else if (!((cls.equals(Date.class) || cls.equals(String.class)) && isRoot)) {
			do {
				for (Field field : cls.getDeclaredFields()) {
					if ((field.getModifiers() & Modifier.STATIC) == 0) {
						Object subObj = null;
						if (obj != null) {
							field.setAccessible(true);
							subObj = field.get(obj);
						}
						
						Element fieldElem = processField(field, subParentClasses,subObj);
						if (fieldElem != null) {
							elem.addContent(fieldElem);
						}
					}
				}
				
				cls = cls.getSuperclass();
			}
			while (cls != null && !cls.equals(Object.class));
		}
		
		return elem;
	}

	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	private Element processField(Field field, Set<Class<?>> parentClasses, Object obj) throws IllegalArgumentException, IllegalAccessException {

		Class<?> fieldType = field.getType();
		if (!parentClasses.contains(fieldType)) {
			if (fieldType.equals(int.class) ||
					fieldType.equals(Integer.class) ||
					fieldType.equals(double.class) ||
					fieldType.equals(Double.class) ||
					fieldType.equals(String.class) ||
					fieldType.equals(long.class) ||
					fieldType.equals(Long.class) ||
					fieldType.equals(boolean.class) ||
					fieldType.equals(Boolean.class) ||
					fieldType.equals(short.class) ||
					fieldType.equals(Short.class)) {
			
				return createField(field.getName(),fieldType.getName(),obj);
			}
			else if (fieldType.isArray()) {
				Class<?> arrayType = fieldType.getComponentType();
				if (arrayType.equals(char.class)) {
					if (obj != null) {
						return createField(field.getName(),arrayType.getName() + "[]",new String((char[])obj));
					}
					else {
						return createField(field.getName(),arrayType.getName() + "[]",obj);
					}
				}
				else {
					return createField(field.getName(),arrayType.getName() + "[]",obj);
				}
			}
			else if (Collection.class.isAssignableFrom(fieldType)) {
				Collection col = (Collection) obj;
//				Element elem = createField(field.getName(),"Collection",null);
				Element elem = new Element("Class");				
				elem.setAttribute("name", field.getName());
				elem.setAttribute("type", fieldType.getName());
				elem.setAttribute("shorttype", ClassBuilder.getShortType(fieldType.getName()));
				
				if (col != null) {
					for (Object value : col) {
						Element subElem = processClass(value.getClass(),value.toString(),parentClasses, value, false);
						elem.addContent(subElem);
					}
				}
				return elem;
			}
			else if (Map.class.isAssignableFrom(fieldType)) {
				Map map = (Map) obj;
				Element elem = new Element("Class");				
				elem.setAttribute("name", field.getName());
				elem.setAttribute("type", fieldType.getName());
				elem.setAttribute("shorttype", ClassBuilder.getShortType(fieldType.getName()));
				
				if (map != null) {
					for (Object key : map.keySet()) {
						Object value = map.get(key);
						Element subElem = processClass(value.getClass(),key.toString(),parentClasses, value, false);
						elem.addContent(subElem);
					}
				}
				return elem;
			}
			else {
				return processClass(field.getType(),field.getName(),parentClasses, obj, false);
			}
		}
		else {
			return null;
		}
	}

	private Element createField(String name, String type, Object obj) {
		Element elem = new Element("Field");
		
		if (name != null) {
			elem.setAttribute("name",name);
		}
		
		elem.setAttribute("type", type);
		elem.setAttribute("shorttype", ClassBuilder.getShortType(type));
		
		if (obj != null) 
			elem.setAttribute("value", obj.toString());
	
		log.debug("Creating field:" + name + "(" + type + ")" + "=" + obj);
		
		return elem;
	}
}
