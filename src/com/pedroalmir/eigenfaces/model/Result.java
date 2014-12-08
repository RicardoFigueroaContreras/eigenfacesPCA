/**
 * 
 */
package com.pedroalmir.eigenfaces.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Pedro Almir
 *
 */
public class Result {
	
	private final String image;
	private ArrayList<Similarity> similarity;
	
	/**
	 * @param image
	 */
	public Result(String image) {
		super();
		this.image = image;
		this.similarity = new ArrayList<Similarity>();
	}
	
	/**
	 * @param similarity
	 */
	public void addSimilatity(Similarity similarity){
		if(similarity != null){
			this.similarity.add(similarity);
		}
	}
	
	/**
	 * @return the similarity
	 */
	public ArrayList<Similarity> getSimilarity() {
		return similarity;
	}
	/**
	 * @param similarity the similarity to set
	 */
	public void setSimilarity(ArrayList<Similarity> similarity) {
		this.similarity = similarity;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Collections.sort(this.getSimilarity());
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < 3; i++){
			if(i < 2){
				buffer.append(this.getSimilarity().get(i) + ", ");
			}else{
				buffer.append(this.getSimilarity().get(i));
			}
		}
		return "Result [image=" + image + ", similarity=" + buffer.toString() + "]";
	}
}
