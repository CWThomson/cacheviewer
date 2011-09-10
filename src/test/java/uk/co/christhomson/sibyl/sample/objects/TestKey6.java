package uk.co.christhomson.sibyl.sample.objects;

import java.util.Date;

public class TestKey6 extends TestKey5 {

	private String test = null;
	
	public TestKey6(String test) {
		super(new Date(),new Date());
		this.test = test;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return "TestKey6 [test=" + test + ", getK1()=" + getK1() + "]";
	}
	
	
}
