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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import uk.co.christhomson.reflection.builders.XmlObjectBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.exception.InvalidCacheNameException;

/*
 QueryServlet
 Servlet providing user inferface for querying data from cache
 using XSL templates to display HTML

 Copyright (C) 2011 Chris Thomson
 */
public class QueryServlet extends SibylXslServlet {

	private static final long serialVersionUID = 7152092118250473123L;

	private static final Logger log = Logger.getLogger(QueryServlet.class);

	@Override
	public String getXsl(ServletConfig config) {
//		String xsl = config.getServletContext().getRealPath("/") + "/" + "xsl/query.xsl";
		String xsl = "query.xsl";
		
		return xsl;
	}

	@Override
	public String execute(Map<String, String[]> params, HttpSession session) throws IOException, TransformerException {

		Element msgsXml = new Element("Messages");
		Element errorsXml = new Element("Errors");
		
		Element cacheXml = new Element("Cache");
		Element queryXml = new Element("Query");
		Element resultsXml = null;
		
		Map<String, String> xslParams = null;
		
		try {

			resultsXml = null;
			
			if (params.get("submit") != null) {
				String cacheName = getCacheName(params,errorsXml);
				String query = getQuery(params,errorsXml);
				
				cacheXml = getCacheXml(cacheName);
				queryXml = getQueryXml(query);
				
				if (errorsXml.getChildren().size() == 0) {
					resultsXml = generateResult(cacheName, query, errorsXml);
				}
			}

			xslParams = new HashMap<String, String>();
		} catch (InvalidCacheNameException e) {
			e.printStackTrace();
			addError(errorsXml, "Please enter a cache name to begin the extraction from the cache");
		} catch (Exception e) {
			e.printStackTrace();
			addError(errorsXml, e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
		Document xmlDoc = createXmlDoc(cacheXml, queryXml, resultsXml, msgsXml,
				errorsXml);

		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		xmlOutput.output(xmlDoc, System.out);

		String output = xsltHelper.processXml(xmlDoc, xslParams);
		return output;
	}

	private Map<?,?> queryObject(String cacheName, String query)
			throws CacheException {

		Map<?,?> result = connector.query(cacheName, query);
		return result;
	}

	private Element getQueryXml(String query) {
		Element queryXml = new Element("Query");
		
		if (query != null) {
			queryXml.setAttribute("query",query);
		}
				
		return queryXml;
	}
	
	private String getQuery(Map<String, String[]> params, Element errorsXml) {
		String query = null;
		if (params.containsKey("query")) {
			query = params.get("query")[0];
		}
		else {
			addError(errorsXml, "No query has been specified");
		}
		
		return query;
	}

	private Element generateResult(String cacheName, 
			String query, Element errorsXml) {
		Element resultsElem = new Element("Results");

		// retrieve data from cache
		try {
			Map<?,?> results = queryObject(cacheName, query);
			if (results != null) {
				for (Object key : results.keySet()) {
					
					Object value = results.get(key);
					
					Element resultElem = new Element("Result");
					Element keyElem = new Element("Key");
					Element valueElem = new Element("Value");
					resultElem.addContent(keyElem);
					resultElem.addContent(valueElem);
					
					XmlObjectBuilder keyXmlBuilder = new XmlObjectBuilder(key
							.getClass(), key);
					Element keyXmlObj = keyXmlBuilder.createXmlObject();
					keyXmlObj.setAttribute("name", key.toString());
					keyElem.addContent(keyXmlObj);
					
					XmlObjectBuilder valueXmlBuilder = new XmlObjectBuilder(value
							.getClass(), value);
					Element valueXmlObj = valueXmlBuilder.createXmlObject();
					valueXmlObj.setAttribute("name", value.toString());
					valueElem.addContent(valueXmlObj);
					
					resultsElem.addContent(resultElem);
				}
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

		return resultsElem;
	}

	private Document createXmlDoc(Element cacheXml, Element queryXml,
			Element resultsXml, Element msgsXml, Element errorsXml) {
		Document xmlDoc = new Document();
		Element root = new Element("QueryRequest");

		if (cacheXml != null) {
			root.addContent(cacheXml);
		}

		if (queryXml != null) {
			root.addContent(queryXml);
		}

		System.out.println("ResultsElem:" + resultsXml);
		if (resultsXml != null) {
			root.addContent(resultsXml);
		}

		if (msgsXml.getChildren().size() > 0) {
			root.addContent(msgsXml);
		}

		if (errorsXml.getChildren().size() > 0) {
			root.addContent(errorsXml);
		}

		xmlDoc.setRootElement(root);

		return xmlDoc;
	}
}
