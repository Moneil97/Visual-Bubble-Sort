import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class VisualBubbleSort extends JFrame{
	
	private Options ops = new Options();

	public VisualBubbleSort() {
		
		setSize(800,600);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.red);
				for (int i=0; i < 200; i++)
					g.fillRect(i, 800, 1, -Math.round((float)Math.random()*100));
			}
			
		};
		getContentPane().add(panel, BorderLayout.CENTER);
		
		System.out.println(panel.getSize());
		
		add(ops, BorderLayout.SOUTH);
		setVisible(true);
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
			
			JSlider elementsSlider = new JSlider();
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
	

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){};
		
		new VisualBubbleSort();
	}

}
