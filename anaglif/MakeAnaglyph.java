package anaglif;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MakeAnaglyph {
	private BufferedImage left;
	private BufferedImage right;
	
	private BufferedImage result;
	
	public MakeAnaglyph(BufferedImage left, BufferedImage right){
		this.left = left;
		this.right = right;
	}
	
	public BufferedImage make(){
		result = new BufferedImage(left.getWidth(), left.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < left.getHeight(); i++){
			for (int j = 0; j < left.getWidth(); j++){
				
				Color colorLeft = new Color(left.getRGB(j,i));
	            int redLeft = colorLeft.getRed();
	            
	            Color color = new Color(right.getRGB(j,i));
	            int greenRight = color.getGreen();
	            int blueRight = color.getBlue();
	            
	            Color resultColor = new Color((int)(redLeft), (int)(greenRight), (int)(blueRight));
	            result.setRGB(j,i, resultColor.getRGB());
			}
		}
		return result;
	}
	
}
