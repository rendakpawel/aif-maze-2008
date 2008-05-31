package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import maze.Maze;

/**
 * This class is extension of the JPanel.
 * It provides graphical representation of 
 * generating and solving the maze.
 * @author Pawe³ Rendak
 *
 */
public class MazePanel extends JPanel {

	/**
	 * Scale defines size for each 'block' of the maze.
	 * By default it has value 10, i.e. every block is
	 * represented as square of size 10x10 pixels. 
	 */
	static int scale = 10;

	/**
	 * Creates new instance of MazePanel.
	 * @param mazeWidth Width of the maze. 
	 * @param mazeHeight Height of the maze.
	 */
	MazePanel(Maze maze) {
		super.setSize(scale*maze.getWidth(), scale*maze.getHeight());
		super.setPreferredSize(new Dimension(scale*maze.getWidth(), scale*maze.getHeight()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setBackground(new Color(255, 204, 153));
        
		bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		raster = bufferedImage.getRaster();
	}
	
	public void drawSTH(){
		
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawLine(10, 10, 40, 40);
		
	}
	
	private static BufferedImage bufferedImage = null;
	private static WritableRaster raster = null;
	
	
	public void update(Graphics g) {

//		long startTime = System.currentTimeMillis();

		if (g == null)
			return;

		Graphics2D g2d = (Graphics2D) (bufferedImage.getGraphics());
		g2d.setBackground(Color.cyan);
		g2d
				.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage
						.getHeight());

		 drawSTH();

		g.drawImage(bufferedImage, 0, 0, null);

//		long time = System.currentTimeMillis() - startTime;
//		System.out.println("Update: " + time + " milisec");
	}
	
	public void paint(Graphics g) {
		this.update(g);
	}
}
