package uk.co.christhomson.sibyl.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.christhomson.sibyl.sample.loader.InstrumentPriceBulkLoader;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/*
BulkLoader
 Servlet to bulk load test data into test Coherence cache
  - currrent loads random price data for a number of securities

Copyright (C) 2011 Chris Thomson
*/
public class BulkLoader extends HttpServlet {

	private static final long serialVersionUID = 7950762067922214478L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		NamedCache cache = CacheFactory.getCache("TEST_PRICE_CACHE");
		
		InstrumentPriceBulkLoader priceLoader = new InstrumentPriceBulkLoader(cache);
		priceLoader.generatePrices("VOD.L", PriceSource.BLOOMBERG);
		priceLoader.generatePrices("VOD.L", PriceSource.REUTERS);
		priceLoader.generatePrices("RBS.L", PriceSource.BLOOMBERG);
		priceLoader.generatePrices("RBS.L", PriceSource.REUTERS);
		priceLoader.generatePrices("BT.L", PriceSource.BLOOMBERG);
		priceLoader.generatePrices("BT.L", PriceSource.REUTERS);
		priceLoader.generatePrices("IBM.N", PriceSource.BLOOMBERG);
		priceLoader.generatePrices("IBM.N", PriceSource.REUTERS);
		priceLoader.generatePrices("III.L", PriceSource.BLOOMBERG);
		priceLoader.generatePrices("III.L", PriceSource.REUTERS);
		
		resp.getOutputStream().write("Prices have been loaded into the cache".getBytes());
	}

}
