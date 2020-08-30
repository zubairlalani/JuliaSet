import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
public class JuliaSetProgram2 extends JPanel implements AdjustmentListener, ActionListener{

	JFrame frame;
	JPanel scrollPanel, boxPanel, connectPanel, labelPanel, buttonPanel;
	//f(x) = a+bi
	double real = 0;	 //"a"
	double complex = 0; //"b"
    double zoom = 1;
	float saturation = 0;
	float brightness = 1f;
	JScrollBar realBar, complexBar, zoomBar, saturationBar, brightnessBar;
	JLabel rLabel, cLabel, zLabel, sLabel, bLabel;
	int width = 1500, height = 800;

	JButton reset;
	BufferedImage im;

	float maxIterations = 50;

	double zx, zy;

	public JuliaSetProgram2(){
		frame = new JFrame("Julia Set Program");
		frame.add(this);

		reset = new JButton("Reset to default");
		reset.addActionListener(this);

		realBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -20000, 20000);
		realBar.addAdjustmentListener(this);

		complexBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -20000, 20000);
		complexBar.addAdjustmentListener(this);

		zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 10000, 0, 10000, 80000);
		zoomBar.addAdjustmentListener(this);

		saturationBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 10000);
		saturationBar.addAdjustmentListener(this);

		brightnessBar = new JScrollBar(JScrollBar.HORIZONTAL, 100000, 0, 0, 100000);
		brightnessBar.addAdjustmentListener(this);

		scrollPanel = new JPanel();
		scrollPanel.setLayout(new GridLayout(5, 1));
		scrollPanel.add(realBar);
		scrollPanel.add(complexBar);
		scrollPanel.add(zoomBar);
		scrollPanel.add(saturationBar);
		scrollPanel.add(brightnessBar);

		rLabel = new JLabel("Real:  ", JLabel.LEFT);
		cLabel = new JLabel("Complex:  ", JLabel.LEFT);
		zLabel = new JLabel("Zoom:  ", JLabel.LEFT);
		sLabel = new JLabel("Saturation:  ", JLabel.LEFT);
		bLabel = new JLabel("Brightness:  ", JLabel.LEFT);

		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(5, 1));
		labelPanel.add(rLabel);
		labelPanel.add(cLabel);
		labelPanel.add(zLabel);
		labelPanel.add(sLabel);
		labelPanel.add(bLabel);


		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(reset, BorderLayout.EAST);



		connectPanel = new JPanel();
		connectPanel.setLayout(new BorderLayout());
		connectPanel.add(scrollPanel, BorderLayout.CENTER);
		connectPanel.add(labelPanel, BorderLayout.WEST);


		frame.add(connectPanel, BorderLayout.SOUTH);
		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(juliaFunc(), 0, 0, null);
	}
	public void adjustmentValueChanged(AdjustmentEvent e){
		if(e.getSource() == realBar){
			real = realBar.getValue()/10000.0;
		}
		if(e.getSource() == complexBar){
			complex = complexBar.getValue()/10000.0;
		}
		if(e.getSource() == zoomBar){
			zoom = zoomBar.getValue()/10000.0;
		}
		if(e.getSource() == saturationBar){
			saturation = saturationBar.getValue()/10000.0f;
		}
		if(e.getSource() == brightnessBar){
			brightness = brightnessBar.getValue()/100000.0f;
		}
		repaint();

	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == reset){
			realBar.setValue(0);
			complexBar.setValue(0);
			zoomBar.setValue(10000);
			saturationBar.setValue(0);
			brightnessBar.setValue(100000);
		}
		repaint();
	}

	public BufferedImage juliaFunc(){
		im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		zx=0;
		zy=0;

		for(int x=0; x<width; x++){

			for(int y=0; y<height; y++){

				float i = maxIterations;

				zx = 1.5*(x-.5*width)/(.5*width*zoom);
				zy = (y-.5*height)/(.5*zoom * height);


				while((Math.pow(zx,2) + Math.pow(zy, 2)) < 6 && i > 0){
					double val = (Math.pow(zx,2) - Math.pow(zy, 2)) + real;
					zy = 2*zx*zy + complex;
					zx=val;
					i--;
				}

				int c;
				if(i>0){

					c= Color.HSBtoRGB((maxIterations / i) % 1, saturation, brightness);
				}
				else{

					c = Color.HSBtoRGB(maxIterations / i, saturation, 0);

				}

				im.setRGB(x, y, c);
			}
		}

		return im;
	}

	public static void main(String[] args)
	{
		JuliaSetProgram2 app = new JuliaSetProgram2();
    }
}