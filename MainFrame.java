

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import segmentation.KMeans;
import segmentation.WatersheedMethod;
import anaglif.MakeAnaglyph;
import aphins.AphinsFrame;
import aphins.Rotate;
import aphins.Scale;
import filters.Brightness;
import filters.Emboss;
import filters.Gray;
import filters.Median;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int HEIGHT=600;
	private static int WIDTH=500;
	private JMenuBar toolBar;
	private JMenuItem anaglyph;
	private JMenu filter;
	private JMenuItem emboss;
	private JMenuItem brightness;
	private JMenuItem gray;
	private JMenuItem median;
	private JMenu segmentationMenu;
	private JMenuItem watershedMethod;
	private JMenuItem kMeansMenu;
	private JMenu aphines;
	private JMenuItem rotateMenu;
	private JMenuItem scaleMenu;
	private JPanel imagePanel;
	
	private int ret;
	private JFileChooser chooser; 
	
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage filterImage;
	private BufferedImage result;
	
	private MakeAnaglyph makeAnaglyph;
	private Emboss embossFilter;
	private Brightness brightnessFilter;
	private Gray grayFilter;
	private Median medianFilter;
	private WatersheedMethod watersheedMethod;
	private Rotate rotate;
	private KMeans kMeans;
	private JLabel initLabel;
	private JLabel resLabel;
	
	public MainFrame(){
		settings();
		createMenu();
		initListeners();
		pack();
	}
	
	public void createMenu(){
		toolBar= new JMenuBar();
		anaglyph = new JMenuItem("make anaglyph");
		toolBar.add(anaglyph);
		
		filter= new JMenu ("make filter");
		emboss = new JMenuItem("emboss");
		filter.add(emboss);
		brightness=new JMenuItem("brightness");
		filter.add(brightness);
		gray = new JMenuItem("gray");
		filter.add(gray);
		median = new JMenuItem("median");
		filter.add(median);
		toolBar.add(filter);
		
		segmentationMenu = new JMenu("segmentation");
		watershedMethod = new JMenuItem("watershed method");
		segmentationMenu.add(watershedMethod);
		kMeansMenu = new JMenuItem("k-means method");
		segmentationMenu.add(kMeansMenu);
		toolBar.add(segmentationMenu);
		
		aphines = new JMenu("aphins");
		scaleMenu = new JMenuItem("scale");
		rotateMenu = new JMenuItem("rotate");
		aphines.add(scaleMenu);
		aphines.add(rotateMenu);
		toolBar.add(aphines);
		
		this.add(toolBar, BorderLayout.NORTH);
		
		imagePanel= new JPanel();
		imagePanel.setSize(250, 500);
		this.add(imagePanel, BorderLayout.SOUTH);
	}
	
	public void initListeners(){
		//filterImage = chooseFileForFilter();
		anaglyph.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				ret = chooser.showDialog(null, "Открыть файл");                
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File image=chooser.getSelectedFile();
				    left = null;
				    try 
				    {
				        left = ImageIO.read(image);
				    }
				    catch (IOException ee){ee.printStackTrace();} 
				}
				chooser = new JFileChooser();
				ret = chooser.showDialog(null, "Открыть файл");  
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File image=chooser.getSelectedFile();
				    right = null;
				    try 
				    {
				    	right = ImageIO.read(image);
				    }
			    catch (IOException ee){ee.printStackTrace();}
				}
			    makeAnaglyph= new MakeAnaglyph(left, right);
			    result=makeAnaglyph.make();
			    createPanel();
			}});
