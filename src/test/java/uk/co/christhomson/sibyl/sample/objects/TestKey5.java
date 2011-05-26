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
