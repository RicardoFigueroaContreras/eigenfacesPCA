/**
 * 
 */
package com.pedroalmir.eigenfaces.model;

/**
 * @author Pedro Almir
 *
 */
public class EignValue implements Comparable<EignValue>{
	private final int index;
	private final double value;
	/**
	 * @param index
	 * @param value
	 */
	public EignValue(int index, double value) {
		super();
		this.index = index;
		this.value = Double.valueOf(String.format("%.2f", value).replace(",", "."));
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public int compareTo(EignValue o) {
		return Double.valueOf(Double.valueOf(o.getValue())).compareTo(this.getValue());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EignValue [index=" + index + ", value=" + value + "]";
	}
}
