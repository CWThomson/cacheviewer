package uk.co.christhomson.sibyl.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;

import org.jdom.Element;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.exception.InvalidCacheNameException;
import uk.co.christhomson.xsl.utilities.XsltHelper;

public abstract class SibylXslServlet extends HttpServlet {

	private static final long serialVersionUID = -488797941075820407L;
	
	protected XsltHelper xsltHelper = null;
	protected CacheConnector connector = null;
	
	public abstract String getXsl(ServletConfig config);
	public abstract String execute(Map<String, String[]> params, HttpSession session) throws TransformerException, IOException;

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
		
		String xsl = getXsl(config);
		try {
			InputStream is = this.getClass().getResourceAsStream(xsl);
			xsltHelper = new XsltHelper(is,new ServletContextResolver());
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, String[]> params = (Map<String, String[]>) req
				.getParameterMap();

		try {
			String output = execute(params,req.getSession());

			resp.getOutputStream().write(output.getBytes());

		} catch (TransformerException e) {
			throw new ServletException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			throw new ServletException(e.getMessage(), e);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req, resp);
	}

	protected String getCacheName(Map<String, String[]> params, Element errorsXml)
			throws InvalidCacheNameException {
		String[] cacheNameArr = params.get("cacheName");
		if (cacheNameArr == null || cacheNameArr.length == 0 || cacheNameArr[0].length() == 0) {
			addError(errorsXml, "No cache name has been specified");
			return null;
		}
		else {
			return cacheNameArr[0];
		}
	}


	protected Element getCacheXml(String cacheName) {
		Element cacheXml = new Element("Cache");
		if (cacheName != null) {
			cacheXml.setAttribute("name",cacheName);
		}
		return cacheXml;
	}

	public void addError(Element errorsXml, String msg) {
		Element error = new Element("Error");
		error.setText(msg);
		errorsXml.addContent(error);
	}

}