/******************************************************************/		
		emboss.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooser = new JFileChooser();
				ret = chooser.showDialog(null, "Открыть файл");  
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File image=chooser.getSelectedFile();
				    filterImage = null;
				    try 
				    {
				    	filterImage = ImageIO.read(image);
				    }
			    catch (IOException ee){ee.printStackTrace();}
				}
				embossFilter=new Emboss(filterImage);
				result=embossFilter.make();
				createFilterPanel();
			}
		});
		
		brightness.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooser = new JFileChooser();
				ret = chooser.showDialog(null, "Открыть файл");  
				if (ret == JFileChooser.APPROVE_OPTION) {
				    File image=chooser.getSelectedFile();
				    filterImage = null;
				    try 
				    {
				    	filterImage = ImageIO.read(image);
				    }
			    catch (IOException ee){ee.printStackTrace();}
				}
				brightnessFilter = new Brightness(filterImage);
				result = brightnessFilter.make();
				createFilterPanel();
			}
			
		});
		
		gray.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
				grayFilter = new Gray(filterImage);
				result = grayFilter.make();
				createFilterPanel();
			}
			});
		
		median.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				chooseFile();
				medianFilter = new Median(filterImage);
				result = medianFilter.make();
				createFilterPanel();
			}
		});
/*************************************************************************/		
		watershedMethod.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseFile();
				watersheedMethod = new WatersheedMethod(filterImage);
				result = watersheedMethod.make();
				createFilterPanel();
			}
			
		});
		kMeansMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseFile();
				String s = (String)JOptionPane.showInputDialog("number", "");
			        // create new KMeans object 
			        KMeans kmeans = new KMeans();
			        try {
			        	int k = Integer.parseInt(s);
			        	if (k > 1){
				        	result = kmeans.make(filterImage, k); 
				        	createFilterPanel();
			        	}
			        	else {
			        		final JPanel panel = new JPanel();
				            JOptionPane.showMessageDialog(panel, "Incorrect data", "Error", JOptionPane.ERROR_MESSAGE);
			        	}
			        }
			        catch (Exception e){
			        	final JPanel panel = new JPanel();
			            JOptionPane.showMessageDialog(panel, "Incorrect data", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			        
			}
			
		});
		/******************************************************************************************************/
		rotateMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseFile();
				AphinsFrame aphinsFrame = new AphinsFrame(1, filterImage);
				JButton ok = aphinsFrame.getOk();
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
							Rotate rotate= new Rotate(filterImage,aphinsFrame.getSliderValue());
							result = rotate.make();
							createFilterPanel();
					}
					
				});
			}
		});
		
		scaleMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				chooseFile();
				AphinsFrame aphinsFrame = new AphinsFrame(2, filterImage);
				JButton ok = aphinsFrame.getOk();
				ok.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						try{
							int f1 = Integer.parseInt(aphinsFrame.getTextField1().getText());
							int f2 = Integer.parseInt(aphinsFrame.getTextField2().getText());
							if ((f1 > 1) && (f2 > 1)){
								Scale scale = new Scale(filterImage, f1, f2);
								result = scale.make();
								createFilterPanel();
							}
							else{
						        final JPanel panel = new JPanel();
						        JOptionPane.showMessageDialog(panel, "Incorrect data", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(Exception e2){
							final JPanel panel = new JPanel();
					        JOptionPane.showMessageDialog(panel, "Incorrect data", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		});
	}
	
	public void chooseFile(){
		chooser = new JFileChooser();
		ret = chooser.showDialog(null, "Открыть файл");  
		if (ret == JFileChooser.APPROVE_OPTION) {
		    File image=chooser.getSelectedFile();
		    filterImage = null;
		    try 
		    {
		    	filterImage = ImageIO.read(image);
		    }
	    catch (IOException ee){ee.printStackTrace();}
		}
	}
	public void createPanel(){
		resLabel.setIcon(new ImageIcon(result));
		imagePanel.add(resLabel);
		this.repaint();
	}
	
	public void createFilterPanel(){
		if (initLabel != null) imagePanel.remove(initLabel);
		if (resLabel != null) imagePanel.remove(resLabel);
		initLabel = new JLabel();
		initLabel.setIcon(new ImageIcon(filterImage));
		resLabel = new JLabel();
		resLabel.setIcon(new ImageIcon(result));
		
		imagePanel.add(initLabel);
		imagePanel.add(resLabel);
		imagePanel.updateUI();
	}

	public void settings(){
		this.setVisible(true);
		this.setLocation(WIDTH/2, HEIGHT/2);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("MY IMAGE CONVERTOR");
	}
}
