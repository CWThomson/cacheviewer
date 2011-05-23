package uk.co.christhomson.sibyl.sample.objects;

import java.io.IOException;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

public class TestKey4 implements PortableObject {
	private TestKey4 k1 = null;
	
	public TestKey4() {}
	
	public TestKey4(TestKey4 k1) {
		this.k1 = k1;
	}
	
	public TestKey4 getK1() {
		return k1;
	}

	public void readExternal(PofReader reader) throws IOException {
		k1 = (TestKey4) reader.readObject(0);
	}

	public void writeExternal(PofWriter writer) throws IOException {
		writer.writeObject(0, k1);
	}
	
	
}
