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

package uk.co.christhomson.sibyl.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import uk.co.christhomson.reflection.builders.ClassBuilder;
import uk.co.christhomson.reflection.builders.ObjectBuilder;
import uk.co.christhomson.reflection.builders.XmlObjectBuilder;
import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.exception.InvalidCacheNameException;
import uk.co.christhomson.sibyl.utilities.PropertyBuilder;
import uk.co.christhomson.xsl.utilities.XsltHelper;

/*
 ExtractorServlet
 Servlet providing user inferface for extracting data from cache
 using XSL templates to display HTML

 Copyright (C) 2011 Chris Thomson
 */
public class ExtractorServlet extends HttpServlet {

	private static final long serialVersionUID = 1746417206634639979L;

	private static final Logger log = Logger.getLogger(ExtractorServlet.class);

	private XsltHelper xsltHelper = null;
	
	private CacheConnector connector = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");

		String connectorName = System.getProperty("sibyl.connector");
		try {
			connector = ConnectorBuilder.getConnector(connectorName);
		}
		catch (Exception ex) {
			throw new ServletException(ex);
		}

		reloadXslt(config);
	}

	private void reloadXslt(ServletConfig config) throws ServletException {
		String xsl = "xsl/extractor.xsl";
		try {
			xsltHelper = new XsltHelper(xsl);
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		reloadXslt(this.getServletConfig());

		String className = req.getParameter("className");

		HttpSession session = req.getSession(true);
		Set<String> classHistory = addClassToHistory(session, className);

		Map<String, String[]> params = (Map<String, String[]>) req
				.getParameterMap();

		try {
			String output = extract(className, params, classHistory);

			resp.getOutputStream().write(output.getBytes());

		} catch (TransformerException e) {
			throw new ServletException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ServletException(e.getMessage(), e);
		}
	}

	private Set<String> addClassToHistory(HttpSession session, String className) {
		Set<String> classHistory = (Set<String>) session
				.getAttribute("classHistory");
		if (classHistory == null) {
			classHistory = new HashSet<String>();
		}

		if (className != null && className.length() > 0) {
			try {
				ClassBuilder.getClassFromName(className);
				classHistory.add(className);
			} catch (ClassNotFoundException e) {
				// ignore exception
			}
		}

		session.setAttribute("classHistory", classHistory);
		return classHistory;
	}

	private Object extractObject(String cacheName, Object key)
			throws SecurityException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchFieldException, CacheException, ParseException {

		Object result = connector.get(cacheName, key);
		return result;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req, resp);
	}

	public String extract(String className, Map<String, String[]> params,
			Collection<String> classHistory) throws IOException,
			TransformerException {
		Map<String, String> xslParams = null;
		Element msgsXml = new Element("Messages");
		Element errorsXml = new Element("Errors");
		Element classHistElem = generateClassHistory(classHistory);
		Element keyXml = null;
		Element cacheXml = null;
		Element resultXml = null;

		Map<String, Object> propMap = PropertyBuilder.processProperties(params);

		if (className != null) {
			try {
				Class cls = ClassBuilder.getClassFromName(className, false);
				ObjectBuilder objBuilder = new ObjectBuilder(cls, propMap);
				Object key = objBuilder.build();

				keyXml = generateXmlKey(cls, key, errorsXml);

				if (keyXml != null && keyXml.getChildren().size() > 0) {
					cacheXml = new Element("Cache");
					if (params.containsKey("cacheName")) {
						cacheXml.setAttribute("name",
								params.get("cacheName")[0]);
					}
				}

				if (params.get("extract") != null) {
					try {
						String cacheName = getCacheName(params);
						resultXml = generateResult(cacheName, key, errorsXml);
					} catch (InvalidCacheNameException e) {
						Element error = new Element("Error");
						error
								.setText("Please enter a cache name to begin the extraction from the cache");
						errorsXml.addContent(error);
					}
				}

				xslParams = new HashMap<String, String>();
				xslParams.put("className", className);
			} catch (Exception e) {
				e.printStackTrace();
				addError(errorsXml, e.getClass().getName() + ":"
						+ e.getMessage());
			}
		} else {
			Element msg = new Element("Message");
			msg
					.setText("Please search for a class name to begin the extraction from the cache");
			msgsXml.addContent(msg);
		}

		Document xmlDoc = createXmlDoc(cacheXml, keyXml, resultXml, msgsXml,
				errorsXml, classHistElem);

		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		xmlOutput.output(xmlDoc, System.out);

		String output = xsltHelper.processXml(xmlDoc, xslParams);
		return output;
	}

	private String getCacheName(Map<String, String[]> params)
			throws InvalidCacheNameException {
		String[] cacheNameArr = params.get("cacheName");
		if (cacheNameArr == null || cacheNameArr.length == 0) {
			throw new InvalidCacheNameException(
					"No cache name has been specified");
		}

		return params.get("cacheName")[0];
	}

	private Element generateXmlKey(Class cls, Object key, Element errorsXml) {
		Element keyXml = new Element("Key");

		try {
			Element objectXml = new XmlObjectBuilder(cls, key)
					.createXmlObject();
			keyXml.addContent(objectXml);
		} catch (Exception e) {
			e.printStackTrace();
			addError(errorsXml, e.getClass().getName() + ":" + e.getMessage());
		}
		return keyXml;
	}

	private Element generateResult(String cacheName, // String className,
			Object key, Element errorsXml) {
		Element resultElem = null;

		// retrieve data from cache
		try {
			Object obj = extractObject(cacheName, key);
			if (obj != null) {
				resultElem = new Element("Result");

				XmlObjectBuilder xmlBuilder = new XmlObjectBuilder(obj
						.getClass(), obj);
				Element resultXmlObj = xmlBuilder.createXmlObject();
				resultXmlObj.setAttribute("name", obj.toString());
				resultElem.addContent(resultXmlObj);
			} else {
				Element error = new Element("Error");
				error.setText("No data found for specified key");
				errorsXml.addContent(error);
			}
		} catch (CacheException e) {
			addError(errorsXml, e.getMessage());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			addError(errorsXml, e.getClass().getName() + ":sijjd:"
					+ e.getMessage());
		}

		return resultElem;
	}

	private void addError(Element errorsXml, String msg) {
		Element error = new Element("Error");
		error.setText(msg);
		errorsXml.addContent(error);
	}

	private Element generateClassHistory(Collection<String> classHistory) {
		Element elem = new Element("ClassHistory");

		for (String className : classHistory) {
			Element classNameElem = new Element("Class");
			classNameElem.setAttribute("name", className);
			classNameElem.setAttribute("shortname", ClassBuilder
					.getShortType(className));
			elem.addContent(classNameElem);
		}

		return elem;
	}

	private Document createXmlDoc(Element cacheXml, Element keyXml,
			Element resultXml, Element msgsXml, Element errorsXml,
			Element classHistory) {
		Document xmlDoc = new Document();
		Element root = new Element("Extraction");

		if (cacheXml != null) {
			root.addContent(cacheXml);
		}

		if (keyXml != null && keyXml.getChildren().size() > 0) {
			root.addContent(keyXml);
		}

		System.out.println("ResultElem:" + resultXml);
		if (resultXml != null) {
			root.addContent(resultXml);
		}

		if (msgsXml.getChildren().size() > 0) {
			root.addContent(msgsXml);
		}

		if (errorsXml.getChildren().size() > 0) {
			root.addContent(errorsXml);
		}

		root.addContent(classHistory);

		xmlDoc.setRootElement(root);

		return xmlDoc;
	}
}
