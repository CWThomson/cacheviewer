package uk.co.christhomson.sibyl.sample.objects;

import java.io.Serializable;

public class TestKey3 implements Serializable {

	private static final long serialVersionUID = 1684465904331235370L;

	private TestKey2 k1 = null;
	
	public TestKey3() {}
	
	public TestKey3(TestKey2 k1) {
		this.k1 = k1;
	}
	
	public TestKey2 getK1() {
		return k1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + k1.hashCode();
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
		TestKey3 other = (TestKey3) obj;
		if (!k1.equals(other.k1))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestKey3 [k1=" + k1 + "]";
	}	
}
