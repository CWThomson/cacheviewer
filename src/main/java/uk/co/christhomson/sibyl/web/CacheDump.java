package uk.co.christhomson.sibyl.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/*
CacheDump
 Servlet to display all data in a cache

Copyright (C) 2011 Chris Thomson
*/
public class CacheDump extends HttpServlet {

	private static final long serialVersionUID = 2630349905846997736L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String cacheName = req.getParameter("cache");
		
		NamedCache cache = CacheFactory.getCache(cacheName);
		
		OutputStream os = resp.getOutputStream();
		
		Set entries = cache.entrySet();
		for (Object entry : entries) {
			os.write(entry.toString().getBytes());
			os.write("<br/>".getBytes());
		}
	}

}
