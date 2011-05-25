package uk.co.christhomson.sibyl.sample.objects;


public class TestKey1 {
	private String k1 = null;
	
	public TestKey1() {}
	
	public TestKey1(String k1) {
		this.k1 = k1;
	}
	
	public String getK1() {
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
		TestKey1 other = (TestKey1) obj;
		if (!k1.equals(other.k1))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestKey4 [k1=" + k1 + "]";
	}
	
}
