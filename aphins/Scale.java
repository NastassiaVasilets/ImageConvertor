package aphins;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scale {
	private BufferedImage scaleImage;
	private BufferedImage result;
	
	private Point center;
	private int width;
	private int height;
	private int procentX;
	private int procentY;
	private int newWidth;
	private int newHeight;
	
	public Scale(BufferedImage scaleImage, int x, int y){
		this.scaleImage = scaleImage;
		this.width = scaleImage.getWidth();
		this.height = scaleImage.getHeight();
		this.procentX = x;
		this.procentY = y ;
		this.newWidth = width * procentX / 100;
		this.newHeight = height * procentY /100;
		this.result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB );
	}

	public BufferedImage make() {
		// TODO Auto-generated method stub
		for(int i = 0; i < newWidth; i++){
            for(int j = 0; j < newHeight; j++){
                int newX = i * width/newWidth;
                int newY = j * height/newHeight;
                result.setRGB(i, j, scaleImage.getRGB(newX, newY));
            }
        }
		return result;
	}
}
	
	
