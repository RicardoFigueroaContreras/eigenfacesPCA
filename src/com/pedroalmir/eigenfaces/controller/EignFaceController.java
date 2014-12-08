/**
 * 
 */
package com.pedroalmir.eigenfaces.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.pedroalmir.eigenfaces.model.EignValue;
import com.pedroalmir.eigenfaces.model.Result;
import com.pedroalmir.eigenfaces.model.Similarity;
import com.pedroalmir.eigenfaces.util.ImageUtil;
import com.pedroalmir.eigenfaces.util.MatrixUtil;


/**
 * @author Pedro Almir
 */
public class EignFaceController {
	
	/**
	 * Calculate Autofaces
	 * 
	 * @param eignVectors
	 * @param normalizedImages
	 * @return autofaces
	 */
	public static double[][] calculateAutofaces(double[][] eignVectors, double[] eignValues, double[][] normalizedImages){
		/* Autofaces matrix */
		double[][] autofaces = new double[normalizedImages.length][eignVectors.length];
		
		for(int line = 0; line < eignVectors.length; line++){
			for(int col = 0; col < eignVectors[0].length; col++){
				/* Remove eignVector if eignValue which is lower than ZERO */
				if(eignValues[col] > 0.0){
					double scalar = eignVectors[line][col];
					for(int pixel = 0; pixel < normalizedImages.length; pixel++){
						autofaces[pixel][line] += scalar * normalizedImages[pixel][line];
					}
				}
			}
		}
		return autofaces;
	}
	
	/**
	 * @param eignVectors
	 * @param eignValues
	 * @param normalizedImages
	 * @param qnt
	 * @return
	 */
	public static double[][] calculateAutofaces(double[][] eignVectors, double[] eignValues, double[][] normalizedImages, int qnt){
		ArrayList<EignValue> values = new ArrayList<EignValue>();
		for(int i = 0; i < eignValues.length; i++){
			values.add(new EignValue(i, eignValues[i]));
		}
		Collections.sort(values);
		
		/* Autofaces matrix */
		double[][] autofaces = new double[normalizedImages.length][eignVectors.length];
		for(int line = 0; line < eignVectors.length; line++){
			for(int col = 0; col < qnt; col++){
				double scalar = eignVectors[line][values.get(col).getIndex()];
				for(int pixel = 0; pixel < normalizedImages.length; pixel++){
					autofaces[pixel][line] += scalar * normalizedImages[pixel][line];
				}
			}
		}
		return autofaces;
	}
	
	/**
	 * Calculate Weights
	 * @param autofaces
	 * @param normalizedImages
	 * @return weights
	 */
	public static double[][] calculateWeights(double[][] autofaces, double[][] normalizedImages){
		double[][] autofacesTransposed = MatrixUtil.getTransposedMatrix(autofaces);
		return MatrixUtil.multiplyMatrix(autofacesTransposed, normalizedImages);
	}
	
