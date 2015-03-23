import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class VisualBubbleSort extends JFrame{
	
	private Options ops;
	private JPanel panel;
	private Sorter sorter;
	protected static int sleepCounter = 50;

	public VisualBubbleSort() {
		
		setSize(800,600);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		panel = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				if (sorter != null) sorter.draw((Graphics2D) g);
				
			}
			
		};
		
		
		getContentPane().add(panel, BorderLayout.CENTER);
		setVisible(true);
		ops = new Options();
		add(ops, BorderLayout.SOUTH);
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				sorter = new Sorter(panel);
				VisualBubbleSort.this.repaint();
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
			
			JSlider elementsSlider = new JSlider(1,100, 50);
			elementsSlider.setInverted(true);
			elementsSlider.setMajorTickSpacing(10);
			elementsSlider.setMinorTickSpacing(1);
			elementsSlider.setPaintTicks(true);
			left.add(elementsSlider);
			JPanel right = new JPanel();
			top.add(right);
			right.setLayout(new BorderLayout());
			
			JLabel lblSpeed = new JLabel("Delay:");
			right.add(lblSpeed, BorderLayout.WEST);
			
			JSlider timeSlider = new JSlider(0,1000, 50);
			timeSlider.setMajorTickSpacing(100);
			timeSlider.setMinorTickSpacing(1);
			timeSlider.setPaintTicks(true);
			timeSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					sleepCounter = timeSlider.getValue();
				}
			});
			right.add(timeSlider);
			
			JPanel bottom = new JPanel();
			bottom.setLayout(new GridLayout(0,2));
			bottom.setBorder(new EmptyBorder(0, 10, 10, 10));
			this.add(bottom);
			
			JButton btnStart = new JButton("Start");
			JButton btnStop = new JButton("Stop");
			btnStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					sorter.setBlockWidth(elementsSlider.getValue());
					sorter.start();
					elementsSlider.setEnabled(false);
					btnStop.setEnabled(true);	
					btnStart.setEnabled(false);
					VisualBubbleSort.this.repaint();
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
