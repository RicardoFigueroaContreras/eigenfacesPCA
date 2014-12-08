/**
 * 
 */
package com.pedroalmir.eigenfaces.util;

import java.awt.image.BufferedImage;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * @author Pedro Almir
 */
public class ImageUtil {
	 
	/**
	 * @param file
	 * @return image in byte array
	 * @throws IOException
	 */
	public static int[] read(final File file) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(file);
		SampleModel sampleModel = bufferedImage.getRaster().getSampleModel();
		int width = sampleModel.getWidth(), height = sampleModel.getHeight();
		
		int pixelCount = 0;
		int[] pixels = new int[width * height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				pixels[pixelCount++] = sampleModel.getSample(x, y, 0, bufferedImage.getData().getDataBuffer());
			}
		}
		
		return pixels;
	}
	
	/**
	 * Write image
	 * 
	 * @param folder
	 * @param filename
	 * @param pixels
	 */
	public static void write(final File folder, String filename, double[] pixels){
		int globalCount = 0;
		BufferedImage image = new BufferedImage(67, 67, BufferedImage.TYPE_INT_RGB);
		
		for(int line = 0; line < 67; line++){
			for(int col = 0; col < 67; col++, globalCount++){
				int value = ((int) Math.abs(pixels[globalCount])) << 16 | ((int) Math.abs(pixels[globalCount])) << 8 | ((int) Math.abs(pixels[globalCount]));
				image.setRGB(line, col, value);
			}
		}

	    try {
	    	File output = new File(folder, filename);
	    	output.createNewFile();
	    	ImageIO.write(image, "bmp", output);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * @param resultFolder
	 * @param filename
	 * @param modeloEigenfaces
	 */
	public static void write(File folder, String filename, ArrayList<double[]> modeloEigenfaces) {
		int imageCount = 0;
		BufferedImage image = new BufferedImage(603, 335, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 5; j++){
				int globalCount = 0;
				double[] pixels = modeloEigenfaces.get(imageCount++);
				for(int line = 0; line < 67; line++){
					for(int col = 0; col < 67; col++, globalCount++){
						int value = ((int) Math.abs(pixels[globalCount])) << 16 | ((int) Math.abs(pixels[globalCount])) << 8 | ((int) Math.abs(pixels[globalCount]));
						int posX = ((i * 67) + line); int posY = ((j * 67) + col); 
						image.setRGB(posX, posY, value);
					}
				}
			}
		}
		
		try {
	    	File output = new File(folder, filename);
	    	output.createNewFile();
	    	ImageIO.write(image, "bmp", output);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Read images from a folder.
	 * Remember: all images must to be with same size.
	 * 
	 * @param folder
	 * @return matrix of images
	 * @throws IOException
	 */
	public static int[][] readImages(final File folder) throws IOException{
		ArrayList<int[]> images = new ArrayList<int[]>();
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isFile()) {
	        	images.add(ImageUtil.read(fileEntry));
	        }
	    }
		int[][] matrix = new int[images.get(0).length][images.size()];
		for(int col = 0; col < images.size(); col++){
			for(int line = 0; line < images.get(0).length; line++){
				matrix[line][col] = images.get(col)[line];
			}
		}
		return matrix;
	}
	
	/**
	 * Get standard image
	 * 
	 * @param images
	 * @return standardImage
	 */
	public static double[] getStandardImage(int[][] images){
		double[] standardImage = new double[images.length];
		for(int line = 0; line < images.length; line++){
			double amount = 0f;
			for(int col = 0; col < images[0].length; col++){
				amount += images[line][col];
			}
			standardImage[line] = amount/(images[0].length);
		}
		return standardImage;
	}
	
	/**
	 * Normalize image from standard image
	 * @param images
	 * @param standardImage
	 * @return normalizedImages
	 */
	public static double[][] normalizeImageFromStandardImage(int[][] images, double[] standardImage){
		double[][] normalizedImages = new double[images.length][images[0].length];
		for(int col = 0; col < images[0].length; col++){
			for(int line = 0; line < images.length; line++){
				normalizedImages[line][col] = images[line][col] - standardImage[line];
			}
		}
		return normalizedImages;
	}
}
