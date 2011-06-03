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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import uk.co.christhomson.reflection.builders.ObjectBuilder;
import uk.co.christhomson.sibyl.cache.connectors.CacheConnector;
import uk.co.christhomson.sibyl.cache.connectors.ConnectorBuilder;
import uk.co.christhomson.sibyl.exception.CacheException;
import uk.co.christhomson.sibyl.utilities.PropertyBuilder;

/*
 CacheViewer
  Command line utility to extract data from cache from command line

 Copyright (C) 2011 Chris Thomson
 */
public class CacheViewer {

	public static void main(String[] args) throws SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchFieldException,
			FileNotFoundException, IOException, CacheException, ParseException {
		run(args);
	}
	
	public static void run(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, SecurityException, CacheException, NoSuchFieldException, ParseException {
		
		if (!(args.length == 3 || args.length == 2)) {
			usage();
			System.exit(0);
		}

		String cacheName = args[0];
		String className = args[1];
		
		String connectorName = System.getProperty("sibyl.connector");
		CacheConnector connector = ConnectorBuilder.getConnector(connectorName);
		
		Properties properties = null;
		
		Object value = null;
		
		if (args.length == 2) {
			// take user input to build properties
			properties = PropertyBuilder.buildPropertiesFromUserInput(className);
		}
		else if (args.length == 3) {
			String file = args[2];
			properties = new Properties();
			properties.load(new FileInputStream(file));
		}
		
		ObjectBuilder builder = new ObjectBuilder(className, PropertyBuilder.processProperties(properties));
		value = connector.get(cacheName,builder.build());
		
		System.out.println("Result:");
		System.out.println(value);
	}

	private static void usage() {
		System.out.println("********************************************");
		System.out
				.println("Usage: uk.co.christhomson.sibyl.commandline.CacheViewer <cache-name> <class-name> [<properties-file>]");
		System.out.println("********************************************");
	}

}
