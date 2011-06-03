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

package uk.co.christhomson.sibyl.commandline;

import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.cache.connectors.HashMapConnector;
import uk.co.christhomson.sibyl.sample.loader.InstrumentPriceBulkLoader;

public class CacheViewerDemo {
	public static void main(String[] args) throws Exception {
		String cacheName = args[0];
		String connectorName = HashMapConnector.class.getName();
		
		CacheConnector connector = ConnectorBuilder.getConnector(connectorName);
		InstrumentPriceBulkLoader loader = new InstrumentPriceBulkLoader(connector);
		loader.loadAll(cacheName);
		
		CacheViewer.run(args);
	}
}
