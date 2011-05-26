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

import uk.co.christhomson.sibyl.exception.CacheException;

public class TestCacheException {
	@Test
	public void test1() {
		CacheException ex = new CacheException();
	}
	
	@Test
	public void test2() {
		CacheException ex = new CacheException("TEST");
	}
	
	@Test
	public void test3() {
		CacheException ex = new CacheException(new Exception("TEST"));
	}
	
	@Test
	public void test4() {
		CacheException ex = new CacheException("TEST",new Exception("TEST"));
	}
}
