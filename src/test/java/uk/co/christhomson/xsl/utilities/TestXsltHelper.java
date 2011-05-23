package uk.co.christhomson.xsl.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.xml.transform.TransformerException;

import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;

public class TestXsltHelper {
	
	private XsltHelper xslHelper = null;
	
	@Before
	public void setup() throws FileNotFoundException, ServletException {
		String xslFile = "src/main/webapp/xsl/extractor.xsl";
		xslHelper = new XsltHelper(xslFile);
		
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
