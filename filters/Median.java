package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Median {
	private BufferedImage image;
	private BufferedImage tempImage;
	private BufferedImage result;
	
	private int width;
	private int height;
	
	public Median(BufferedImage image){
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
				List<Integer> redList = new ArrayList<Integer>();
				redList.add((tempImage.getRGB(j, i) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j + 1, i) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j + 2, i) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j, i + 1) >> 16) & 0xff);
				redList.add((image.getRGB(j, i) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j + 2, i + 1) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j, i + 2) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j + 1, i + 2) >> 16) & 0xff);
				redList.add((tempImage.getRGB(j + 2, i + 2) >> 16) & 0xff);
				Collections.sort(redList);
				List<Integer> greenList = new ArrayList<Integer>();
				greenList.add((tempImage.getRGB(j, i) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j + 1, i) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j + 2, i) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j, i + 1) >> 8) & 0xff);
				greenList.add((image.getRGB(j, i) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j + 2, i + 1) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j, i + 2) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j + 1, i + 2) >> 8) & 0xff);
				greenList.add((tempImage.getRGB(j + 2, i + 2) >> 8) & 0xff);
				Collections.sort(greenList);
				List<Integer> blueList = new ArrayList<Integer>();
				blueList.add(tempImage.getRGB(j, i) & 0xff);
				blueList.add(tempImage.getRGB(j + 1, i) & 0xff);
				blueList.add(tempImage.getRGB(j + 2, i)& 0xff);
				blueList.add(tempImage.getRGB(j, i + 1) & 0xff);
				blueList.add(image.getRGB(j, i) & 0xff);
				blueList.add(tempImage.getRGB(j + 2, i + 1) & 0xff);
				blueList.add(tempImage.getRGB(j, i + 2) & 0xff);
				blueList.add(tempImage.getRGB(j + 1, i + 2) & 0xff);
				blueList.add(tempImage.getRGB(j + 2, i + 2) & 0xff);
				Collections.sort(blueList);
				
				Color color = new Color(image.getRGB(j, i));
				Color resultColor = new Color(redList.get(4), greenList.get(4), blueList.get(4));
				result.setRGB(j, i, resultColor.getRGB());
			}
		}
		return result;
	}
}
