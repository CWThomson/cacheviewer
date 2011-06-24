package uk.co.christhomson.sibyl.web;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class ServletContextResolver implements URIResolver {

	public ServletContextResolver() {
	}

	/**
	 * Reslove location of file
	 * 
	 * @param href
	 *            The file where / is the root dir in the servlet context (eg.
	 *            /WEB-INF)
	 * @param base
	 */
	public Source resolve(String href, String base) throws TransformerException {

		StreamSource source = new StreamSource(this.getClass()
				.getResourceAsStream("../../../../../" + href));
		return source;
	}

}
