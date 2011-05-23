package uk.co.christhomson.coherence.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.Member;
import com.tangosol.run.xml.XmlElement;

/*
 CacheInfo
 Utility cache to extract meta-data from Coherence cluster

 Copyright (C) 2011 Chris Thomson
 */
public class CacheInfo {
	public static String getCacheScheme(String cacheName) {
		DefaultConfigurableCacheFactory factory = (DefaultConfigurableCacheFactory) CacheFactory
				.getConfigurableCacheFactory();

		DefaultConfigurableCacheFactory.CacheInfo cacheInfo = factory
				.findSchemeMapping(cacheName);

		// get caching scheme xml contents
		XmlElement xmlConf = factory.resolveScheme(cacheInfo);

		// Quick conversion to format xml properly for display
		String xmlConfig = xmlConf.toString();

		return xmlConfig;
	}

	public static Collection<Member> getMembers() {
		Cluster cluster = CacheFactory.getCluster();
		List<Member> members = new ArrayList<Member>();

		for (Object obj : cluster.getMemberSet()) {
			Member member = (Member) obj;
			members.add(member);
		}

		return members;
	}

	public static Set<String> a(String jmxDomainName) throws MalformedObjectNameException, NullPointerException {
		// find (or create) an MBeanServer for the specified default JMX domain
		// name
		MBeanServer mServer = null;
		for (MBeanServer server : MBeanServerFactory.findMBeanServer(null)) {
			if (jmxDomainName.length() == 0
					|| server.getDefaultDomain().equals(jmxDomainName)) {
				mServer = server;
				break;
			}
		}
		
		if (mServer == null) {
			mServer = MBeanServerFactory.createMBeanServer(jmxDomainName);
		}

		// get the set of all Coherence Member MBean ObjectNames
		ObjectName objNameMembers = new ObjectName("Coherence:type=Node,*");
		@SuppressWarnings("unused")
		Set<ObjectName> setObjNameMembers = mServer.queryNames(objNameMembers, null);

		// get the set of Coherence Cache names (obtained from the 'name'
		// property on
		// a Cache MBean ObjectName)
		ObjectName objNameCaches = new ObjectName("Coherence:type=Cache,*");
		Set<ObjectName> setObjNameCaches = mServer.queryNames(objNameCaches, null);
		Set<String> cacheNames = new HashSet<String>();

		for (ObjectName objName : setObjNameCaches) {
			cacheNames
					.add(objName.getKeyProperty("name"));
		}

		return cacheNames;
	}
}
