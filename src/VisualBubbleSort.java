import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class Sorter {

	private JPanel panel;
	private int blockWidth, blockAmount;
	private float[] blocks;
	private int currentBlock = 0;
	private int done = 0;
	private boolean running = false;
	private Thread thread;
	
	abstract void onComplete(); 

	public Sorter(JPanel panel) {
		this.panel = panel;
		blockWidth = 50;
		blockAmount = panel.getWidth()/blockWidth;
		blocks = new float[blockAmount];
		generateBlocks();
	}
	
	private void generateBlocks() {
		for (int i=0; i < blockAmount; i++)
			blocks[i] = (float) Math.random();
	}

	public void draw(Graphics2D g){
		
		
		for (int i=0; i < blocks.length; i++){
			int blockHeight = Math.round(blocks[i] * panel.getHeight());
			
			g.setColor(!running ? Color.green : (i == currentBlock || i == currentBlock +1 ? Color.blue : (i < blocks.length-done ? Color.red : Color.green)));
			g.fillRect(i*blockWidth, panel.getHeight()- blockHeight, blockWidth, blockHeight);
			
			if(blockWidth > 2){
				g.setColor(Color.black);
				g.drawRect(i*blockWidth, panel.getHeight()- blockHeight, blockWidth, blockHeight);
			}
		}
		
		g.setColor(Color.blue);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("Comparisons: " + comparisons, 10, 20);
		
	}

	public void setBlockWidth(int i) {
		blockWidth = i;
		blockAmount = panel.getWidth()/blockWidth;
		blocks = new float[blockAmount];
		generateBlocks();
	}

	long comparisons = 0;
	
	public void start() {

		thread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					running = true;
					comparisons = 0;

					boolean noChange = false;

					while (!noChange) {

						noChange = true;

						for (int i = 0; i < blocks.length - 1 - done; i++) {

							currentBlock = i;
							comparisons++;

							if (blocks[i] <= blocks[i + 1])
								continue;
							else {
								float temp = blocks[i];
								blocks[i] = blocks[i + 1];
								blocks[i + 1] = temp;
								noChange = false;
							}

							panel.repaint();

							Thread.sleep(VisualBubbleSort.sleepCounter);

						}
						done++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					done = 0;
					currentBlock = 0;
					running = false;
					thread = null;
					onComplete();
					panel.repaint();
					say("done");
				}
			}
		});

		thread.start();
	}
	
	public void stop(){
		try{
			thread.interrupt();
		}catch(Exception e){
			e.printStackTrace();
		};
	}
	
	
	
	public static void say(Object s){
		System.out.println(s);
	}
	
}
