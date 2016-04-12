package aphins;

import java.awt.image.BufferedImage;

public class Rotate {
	private BufferedImage image;
	private BufferedImage result;
	private int degree;
	private int width;
	private int height;

	public Rotate(BufferedImage image, Integer degree) {
		// TODO Auto-generated constructor stub
		this.image = image;
		this.degree = degree;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public BufferedImage make() {
		// TODO Auto-generated method stub
		double newDegree = Math.toRadians(degree);
        if(newDegree>360){
            newDegree = newDegree%360;
        }
        double sin = Math.sin(newDegree);
        double cos = Math.cos(newDegree);
        double x = 0.5*(width-1);  //point to rotate
        double y = 0.5*(height-1); //image center
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                double a = i-x;
                double b = j-y;
                int newX = (int)(+a*cos-b*sin+x);
                int newY = (int)(+a*sin+b*cos+y);
                if(newX>=0 && newX<width && newY>=0 && newY<height) 
                    result.setRGB(i, j, image.getRGB(newX, newY));
            }
        }
		return result;
	}

}
