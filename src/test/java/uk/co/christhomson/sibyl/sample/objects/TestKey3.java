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
