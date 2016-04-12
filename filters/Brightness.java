package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Brightness {
	private BufferedImage image;
	private int width;
	private int height;
	private BufferedImage result;
	
	 public Brightness(BufferedImage image){
		 this.image=image;
		 this.width=image.getWidth();
		 this.height=image.getHeight();
		 
		 result=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	 }
	 
	 public BufferedImage make(){
		 for (int i=0; i<height; i++){
			 for (int j=0; j<width; j++){
				 Color color=new Color(image.getRGB(j, i));
				 result.setRGB(j, i, color.brighter().getRGB());
			 }
		 }
		 return result;
	 }
}
