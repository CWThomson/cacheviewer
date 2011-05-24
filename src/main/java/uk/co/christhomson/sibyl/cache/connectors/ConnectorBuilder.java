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
