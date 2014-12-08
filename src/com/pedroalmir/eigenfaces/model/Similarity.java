/**
 * 
 */
package com.pedroalmir.eigenfaces.model;

/**
 * @author Pedro Almir
 */
public class Similarity implements Comparable<Similarity>{
	private final String klass;
	private final double similarityDegree;
	
	/**
	 * @param klass
	 * @param similarityDegree
	 */
	public Similarity(String klass, double similarityDegree) {
		super();
		this.klass = klass;
		this.similarityDegree = similarityDegree;
	}
	/**
	 * @return the klass
	 */
	public String getKlass() {
		return klass;
	}
	/**
	 * @return the similarityDegree
	 */
	public double getSimilarityDegree() {
		return similarityDegree;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[klass=" + klass + ", similarityDegree=" + String.format("%.2f", similarityDegree) + "]";
	}
	
	@Override
	public int compareTo(Similarity o) {
		return Double.valueOf(this.getSimilarityDegree()).compareTo(o.getSimilarityDegree());
	}
}
