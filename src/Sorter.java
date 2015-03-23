import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class Sorter {

	private JPanel panel;
	private int blockWidth, blockAmount;
	private Color color = Color.green;
	private float[] blocks;

	public Sorter(JPanel panel) {
		this.panel = panel;
		blockWidth = 10;
		blockAmount = panel.getWidth()/blockWidth;
		blocks = new float[blockAmount];
		generateBlocks();
	}
	
	private void generateBlocks() {
		for (int i=0; i < blockAmount; i++)
			blocks[i] = (float) Math.random();
	}

	public void draw(Graphics2D g){
		
		if (running){
			g.setColor(Color.red);
			for (int i=0; i < blocks.length-done; i++)
				g.fillRect(i*blockWidth, panel.getHeight(), blockWidth, -Math.round(blocks[i] * panel.getHeight()));
			
			g.setColor(Color.green);
			for (int i=blocks.length-done; i < blocks.length; i++)
				g.fillRect(i*blockWidth, panel.getHeight(), blockWidth, -Math.round(blocks[i] * panel.getHeight()));
			
			g.setColor(Color.blue);
			g.fillRect(currentBlock*blockWidth, panel.getHeight(), blockWidth, -Math.round(blocks[currentBlock] * panel.getHeight()));
			g.fillRect((currentBlock+1)*blockWidth, panel.getHeight(), blockWidth, -Math.round(blocks[(currentBlock+1)] * panel.getHeight()));
		}
		else{
			g.setColor(Color.green);
			for (int i=0; i < blocks.length; i++)
				g.fillRect(i*blockWidth, panel.getHeight(), blockWidth, -Math.round(blocks[i] * panel.getHeight()));
		}
		
		
	}

	public void setBlockWidth(int i) {
		blockWidth = i;
		blockAmount = panel.getWidth()/blockWidth;
		blocks = new float[blockAmount];
		generateBlocks();
	}

	int currentBlock = 0;
	int done = 0;

	boolean running = false;
	
	public void start() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				running  = true;
				
				boolean noChange = false;
				
				while (!noChange){
					
					noChange = true;
					
					for (int i=0; i < blocks.length-1-done; i++){
						
						currentBlock = i;
						
						if (blocks[i] <= blocks[i+1])
							continue;
						else{
							float temp = blocks[i];
							blocks[i] = blocks[i+1];
							blocks[i+1] = temp;
							noChange = false;
						}
						
						panel.repaint();
						
						try {
							Thread.sleep(VisualBubbleSort.sleepCounter);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					done++;
					say(done);
				}
				
				say("done");
				running = false;
				panel.repaint();
			}
		}).start();
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public static void say(Object s){
		System.out.println(s);
	}
	

}
