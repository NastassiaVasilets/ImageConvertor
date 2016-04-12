package segmentation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.text.MaskFormatter;

public class WatersheedMethod {
	
	private BufferedImage image;
	private BufferedImage result;
	private BufferedImage tempImage;
	private int width;
	private int height;
	
	public WatersheedMethod(BufferedImage image){
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public BufferedImage make(){
		result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		byteGray();
		sobelFilter();
		return result;
	}
	
	public void byteGray(){
		WritableRaster raster = result.getRaster();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				Color c = new Color(rgb);
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int gray = (int) (r * 0.3 + g * 0.59 + b * 0.11);
				raster.setPixel(x, y, new int[] { gray });
			}
		}
	}
	
	public void sobelFilter(){
		this.tempImage=new BufferedImage(image.getWidth() + 2, image.getHeight() + 2, BufferedImage.TYPE_INT_RGB);
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
		
		for (int i = 0; i < image.getHeight(); i++){
			  for (int j = 0; j < image.getWidth(); j++){
				  int xRed = ((tempImage.getRGB(j, i) >> 16) & 0xff) * -1 + 
							((tempImage.getRGB(j + 2, i) >> 16) & 0xff) * 1 +
							((tempImage.getRGB(j, i + 1) >> 16) & 0xff) * -2 +
							((tempImage.getRGB(j + 2, i + 1) >> 16) & 0xff) * 2 +
							((tempImage.getRGB(j, i + 2) >> 16) & 0xff) * -1 +
							((tempImage.getRGB(j + 2, i + 2) >> 16) & 0xff) * 1;
					int xGreen = ((tempImage.getRGB(j, i) >> 8) & 0xff) * -1 + 
							((tempImage.getRGB(j + 2, i) >> 8) & 0xff) * 1 +
							((tempImage.getRGB(j, i + 1) >> 8) & 0xff) * -2 + 
							((tempImage.getRGB(j + 2, i + 1) >> 8) & 0xff) * 2 +
							((tempImage.getRGB(j, i + 2) >> 8) & 0xff) * -1 +
							((tempImage.getRGB(j + 2, i + 2) >> 8) & 0xff) * 1;
					int xBlue = (tempImage.getRGB(j, i) & 0xff) * -1 + 
							(tempImage.getRGB(j + 2, i) & 0xff) * 1 +
							(tempImage.getRGB(j, i + 1) & 0xff) * -2 +
							(tempImage.getRGB(j +2, i + 1) & 0xff) * 2 +
							(tempImage.getRGB(j, i + 2) & 0xff) * -1 +
							(tempImage.getRGB(j + 2, i + 2) & 0xff) * 1;
					
					int yRed = ((tempImage.getRGB(j, i) >> 16) & 0xff) * -1 + 
								((tempImage.getRGB(j + 1, i) >> 16) & 0xff) * -2 +
								((tempImage.getRGB(j + 2, i) >> 16) & 0xff) * -1 +
								((tempImage.getRGB(j, i + 2) >> 16) & 0xff) * 1 +
								((tempImage.getRGB(j + 1, i + 2) >> 16) & 0xff) * 2 +
								((tempImage.getRGB(j + 2, i + 2) >> 16) & 0xff) * 1;
					int yGreen = ((tempImage.getRGB(j, i) >> 8) & 0xff) * -1 + 
								((tempImage.getRGB(j + 1, i) >> 8) & 0xff) * -2 +
								((tempImage.getRGB(j + 2, i) >> 8) & 0xff) * -1 + 
								((tempImage.getRGB(j, i + 2) >> 8) & 0xff) * 1 +
								((tempImage.getRGB(j + 1, i + 2) >> 8) & 0xff) * 2 +
								((tempImage.getRGB(j + 2, i + 2) >> 8) & 0xff) * 1;
					int yBlue = (tempImage.getRGB(j, i) & 0xff) * -1 + 
								(tempImage.getRGB(j + 1, i) & 0xff) * -2 +
								(tempImage.getRGB(j + 2, i) & 0xff) * -1 + 
								(tempImage.getRGB(j, i + 2) & 0xff) * 1 +
								(tempImage.getRGB(j + 1, i + 2) & 0xff) * 2 +
								(tempImage.getRGB(j + 2, i + 2) & 0xff) * 1;
						
			    int valR = (int) Math.sqrt((xRed * xRed) + (yRed * yRed));
			    int valG = (int) Math.sqrt((xGreen * xGreen) + (yGreen * yGreen));
			    int valB = (int) Math.sqrt((xBlue * xBlue) + (yBlue * yBlue));
			    
			    if (valR > 255) valR = 255;
				if (valG > 255) valG = 255;
				if (valB > 255) valB = 255;
				if (valR < 0) valR = 0;
				if (valG < 0) valG = 0;
				if (valB < 0) valB = 0;
				
			    Color color = new Color(valR,valG,valB);
			    result.setRGB(j, i, color.getRGB());
			  }
			}
	}
}
