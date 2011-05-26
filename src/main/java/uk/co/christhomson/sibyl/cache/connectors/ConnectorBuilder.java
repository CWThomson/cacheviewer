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

package uk.co.christhomson.sibyl.cache.connectors;

public class ConnectorBuilder {
	public static CacheConnector getConnector() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String className = "uk.co.christhomson.sibyl";
		return getConnector(className);
	}
	
	public static CacheConnector getConnector(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> cls = Class.forName(className);
		CacheConnector connector = (CacheConnector) cls.newInstance();
		return connector;
	}
}
