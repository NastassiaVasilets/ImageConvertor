package filters;

import java.awt.image.BufferedImage;

public class Gray {
	private BufferedImage image;
	private BufferedImage result;
	
	private int width;
	private int height;
	
	public Gray(BufferedImage image){
		this.image=image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public BufferedImage make(){
		for (int i=0; i<height; i++){
			for (int j=0; j<width; j++){
			int nRGB= image.getRGB(j, i);
				
			int red = (nRGB >> 16) & 0xff;
			int green = (nRGB >> 8) & 0xff;
			int blue = nRGB & 0xff;
	    
			int gray = (int)(0.56 * green + 0.33 * red + 
					0.11 * blue);
	    result.setRGB(j, i, 0xff000000 | gray << 16 | gray << 8 | gray);
			}
		}
		return result;
	}
}
