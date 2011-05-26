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

package uk.co.christhomson.sibyl.sample.loader;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPrice;
import uk.co.christhomson.sibyl.sample.objects.InstrumentPriceKey;
import uk.co.christhomson.sibyl.sample.objects.PriceSource;

/*
 InstrumentPriceBulkLoader
 Bulk loader that generates a series of random stock prices 
 for a given security 

 Copyright (C) 2011 Chris Thomson
 */
public class InstrumentPriceBulkLoader {

	private CacheConnector connector = null;

	public static final void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, CacheException {
		String connectorName = args[0];
		String cacheName = args[1];
		
		CacheConnector connector = ConnectorBuilder.getConnector(connectorName);
		InstrumentPriceBulkLoader loader = new InstrumentPriceBulkLoader(connector);
		loader.loadAll(cacheName);
	}

	public InstrumentPriceBulkLoader(CacheConnector connector) {
		this.connector = connector;
	}

	public void generatePrices(String cacheName, String ticker, PriceSource source) throws CacheException {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2010, Calendar.JANUARY, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Random rand = new Random();

		Map<InstrumentPriceKey, InstrumentPrice> prices = new HashMap<InstrumentPriceKey, InstrumentPrice>();

		double price = rand.nextInt(1000);

		for (int i = 0; i < 10; i++) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date date = cal.getTime();
			
			price = price + ((rand.nextDouble() - 0.5) * (price / 10));
			InstrumentPriceKey key = new InstrumentPriceKey(ticker, date,
					source);
			InstrumentPrice value = new InstrumentPrice(price);

			prices.put(key, value);
		}

		connector.putAll(cacheName, prices);
	}

	public void loadAll(String cacheName) throws CacheException {
		generatePrices(cacheName,"VOD.L", PriceSource.BLOOMBERG);
		generatePrices(cacheName,"VOD.L", PriceSource.REUTERS);
		generatePrices(cacheName,"RBS.L", PriceSource.BLOOMBERG);
		generatePrices(cacheName,"RBS.L", PriceSource.REUTERS);
		generatePrices(cacheName,"BT.L", PriceSource.BLOOMBERG);
		generatePrices(cacheName,"BT.L", PriceSource.REUTERS);
		generatePrices(cacheName,"IBM.N", PriceSource.BLOOMBERG);
		generatePrices(cacheName,"IBM.N", PriceSource.REUTERS);
		generatePrices(cacheName,"III.L", PriceSource.BLOOMBERG);
		generatePrices(cacheName,"III.L", PriceSource.REUTERS);
	}
}
