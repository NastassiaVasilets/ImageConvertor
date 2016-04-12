package segmentation; 
import java.awt.image.BufferedImage; 
import java.util.ArrayList;

public class KMeans { 
    BufferedImage result; 
    Klaster[] klasters; 
	private int width;
	private int height;
    private int number;
	private BufferedImage filterImage;
     
     
    public KMeans() {    } 
     
    public BufferedImage make(BufferedImage filterImage,  
                                            int k) { 
    	this.filterImage = filterImage;
        this.width = filterImage.getWidth(); 
        this.height = filterImage.getHeight(); 
        this.number = k;
        klasters = createKlasters(); 
        ArrayList<Integer> pixels = new ArrayList<Integer>();
        for (int i = 0; i < width * height; i++){
        	pixels.add(-1);
        }
         
        boolean change = true;
        while (change) { 
            change = false;
            for (int i = 0; i < height; i++) { 
                for (int j = 0; j < width; j++) { 
                    int pixel = filterImage.getRGB(j, i); 
                    Klaster klaster = findClosestKlaster(pixel); 
                    if (pixels.get(width * j + i) != klaster.getId()) {
                        if (pixels.get(width * j + i) != -1) {
                           klasters[pixels.get(width * j + i)].removePixel(pixel); 
                        } 
                        klaster.addPixel(pixel); 
                        change = true; 
                        pixels.set(width * j + i, klaster.getId()); 
                    } 
                } 
            }
        } 
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        for (int i = 0; i < height; i++) { 
            for (int j = 0; j < width; j++) { 
                int clusterId = pixels.get(width * j + i); 
                result.setRGB(j, i, klasters[clusterId].getRGB()); 
            } 
        } 
        return result; 
    } 
     
    public Klaster[] createKlasters() { 
        Klaster[] result = new Klaster[number]; 
        int x = 0; int y = 0; 
        int dx = filterImage.getWidth()/number; 
        int dy = filterImage.getHeight()/number; 
        for (int i=0;i<number;i++) { 
            result[i] = new Klaster(i,filterImage.getRGB(x, y)); 
            x+=dx; y+=dy; 
        } 
        return result; 
    } 
     
    public Klaster findClosestKlaster(int rgb) { 
        Klaster klaster = null; 
        int min = Integer.MAX_VALUE; 
        for (int i=0;i<klasters.length;i++) { 
            int distance = klasters[i].distance(rgb); 
            if (distance<min) { 
                min = distance; 
                klaster = klasters[i]; 
            } 
        } 
        return klaster; 
    } 
}