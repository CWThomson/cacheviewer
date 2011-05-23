package uk.co.christhomson.sibyl.sample.objects;

import java.io.IOException;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

public class TestKey1 implements PortableObject {
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

	public void readExternal(PofReader reader) throws IOException {
		k1 = reader.readString(0);
	}

	public void writeExternal(PofWriter writer) throws IOException {
		writer.writeString(0, k1);
	}
	
	
}
