import java.awt.Point;
import java.util.Stack;

import maze.Maze;
import exceptions.CellsAreNotNeighborsException;
import exceptions.MazeNotGeneratedException;
import exceptions.OutOfBoardException;
import gui.MazeFrame;



public class Main {

	/**
	 * The greatest maze project ever!
	 * 
	 * @author £ukasz Barcikowski
	 * @author Pawe³ Rendak
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
			
		
		Maze maze = new Maze(10,10);
		maze.setDebugMode(false);
		
		MazeFrame window = new MazeFrame(maze);
		window.setVisible(true);
		
		try {
			maze.generateMaze();
		} catch (CellsAreNotNeighborsException e) {
			System.err.println("[Algorithm error] Generation tried to operate on non-neighboring cells!");
		} catch (OutOfBoardException e) {
			System.err.println("[Algorithm error] Generation went out of board range!");
		}
		
		maze.printMaze();
	
		try {
			Stack<Point> mazeSolution = maze.solveMaze();
			System.out.println("\nSolution (" + mazeSolution.size() + " steps):\n");
			Point point; 
			for (int i = mazeSolution.size(); i > 0; i--) {
				point = mazeSolution.pop();
				System.out.println("Go to [" + point.x + "," + point.y + "]");
			}
		} catch (MazeNotGeneratedException e) {
			System.err.println("The maze cannot be solved because it hasn't been generated yet!");
		}
	}
}
