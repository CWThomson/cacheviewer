package uk.co.christhomson.sibyl.sample.objects;

import java.util.Date;

public class TestKey5 {
	private Date k1 = null;
	private Date k2 = null;
	
	public TestKey5() {}
	
	public TestKey5(Date k1, Date k2) {
		this.k1 = k1;
		this.k2 = k2;
	}
	
	public Date getK1() {
		return k1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + k1.hashCode();
		result = prime * result + k2.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestKey5 other = (TestKey5) obj;
		if (!k1.equals(other.k1))
			return false;
		if (!k2.equals(other.k2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestKey5 [k1=" + k1 + ",k2=" + k2 + "]";
	}
}
