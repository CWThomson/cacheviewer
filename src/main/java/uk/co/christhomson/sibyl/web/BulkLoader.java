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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.sample.loader.InstrumentPriceBulkLoader;

/*
BulkLoader
 Servlet to bulk load test data into test cache
  - currrent loads random price data for a number of securities

Copyright (C) 2011 Chris Thomson
*/
public class BulkLoader extends HttpServlet {

	private static final long serialVersionUID = 7950762067922214478L;
	
	private CacheConnector connector = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String connectorName = System.getProperty("sibyl.connector");
		try {
			connector = ConnectorBuilder.getConnector(connectorName);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		InstrumentPriceBulkLoader priceLoader = new InstrumentPriceBulkLoader(connector);
		try {
			priceLoader.loadAll("TEST_PRICE_CACHE");
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		
		resp.getOutputStream().write("Prices have been loaded into the cache".getBytes());
	}

}
