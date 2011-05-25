package uk.co.christhomson.sibyl.sample.objects;

import java.io.Serializable;

public class TestKey2 implements Serializable {

	private static final long serialVersionUID = 1581838600030608271L;
	
	private int k1 = 0;
	private String k2 = null;
	private short k3 = 0;
	private long k4 = 0;
	private double k5 = 0;
	private boolean k6 = false;
	private Integer k7 = null;
	private Short k8 = null;
	private Long k9 = null;
	private Double k10 = null;
	private Boolean k11 = null;
	
	public TestKey2() {}
	
	public TestKey2(int k1, String k2, short k3, long k4, double k5, boolean k6, Integer k7,
			Short k8, Long k9, Double k10, Boolean k11) {
		this.k1 = k1;
		this.k2 = k2;
		this.k3 = k3;
		this.k4 = k4;
		this.k5 = k5;
		this.k6 = k6;
		this.k7 = k7;
		this.k8 = k8;
		this.k9 = k9;
		this.k10 = k10;
		this.k11 = k11;
	}
	
	public int getK1() {
		return k1;
	}

	public String getK2() {
		return k2;
	}

	public short getK3() {
		return k3;
	}

	public long getK4() {
		return k4;
	}

	public double getK5() {
		return k5;
	}

	public boolean isK6() {
		return k6;
	}

	public Integer getK7() {
		return k7;
	}

	public Short getK8() {
		return k8;
	}

	public Long getK9() {
		return k9;
	}

	public Double getK10() {
		return k10;
	}

	public Boolean getK11() {
		return k11;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + k1;
		result = prime * result + ((k10 == null) ? 0 : k10.hashCode());
		result = prime * result + ((k11 == null) ? 0 : k11.hashCode());
		result = prime * result + ((k2 == null) ? 0 : k2.hashCode());
		result = prime * result + k3;
		result = prime * result + (int) (k4 ^ (k4 >>> 32));
		long temp;
		temp = Double.doubleToLongBits(k5);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (k6 ? 1231 : 1237);
		result = prime * result + ((k7 == null) ? 0 : k7.hashCode());
		result = prime * result + ((k8 == null) ? 0 : k8.hashCode());
		result = prime * result + ((k9 == null) ? 0 : k9.hashCode());
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
		TestKey2 other = (TestKey2) obj;
		if (k1 != other.k1)
			return false;
		if (k10 == null) {
			if (other.k10 != null)
				return false;
		} else if (!k10.equals(other.k10))
			return false;
		if (k11 == null) {
			if (other.k11 != null)
				return false;
		} else if (!k11.equals(other.k11))
			return false;
		if (k2 == null) {
			if (other.k2 != null)
				return false;
		} else if (!k2.equals(other.k2))
			return false;
		if (k3 != other.k3)
			return false;
		if (k4 != other.k4)
			return false;
		if (Double.doubleToLongBits(k5) != Double.doubleToLongBits(other.k5))
			return false;
		if (k6 != other.k6)
			return false;
		if (k7 == null) {
			if (other.k7 != null)
				return false;
		} else if (!k7.equals(other.k7))
			return false;
		if (k8 == null) {
			if (other.k8 != null)
				return false;
		} else if (!k8.equals(other.k8))
			return false;
		if (k9 == null) {
			if (other.k9 != null)
				return false;
		} else if (!k9.equals(other.k9))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestKey3 [k1=" + k1 + ", k10=" + k10 + ", k11=" + k11 + ", k2="
				+ k2 + ", k3=" + k3 + ", k4=" + k4 + ", k5=" + k5 + ", k6="
				+ k6 + ", k7=" + k7 + ", k8=" + k8 + ", k9=" + k9 + "]";
	}
	
	
}
