/**
 * 
 */
package com.pedroalmir.eigenfaces.util;

import java.util.ArrayList;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import Jama.SingularValueDecomposition;



/**
 * @author Pedro Almir
 */
public class MatrixUtil {
	/**
	 * Get transposed matrix
	 * 
	 * @param matrix
	 * @return transposedMatrix
	 */
	public static double[][] getTransposedMatrix(double[][] matrix){
		double[][] transposedMatrix = new double[matrix[0].length][matrix.length];
		for(int line = 0; line < matrix.length; line++){
			for(int col = 0; col < matrix[0].length; col++){
				transposedMatrix[col][line] = matrix[line][col];
			}
		}
		
		return transposedMatrix;
	}
	
	/**
	 * Multiply matrix A and B
	 * @param matrixA
	 * @param matrixB
	 * @return matrix A * matrix B
	 */
	public static double[][] multiplyMatrix(double[][] matrixA, double[][] matrixB){
		if(matrixA[0].length == matrixB.length){
			int n = matrixA[0].length; 
			double[][] result = new double[matrixA.length][matrixB[0].length];
			for(int line = 0; line < matrixA.length; line++){
				for(int col = 0; col < matrixB[0].length; col++){
					for(int i = 0; i < n; i++){
						result[line][col] += matrixA[line][i] * matrixB[i][col];
					}
				}
			}
			return result;
		}else{
			return null;
		}
	}
	
	/**
	 * Print Matrix
	 * @param matrix
	 */
	public static void printMatrix(double[][] matrix){
		for(int line = 0; line < matrix.length; line++){
			for(int col = 0; col < matrix[0].length; col++){
				System.out.print((matrix[line][col] + "\t").replace(".", ","));
			}
			System.out.println();
		}
	}
	
	/**
	 * GetEignVectorsMatrix
	 * 
	 * @param matrix
	 * @return EignVectorsMatrix
	 */
	public static double[][] getEignVectorsMatrix(double[][] matrix){
		Matrix matA = new Matrix(matrix);
		//EigenvalueDecomposition eig = matA.eig();
		SingularValueDecomposition svd = matA.svd();
		return svd.getV().getArray();
	}
	
	/**
	 * GetEignValuesMatrix
	 * 
	 * @param matrix
	 * @return EignValues
	 */
	public static double[] getEignValuesMatrix(double[][] matrix){
		Matrix matA = new Matrix(matrix);
		EigenvalueDecomposition eig = matA.eig();
		return eig.getRealEigenvalues();
	}
	
	/**
	 * Calculate StandardEuclidean
	 * @param weights
	 * @param testWeights
	 * @return standardEuclidean
	 */
	public static ArrayList<double[]> calculateStandardEuclidean(double[][] weights, double[][] testWeights) {
		if(weights[0].length == testWeights[0].length){
			ArrayList<double[]> result = new ArrayList<double[]>();
			
			for(int image = 0; image < testWeights[0].length; image++){
				double[] standardEuclidean = new double[weights[0].length];
				for(int col = 0; col < weights[0].length; col++){
					double amount = 0.0;
					for(int line = 0; line < weights.length; line++){
						amount += Math.pow((weights[line][col] - testWeights[line][image]), 2);
					}
					standardEuclidean[col] = Math.pow(amount, 0.5);
				}
				result.add(standardEuclidean);
			}
			
			return result;
		}else{
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[][] matrixA = new double[][]{{1.0, 2.0, 3.0}, {3.0, 4.0, 5.0}, {6.0, 7.0, 8.0}, {9.0, 1.0, 0.0}};
		double[][] matrixB = new double[][]{{1.0, 1.0, 1.0, 1.0}, {0.0, 0.0, 0.0, 0.0}, {1.0, 1.0, 1.0, 1.0}};
		double[][] multiplyMatrix = MatrixUtil.multiplyMatrix(matrixA, matrixB);
		System.out.println(multiplyMatrix);
	}

}
