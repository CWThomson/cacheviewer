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

package uk.co.christhomson.sibyl.exception;

import org.junit.Test;

import uk.co.christhomson.sibyl.exception.InvalidCacheNameException;

public class TestInvalidCacheNameException {
	@Test
	public void test1() {
		InvalidCacheNameException ex = new InvalidCacheNameException();
	}
	
	@Test
	public void test2() {
		InvalidCacheNameException ex = new InvalidCacheNameException("TEST");
	}
	
	@Test
	public void test3() {
		InvalidCacheNameException ex = new InvalidCacheNameException(new Exception("TEST"));
	}
	
	@Test
	public void test4() {
		InvalidCacheNameException ex = new InvalidCacheNameException("TEST",new Exception("TEST"));
	}
}
