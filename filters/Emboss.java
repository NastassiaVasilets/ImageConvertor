package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Emboss {
	private BufferedImage image;
	
	private int width;
	private int height;
	
	private BufferedImage tempImage;
	private BufferedImage result;
	
	public Emboss(BufferedImage image){
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		makeTempImage();
		result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	public void makeTempImage(){
		this.tempImage=new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++){
			tempImage.setRGB(i + 1, 0, image.getRGB(i,0));
			tempImage.setRGB(i + 1, height+1, image.getRGB(i, height-1));
		}
		for (int i = 0; i < height; i++){
			tempImage.setRGB(0, i + 1, image.getRGB(0, i));
			tempImage.setRGB(width + 1, i + 1, image.getRGB(width - 1, i));
		}
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				tempImage.setRGB(j + 1, i + 1, image.getRGB(j, i));
			}
		}
		//углы
		tempImage.setRGB(0, 0, image.getRGB(0,0));
		tempImage.setRGB(width + 1, height + 1, image.getRGB(width - 1,height - 1));
		tempImage.setRGB(0, height + 1, image.getRGB(0, height - 1));
		tempImage.setRGB(width + 1, 0, image.getRGB(width - 1,0));
	}
	
	public BufferedImage make(){
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
//				/* -2 -1 0
//				  -1  1 1
//				  0  1 2
//				 */
				int nRed = ((tempImage.getRGB(j, i) >> 16) & 0xff) * -2 + 
						((tempImage.getRGB(j + 1, i) >> 16) & 0xff) * -1 +
						((tempImage.getRGB(j, i + 1) >> 16) & 0xff) * -1 + 
						((image.getRGB(j, i) >> 16) & 0xff) * 1 +
						((tempImage.getRGB(j +2, i + 1) >> 16) & 0xff) * 1 +
						((tempImage.getRGB(j + 1, i + 2) >> 16) & 0xff) * 1 +
						((tempImage.getRGB(j + 2, i + 2) >> 16) & 0xff) * 2;
				int nGreen = ((tempImage.getRGB(j, i) >> 8) & 0xff) * -2 + 
						((tempImage.getRGB(j + 1, i) >> 8) & 0xff) * -1 +
						((tempImage.getRGB(j, i + 1) >> 8) & 0xff) * -1 + 
						((image.getRGB(j, i) >> 8) & 0xff) * 1 +
						((tempImage.getRGB(j +2, i + 1) >> 8) & 0xff) * 1 +
						((tempImage.getRGB(j + 1, i + 2) >> 8) & 0xff) * 1 +
						((tempImage.getRGB(j + 2, i + 2) >> 8) & 0xff) * 2;
				int nBlue = (tempImage.getRGB(j, i) & 0xff) * -2 + 
						(tempImage.getRGB(j + 1, i) & 0xff) * -1 +
						(tempImage.getRGB(j, i + 1) & 0xff) * -1 + 
						(image.getRGB(j, i) & 0xff) * 1 +
						(tempImage.getRGB(j +2, i + 1) & 0xff) * 1 +
						(tempImage.getRGB(j + 1, i + 2) & 0xff) * 1 +
						(tempImage.getRGB(j + 2, i + 2) & 0xff) * 2;
				
				if (nRed > 255) nRed = 255;
				if (nGreen > 255) nGreen = 255;
				if (nBlue > 255) nBlue = 255;
				if (nRed < 0) nRed = 0;
				if (nGreen < 0) nGreen = 0;
				if (nBlue < 0) nBlue = 0;
				
				Color resultColor = new Color(nRed, nGreen, nBlue);
				result.setRGB(j, i, resultColor.getRGB());
			}
		}
		return result;
	}
	
}
