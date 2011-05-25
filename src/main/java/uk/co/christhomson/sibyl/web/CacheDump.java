package uk.co.christhomson.sibyl.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;

/*
CacheDump
 Servlet to display all data in a cache

Copyright (C) 2011 Chris Thomson
*/
public class CacheDump extends HttpServlet {

	private static final long serialVersionUID = 2630349905846997736L;
	
	private CacheConnector connector = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String connectorName = System.getProperty("connector");
		try {
			connector = ConnectorBuilder.getConnector(connectorName);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String cacheName = req.getParameter("cache");
		
		try {
			Map<?, ?> cache = connector.getAll(cacheName);
			
			OutputStream os = resp.getOutputStream();
	
			os.write("<html><body>".getBytes());
			
			Set entries = cache.entrySet();
			for (Object entry : entries) {
				os.write(entry.toString().getBytes());
				os.write("<br/>".getBytes());
			}
			
			os.write("</body></html>".getBytes());
		}
		catch (CacheException ex) {
			throw new ServletException(ex);
		}
		 
	}

}