	public static void main(String[] args) throws IOException {
		File eigenfacesFolder = new File("C:\\Desenvolvimento\\workspace\\eigenfaces\\data\\67x67\\1_eigenfaces");
		File trainingFolder = new File("C:\\Desenvolvimento\\workspace\\eigenfaces\\data\\67x67\\2_training");
		File testFolder = new File("C:\\Desenvolvimento\\workspace\\eigenfaces\\data\\67x67\\3_tests");
		
		/* Get Images */
		int[][] images = ImageUtil.readImages(eigenfacesFolder);
		int[][] trainingImages = ImageUtil.readImages(trainingFolder);
		int[][] testImages = ImageUtil.readImages(testFolder);
		
		/* First step: get standard image */
		double[] standardImage = ImageUtil.getStandardImage(images);
		/* Second step: normalize images */
		double[][] normalizedImages = ImageUtil.normalizeImageFromStandardImage(images, standardImage);
		/* Third step: calculate covariance matrix */
		double[][] covarianceMatrix = MatrixUtil.multiplyMatrix(MatrixUtil.getTransposedMatrix(normalizedImages), normalizedImages);
		/* Fourth step: calculate eignvector matrix and eignvalues vector */
		double[][] eignVectorMatrix = MatrixUtil.getEignVectorsMatrix(covarianceMatrix);
		double[] eignValues = MatrixUtil.getEignValuesMatrix(covarianceMatrix);
		
		/* Fifth step: calculate autofaces matrix */
		double[][] autofaces = EignFaceController.calculateAutofaces(eignVectorMatrix, eignValues, normalizedImages);
		double[][] autoFaces3 = EignFaceController.calculateAutofaces(eignVectorMatrix, eignValues, normalizedImages, 3);
		double[][] autoFaces2 = EignFaceController.calculateAutofaces(eignVectorMatrix, eignValues, normalizedImages, 2);
		double[][] autoFaces1 = EignFaceController.calculateAutofaces(eignVectorMatrix, eignValues, normalizedImages, 1);
		
		/* Sixth step: calculate weights matrix using trainingImages */
		double[][] trainingNormalized = ImageUtil.normalizeImageFromStandardImage(trainingImages, standardImage);
		double[][] weights = EignFaceController.calculateWeights(autofaces, trainingNormalized);
		double[][] weights3 = EignFaceController.calculateWeights(autoFaces3, trainingNormalized);
		double[][] weights2 = EignFaceController.calculateWeights(autoFaces2, trainingNormalized);
		double[][] weights1 = EignFaceController.calculateWeights(autoFaces1, trainingNormalized);
		
		/* Seventh step: test algorithm */
		double[][] testNormalized = ImageUtil.normalizeImageFromStandardImage(testImages, standardImage);
		double[][] testWeights = EignFaceController.calculateWeights(autofaces, testNormalized);
		double[][] testWeights3 = EignFaceController.calculateWeights(autoFaces3, testNormalized);
		double[][] testWeights2 = EignFaceController.calculateWeights(autoFaces2, testNormalized);
		double[][] testWeights1 = EignFaceController.calculateWeights(autoFaces1, testNormalized);
		
		ArrayList<double[]> standardEuclidean = MatrixUtil.calculateStandardEuclidean(weights, testWeights);
		ArrayList<double[]> standardEuclidean3 = MatrixUtil.calculateStandardEuclidean(weights3, testWeights3);
		ArrayList<double[]> standardEuclidean2 = MatrixUtil.calculateStandardEuclidean(weights2, testWeights2);
		ArrayList<double[]> standardEuclidean1 = MatrixUtil.calculateStandardEuclidean(weights1, testWeights1);
		
		/*System.out.println(images.length + " - " + images[0].length);
		System.out.println(standardImage.length);
		
		System.out.println(normalizedImages.length + " - " + normalizedImages[0].length);
		System.out.println(covarianceMatrix.length + " - " + covarianceMatrix[0].length);
		System.out.println(eignVectorMatrix.length + " - " + eignVectorMatrix[0].length);
		
		System.out.println(autofaces.length + " - " + autofaces[0].length);
		System.out.println(weights.length + " - " + weights[0].length);
		System.out.println(testWeights.length + " - " + testWeights[0].length);
		
		System.out.println(autofaces.length + " - " + autofaces[0].length);
		System.out.println(autoFaces3.length + " - " + autoFaces3[0].length);
		System.out.println(autoFaces2.length + " - " + autoFaces2[0].length);
		System.out.println(autoFaces1.length + " - " + autoFaces1[0].length);*/
		
		EignFaceController.printResult(standardEuclidean);
		EignFaceController.printResult(standardEuclidean3);
		EignFaceController.printResult(standardEuclidean2);
		EignFaceController.printResult(standardEuclidean1);
		
		//MatrixUtil.printMatrix(covarianceMatrix);
		//MatrixUtil.printMatrix(eignVectorMatrix);
		//System.out.println(Arrays.toString(eignValues));
		
		/* ------------------------------------------ Results ------------------------------------------ */
		File eigenfacesResultFolder = new File("C:\\Desenvolvimento\\workspace\\eigenfaces\\data\\results\\eigenfaces");
		File resultFolder = new File("C:\\Desenvolvimento\\workspace\\eigenfaces\\data\\results");
		
		ArrayList<double[]> modeloEigenfaces = new ArrayList<double[]>();
		for(int image = 0; image < autofaces[0].length; image++){
			double[] pixels = new double[autofaces.length];
			for(int pixel = 0; pixel < autofaces.length; pixel++){
				pixels[pixel] = autofaces[pixel][image];
			}
			if(image != 0){
				modeloEigenfaces.add(pixels);
			}
			String filename = (image < 9) ? ("eigenfaces_0" + (image + 1) + ".bmp") : ("eigenfaces_" + (image + 1) + ".bmp");
			ImageUtil.write(eigenfacesResultFolder, filename, pixels);
		}
		
		modeloEigenfaces.add(standardImage);
		ImageUtil.write(resultFolder, "standardImage.bmp", standardImage);
		ImageUtil.write(resultFolder, "eigenFaceModel.bmp", modeloEigenfaces);
		
		/*Arrays.sort(eignValues);
		double total = 0.0, amount = 0.0;
		
		for(int i = eignValues.length-1; i >= 0; i--){
			total += eignValues[i];
			System.out.println(String.format("%.2f", eignValues[i]));
		}
		
		System.out.println("\n\n");
		
		for(int i = eignValues.length-1; i >= 0; i--){
			amount += eignValues[i];
			System.out.println(String.format("%.2f", (amount*100)/total));
		}*/
	}
	
	/**
	 * @param standardEuclidean
	 */
	private static void printResult(ArrayList<double[]> standardEuclidean){
		int count = 1; double hits = 0;
		for(double[] array : standardEuclidean){
			Result result = new Result("Image " + (count++));
			for(int i = 0; i < array.length; i++){
				Similarity similarity = new Similarity("Image " + (i + 1), array[i]);
				result.addSimilatity(similarity);
			}
			Collections.sort(result.getSimilarity());
			if(result.getImage().equals(result.getSimilarity().get(0).getKlass())){
				hits++;
			}
			System.out.println(result.toString());
		}
		
		System.out.println("\nHits: " + hits + ", Samples: " + standardEuclidean.size());
		System.out.println("Accuracy rate: " + String.format("%.2f", ((hits * 100)/standardEuclidean.size())) + "%\n\n");
	}
}
