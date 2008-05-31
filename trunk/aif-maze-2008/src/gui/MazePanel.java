package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JPanel;

import maze.Maze;
import exceptions.CellsAreNotNeighborsException;
import exceptions.MazeNotGeneratedException;
import exceptions.OutOfBoardException;

/**
 * This class is extension of the JPanel. It provides graphical representation
 * of generated and solved maze.
 * 
 * @author Pawe³ Rendak
 * 
 */
public class MazePanel extends JPanel {

	final static long serialVersionUID = -1234234234234L;

	/**
	 * Scale defines size for each 'block' of the maze. By default it has value
	 * 10, i.e. every block is represented as square of size 10x10 pixels.
	 * Smallest possible value is 2 (otherwise i.e. 0,1 one cannot spot
	 * anything).
	 */
	static int scale = 10;

	/**
	 * Reference to maze which initialized MazePanel. This maze will be drawn
	 */
	Maze drawingMaze = null;

	/**
	 * Image on which maze will be drawn.
	 */
	private static BufferedImage bufferedImage = null; // @jve:decl-index=0:

	/**
	 * Stack for the solution of the maze.
	 */
	Stack<Point> mazeSolution = null;

	/**
	 * Creates new instance of MazePanel.
	 * 
	 * @param mazeWidth
	 *            Width of the maze.
	 * @param mazeHeight
	 *            Height of the maze.
	 */
	MazePanel(int mazeWidth, int mazeHeight) {
		drawingMaze = new Maze(mazeWidth, mazeHeight);

		/*
		 * Width and height is enlarged by two times the scale, each of four
		 * margins is of size of the scale.
		 */
		super.setSize(scale * drawingMaze.getWidth() + 2 * scale, scale
				* drawingMaze.getHeight() + 2 * scale);
		initialize();
	}

	/**
	 * Generates the maze, handles the exceptions (prints them in standard
	 * error).
	 */
	public void generateMaze() {
		try {
			drawingMaze.generateMaze();
		} catch (CellsAreNotNeighborsException e) {
			System.err
					.println("[Algorithm error] Generation tried to operate on non-neighboring cells!");
		} catch (OutOfBoardException e) {
			System.err
					.println("[Algorithm error] Generation went out of board range!");
		}
	}

	/**
	 * Draws maze solution on the bufferedImage.
	 */
	public void drawSolveMaze() {
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setBackground(Color.white);
		g2d.setColor(Color.red);

		try {
			Stack<Point> mazeSolution = drawingMaze.solveMaze();

			Point point;
			boolean start = true;
			Point tmp = null;

			for (int i = mazeSolution.size(); i > 0; i--) {
				point = mazeSolution.pop();

				/*
				 * Drawing the lines, if it is first on the stack then do not
				 * draw and make this point as temporary point. Otherwise draw
				 * line temporary->current point and make point as temporary
				 * point.
				 */
				if (start) {
					start = false;
					tmp = point;
				} else {
					g2d.drawLine(tmp.x * scale + 5, tmp.y * scale + 5, point.x
							* scale + 5, point.y * scale + 5);
					tmp = point;
				}
			}
			
			/*
			 * Drawing the entrance
			 */
			int[] entry = drawingMaze.getEntry();
			markOneField(g2d, Color.blue, entry);
			
			/*
			 * Drawing the exit
			 */
			int[] exit = drawingMaze.getExit();
			markOneField(g2d, Color.black, exit);
			
			repaint();
		} catch (MazeNotGeneratedException e) {
			System.err
					.println("The maze cannot be solved because it hasn't been generated yet!");
		}

	}

	/**
	 * Draws in given point on the board 'X'  
	 * @param g2d Graphics on which drawing takes place.
	 * @param color Color of 'X'.
	 * @param point Point on the board on which 'X' is drawn.
	 */
	private void markOneField(Graphics g2d,Color color, int[] point) {
		g2d.setColor(color);
		
		/*
		 * code above draws nice 'X' i color assigned above
		 */
		g2d.drawLine(point[0] * scale + 3, point[1] * scale + 3, point[0]
				* scale + scale - 3, point[1] * scale + scale - 3);
		g2d.drawLine(point[0] * scale + scale - 3, point[1] * scale + 3,
				point[0] * scale + 3, point[1] * scale + scale - 3);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setBackground(new Color(255, 204, 153));
		bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
	}

	/**
	 * This function draws generated maze. It uses Board's two-dimensional
	 * tables contains information about existence of the maze walls. It checks
	 * if wall exists. If yes draws it in proper place and scale on the
	 * bufferedImage.
	 */
	public void drawGeneratedMaze() {

		if (drawingMaze.isGenerated) {
			/*
			 * Obtaining graphics from image, setting the background color,
			 * clearing foreground and setting color for walls.
			 */
			Graphics2D g2d = bufferedImage.createGraphics();
			g2d.setBackground(Color.white);
			g2d.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage
					.getHeight());
			g2d.setColor(Color.black);

			/*
			 * Drawing horizontal walls leaving left and right margin.
			 */
			boolean[][] hwalls = drawingMaze.getHorizontalWalls();
			for (int i = 1; i < hwalls.length - 1; i++) {
				for (int j = 1; j < hwalls[i].length; j++) {
					if (hwalls[i][j]) {
						g2d.draw(new Line2D.Double(i * scale, j * scale, i
								* scale + scale, j * scale));
					}
				}
			}

			/*
			 * Drawing vertical walls leaving top and bottom margin.
			 */
			boolean[][] vwalls = drawingMaze.getVerticalWalls();
			for (int i = 1; i < vwalls.length; i++) {
				for (int j = 1; j < vwalls[i].length - 1; j++) {
					if (vwalls[i][j]) {
						g2d.draw(new Line2D.Double(i * scale, j * scale, i
								* scale, j * scale + scale));
					}
				}
			}
			repaint();
		} else
			System.err
					.println("[MazePanel error] Cannot draw the maze. Maze is not generated!");
	}

	/**
	 * Typical paint function. Checks if graphics was created and draws the
	 * image.
	 */
	public void paint(Graphics g) {
		if (g == null)
			return;
		g.drawImage(bufferedImage, 0, 0, null);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
