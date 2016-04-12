package aphins;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class AphinsFrame extends JFrame {
	private JSlider slider;
	private JTextField textField1;
	private JTextField textField2;
	private JLabel label;
	private JButton ok;
	private int type;
	private BufferedImage image;
	private BufferedImage result;
	
	public AphinsFrame(int type, BufferedImage image){
		this.setVisible(true);
		this.setLayout(new GridLayout(2,1));
		this.setLocation(100, 200);
		this.setSize(400, 100);
		this.image = image;
		this.type = type;
		switch(type){
		case 1:{
			this.setLayout(new GridLayout(3, 1));
			slider = new JSlider(0,360);
			this.add(slider);
			label = new JLabel();
			this.add(label);
			slider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					int value = ((JSlider)e.getSource()).getValue(); 
					label.setText(value + " degree");
				}
			});
			break;
		}
		case 2:{
			textField1 = new JTextField();
			this.add(textField1);
			textField2 = new JTextField();
			this.add(textField2);
			break;
		}
		default:{
		}
		}
		ok = new JButton("ok");
		this.add(ok);
		pack();
		
	}
	
	public JButton getOk(){
		return ok;
	}
	
	public JTextField getTextField1(){
		return textField1;
	}
	
	public JTextField getTextField2(){
		return textField2;
	}
	
	
	public int getSliderValue(){
		return slider.getValue();
	}
}
