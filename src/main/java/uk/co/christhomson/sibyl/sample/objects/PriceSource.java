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

/*
PriceSource
 Enum to represent sources of price data

Copyright (C) 2011 Chris Thomson
*/
public enum PriceSource {
	REUTERS(1),
	BLOOMBERG(2),
	UNKNOWN(3);
	
	private int value;
	
	private PriceSource(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static PriceSource find(int value) {
		switch (value) {
		case 1 :
			return REUTERS;
		case 2:
			return BLOOMBERG;
		default : 
			return UNKNOWN;
		
		}
	}
}
