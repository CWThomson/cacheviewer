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

	public XsltHelper(String xslFile) throws ServletException, FileNotFoundException {
		this(new FileInputStream(xslFile));
	}

	public XsltHelper(InputStream is) throws ServletException, FileNotFoundException {
		loadTemplate(is);
	}
	
	public void loadTemplate(InputStream is) throws ServletException {
		StreamSource xsltSource;
		
		try {
			xsltSource = new StreamSource(is);
			cachedXslt = tFactory.newTemplates(xsltSource);
			transformer = cachedXslt.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new ServletException(e.getMessage(),e);
		}
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
