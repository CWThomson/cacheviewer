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

package uk.co.christhomson.xsl.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.transform.JDOMSource;

/*
XsltHelper
 Utility class to apply XSL transformations

Copyright (C) 2011 Chris Thomson
*/
public class XsltHelper {

	private TransformerFactory tFactory = TransformerFactory.newInstance();
	private Templates cachedXslt;
	private Transformer transformer;

	public XsltHelper(String xslFile, URIResolver uriResolver) throws ServletException, FileNotFoundException {
		loadTemplate(xslFile, uriResolver);
	}

	public XsltHelper(InputStream is, URIResolver uriResolver) throws ServletException, FileNotFoundException {
		loadTemplate(is,uriResolver);
	}

	public void loadTemplate(InputStream is, URIResolver uriResolver) throws ServletException {
		StreamSource xsltSource;
		
		try {
			if (uriResolver != null) {
				tFactory.setURIResolver(uriResolver);
			}
			
			xsltSource = new StreamSource(is);
			cachedXslt = tFactory.newTemplates(xsltSource);
			
			transformer = cachedXslt.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new ServletException(e.getMessage(),e);
		}
	}

	public void loadTemplate(String xslFile, URIResolver uriResolver) throws ServletException, FileNotFoundException {
		loadTemplate(new FileInputStream(xslFile), uriResolver);
	}

	public String processXml(String xmlFile) throws TransformerException, JDOMException, IOException {
		return processXml(xmlFile,null);
	}
	
	public String processXml(String xmlFile, Map<String, String> params) throws TransformerException, JDOMException, IOException {
		Document inputXmlDoc = new SAXBuilder().build(new File(xmlFile));
		return processXml(inputXmlDoc,params);
	}

	public String processXml(InputStream is, Map<String, String> params) throws TransformerException, JDOMException, IOException {
		Document inputXmlDoc = new SAXBuilder().build(is);
		return processXml(inputXmlDoc,params);
	}
	
	public String processXml(Document xmlDoc, Map<String, String> params) throws TransformerException {
		
		JDOMSource xmlSource = new JDOMSource(xmlDoc);

		// Make the output result for the finished document using
		// the HTTPResponse OutputStream
		StringWriter sw = new StringWriter();
		StreamResult xmlResult = new StreamResult(sw);// response.getOutputStream());

		// Do the transform
		if (params != null) {
			for (String key : params.keySet()) {
				transformer.setParameter(key, params.get(key));
			}
		}
		
		transformer.transform(xmlSource, xmlResult);
		
		String outputXml = sw.toString();
		
		return outputXml;
	}
}
