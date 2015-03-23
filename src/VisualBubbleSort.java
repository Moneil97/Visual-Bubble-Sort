import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class VisualBubbleSort extends JFrame{
	
	private Options ops;// = new Options();
	private JPanel panel;
	protected int blockWidth = -5;

	public VisualBubbleSort() {
		
		setSize(800,600);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		panel = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				if (blockWidth > -4){

					say("BlockWidth: " + blockWidth + "  x " +  this.getWidth()/blockWidth);
					g.setColor(Color.green);
					final int sorts = this.getWidth()/blockWidth;
					for (int i = 0; i < sorts; i++){
						g.fillRect(i*blockWidth, this.getHeight(), blockWidth, -Math.round((float)Math.random()*this.getHeight()));
					}
					
				}
				else{
					g.setColor(Color.red);
					for (int i=0; i < 1600; i+=2)
						g.fillRect(i, 800, 2, -Math.round((float)Math.random()*400));
				}
			}
			
		};
		
		getContentPane().add(panel, BorderLayout.CENTER);
		setVisible(true);
		ops = new Options();
		add(ops, BorderLayout.SOUTH);
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(panel.getSize());
			}
		});
		
	}
	
	class Options extends JPanel{
		
		public Options() {
			this.setLayout(new GridLayout(2,0));
			
			JPanel top = new JPanel();
			top.setLayout(new GridLayout(0,2));
			top.setBorder(new EmptyBorder(10, 10, 10, 10));
			this.add(top);
			JPanel left = new JPanel();
			top.add(left);
			left.setLayout(new BorderLayout());
			
			JLabel lblElementsToSort = new JLabel("Elements to Sort:");
			left.add(lblElementsToSort, BorderLayout.WEST);
			
			JSlider elementsSlider = new JSlider(0,100, 50);
			elementsSlider.setMinorTickSpacing(1);
			left.add(elementsSlider);
			JPanel right = new JPanel();
			top.add(right);
			right.setLayout(new BorderLayout());
			
			JLabel lblSpeed = new JLabel("Speed:");
			right.add(lblSpeed, BorderLayout.WEST);
			
			JSlider slider_1 = new JSlider();
			right.add(slider_1);
			
			JPanel bottom = new JPanel();
			bottom.setLayout(new GridLayout(0,2));
			bottom.setBorder(new EmptyBorder(0, 10, 10, 10));
			this.add(bottom);
			
			JButton btnStart = new JButton("Start");
			JButton btnStop = new JButton("Stop");
			btnStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					VisualBubbleSort.this.blockWidth = 101 - elementsSlider.getValue();
					VisualBubbleSort.this.repaint();
					
					elementsSlider.setEnabled(false);
					btnStop.setEnabled(true);	
					btnStart.setEnabled(false);	
				}
			});
			btnStop.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					elementsSlider.setEnabled(true);
					btnStart.setEnabled(true);	
					btnStop.setEnabled(false);	
				}
			});
			btnStop.setEnabled(false);
			
			bottom.add(btnStart);
			bottom.add(btnStop);
		}
		
	}
	
	public static void say(Object s){
		System.out.println(s);
	}

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){};
		
		new VisualBubbleSort();
	}

}
