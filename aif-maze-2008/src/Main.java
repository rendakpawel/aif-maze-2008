import java.awt.Point;
import java.util.Stack;

import exceptions.CellsAreNotNeighborsException;
import exceptions.OutOfBoardException;

import maze.Maze;


public class Main {

	/**
	 * The greatest maze project ever!
	 * 
	 * @author £ukasz Barcikowski
	 * @author Pawe³ Rendak
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		Maze maze = new Maze(9,9);
		maze.setDebugMode(false);
		
		try {
			maze.generateMaze();
		} catch (CellsAreNotNeighborsException e) {
			System.err.println("[Algorithm error] Generation tried to operate on non-neighboring cells!");
		} catch (OutOfBoardException e) {
			System.err.println("[Algorithm error] Generation went out of board range!");
			}
		maze.printMaze();
		
		maze.solveMaze();
		Stack<Point> mazeSolution = maze.getSolution();
		System.out.println("\nSolution (" + mazeSolution.size() + " steps):\n");
		for (Point point : mazeSolution) {
			System.out.println("Go to [" + point.x + "," + point.y + "]");
		}
	}
}
