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

package uk.co.christhomson.xsl.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.xml.transform.TransformerException;

import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;

import uk.co.christhomson.sibyl.web.ServletContextResolver;

public class TestXsltHelper {
	
	private XsltHelper xslHelper = null;
	
	@Before
	public void setup() throws IOException, ServletException {
//		String xslFile = "src/main/webapp/xsl/extractor.xsl";
		String xslFile = "src/main/resources/uk/co/christhomson/sibyl/web/extractor.xsl";
		xslHelper = new XsltHelper(xslFile,new ServletContextResolver());
		
		String outputDir = "target/xsl/output";
		File dir = new File(outputDir);
		dir.mkdirs();
	}

	@Test
	public void test1() throws TransformerException, JDOMException, IOException {
		String xml = xslHelper.processXml("src/test/resources/xml/key1.xml",null);
		System.out.println(xml);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("target/xsl/output/test1.html"));
		bw.write(xml);
		bw.close();
	}

	@Test
	public void test2() throws TransformerException, JDOMException, IOException {
		String xml = xslHelper.processXml("src/test/resources/xml/key2.xml",null);
		System.out.println(xml);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("target/xsl/output/test2.html"));
		bw.write(xml);
		bw.close();
	}

	@Test
	public void test3() throws TransformerException, JDOMException, IOException {
		String xml = xslHelper.processXml("src/test/resources/xml/key3.xml",null);
		System.out.println(xml);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("target/xsl/output/test3.html"));
		bw.write(xml);
		bw.close();
	}
}
