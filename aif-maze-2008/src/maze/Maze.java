package maze;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import exceptions.CellsAreNotNeighborsException;
import exceptions.OutOfBoardException;

/**
 * This class is an extension of Board class
 * and provides functionality connected with 
 * using Board as a maze.
 * New instance of maze automatically generates 
 * new maze by removing properly random generated 
 * walls.
 * 
 * @author £ukasz Barcikowski
 */
public class Maze extends Board {
	
	/**
	 * Contains information on cells visited my sprite.
	 */
	private Stack<Integer> mySolution;
	
	/**
	 * This field is used for generating the maze.
	 * It holds information on visited cells wh
	 */
	private List<Integer> visitedCells;
	
	/**
	 * Stack used by generation algorithm.
	 */
	private Stack<Integer> generationStack;
	
	/**
	 * Key identifying entry coordinates.
	 * Key is of form XXXXYYYY, where
	 * XXXX is x coordinate and YYYY is y
	 * coordinate. For generating and parsed
	 * bye getKey() and parseKey() methods.
	 */
	private int myEntry;
	
	/**
	 * Key identifying exit coordinates.
	 * Key is of form XXXXYYYY, where
	 * XXXX is x coordinate and YYYY is y
	 * coordinate. For generating and parsed
	 * bye getKey() and parseKey() methods.
	 */
	private int myExit;
	
	/**
	 * X coordinate of sprite.
	 * Used for debug purposes.
	 */
	private int spriteX = 0;
	
	/**
	 * Y coordinate of sprite.
	 * Used for debug purposes.
	 */
	private int spriteY = 0;
	
	/**
	 * Generates new maze basing on new Board.
	 * @param width Width of maze board.
	 * @param height Height of maze board.
	 */
	public Maze(int width, int height) {
		super(width, height);
		this.generationStack = new Stack<Integer>();
		this.visitedCells = new Vector<Integer>();
		this.mySolution = new Stack<Integer>();

		try {
			this.generateMaze();
		} catch (CellsAreNotNeighborsException e) {
			System.err.println("Generation of the maze tryied to operate on non neighbors...");
		} catch (OutOfBoardException e) {
			System.err.println("Generation of the maze somehow got out of the board...");
		}
	}
	
	/**
	 * This is one of the most important methods. It generates
	 * the maze by removing specific walls from the preset board
	 * using so called stack based algorithm.
	 * 
	 * @throws CellsAreNotNeighborsException Thrown when generation 
	 * tries to operate on non-neighboring cells.
	 * @throws OutOfBoardException Thrown when generation exceeds board.
	 */
	private void generateMaze() throws CellsAreNotNeighborsException, OutOfBoardException {
		System.out.println("Starting maze generation...");
		Random rnd = new Random();
		
		System.out.println("Starting first junction...");
		/*
		 * Selecting random cell from which the
		 * generation of the maze shell start and
		 * its random neighbor.
		 */
		int currentX = rnd.nextInt(this.myWidth - 1) + 1;
		int currentY = rnd.nextInt(this.myHeight - 1) + 1;	
		
		System.out.println("Initial cell is [" + currentX + "," + currentY + "]");
		
		int nextX, nextY;
				
		System.out.println("First junction started!");
		
		int cellAmount = this.myHeight * this.myWidth;
		while(this.visitedCells.size() < cellAmount - 1) {
			if(!this.generationStack.contains(this.getKey(currentX, currentY)))
				this.generationStack.push(this.getKey(currentX, currentY));
			if(!this.visitedCells.contains(this.getKey(currentX, currentY)))
				this.visitedCells.add(this.getKey(currentX, currentY));
			
			/*
			 * Checking for unvisited neighbors.
			 * 
			 * Case 1:
			 * If there are some unvisited neighbors get random unvisited 
			 * neighboring cell, then destroy wall between current cell
			 * and newly selected one and switch current to it. 
			 * New cell is added to list of visited cells and pushed onto stack.
			 */
			if(this.hasUnvisitedNeighbors(currentX, currentY)) {
				int tmpNeighbor; 
				do {
					tmpNeighbor = this.getRandomNeighbor(currentX, currentY);
				} while (this.visitedCells.contains(tmpNeighbor));
				int[] tmp = this.parseKey(tmpNeighbor);
				nextX = tmp[0];
				nextY = tmp[1];
				try {
					if(this.isWallBetweenCells(currentX, currentY, nextX, nextY)) 
						this.setWallBetweenCells(currentX, currentY, nextX, nextY, false);
					currentX = nextX;
					currentY = nextY;
				} catch (CellsAreNotNeighborsException e) {
					throw new CellsAreNotNeighborsException();
				} catch (OutOfBoardException e) {
					throw new OutOfBoardException();
				}
				
				System.out.println("Current cell [" + currentX + "," + currentY + "]");
			}			
			/*
			 * Case 2:
			 * If there are no unvisited neighbors backtrack the stack until
			 * some cell with unvisited neighbors is found and proceed with case 1
			 * from new junction.
			 */
			else {
				System.out.println();
				this.printMaze();
				System.out.println();
				System.out.println("Starting new junction...");
				while(!this.hasUnvisitedNeighbors(currentX, currentY)) {
					int prevCell = this.generationStack.pop();
					// this.generationStack.remove(prevCell);
					int[] tmp = this.parseKey(prevCell);
					currentX = tmp[0];
					currentY = tmp[1];
				}
			}			
		}
		
		/*
		 * Setting up exits...
		 */
		this.chooseExits();
		System.out.println("Setting up exits...");
		System.out.println();
		this.printMaze();
		System.out.println();
	}
	
	/**
	 * This method sets entry and exit points on 
	 * outer bounds of maze board. Each of selected
	 * cells is selected on different wall.
	 */
	private void chooseExits() {
		Random rnd = new Random();
		int entryWall = rnd.nextInt(4);
		int exitWall;
		do {
			exitWall = rnd.nextInt(4);
		} while(exitWall == entryWall);
		
		try {
			// North wall
			if(entryWall == 0) {
				this.myEntry = this.getKey(rnd.nextInt(this.myWidth) + 1,1);
				int[] entry = parseKey(this.myEntry);
				this.setNorthWall(entry[0], entry[1], false);
			}
			// East wall
			else if(entryWall == 1) {
				this.myEntry = this.getKey(this.myWidth, rnd.nextInt(this.myHeight) + 1);
				int[] entry = parseKey(this.myEntry);
				this.setEastWall(entry[0], entry[1], false);
			}
			// South wall
			else if(entryWall == 2) {
				this.myEntry = this.getKey(rnd.nextInt(this.myWidth) + 1, this.myHeight);
				int[] entry = parseKey(this.myEntry);
				this.setSouthWall(entry[0], entry[1], false);
			}
			// West wall
			else if(entryWall == 3) {
				this.myEntry = this.getKey(1, rnd.nextInt(this.myHeight) + 1);
				int[] entry = parseKey(this.myEntry);
				this.setWestWall(entry[0], entry[1], false);
			}
				
			// North wall
			if(exitWall == 0) {
				this.myExit = this.getKey(rnd.nextInt(this.myWidth) + 1,1);
				int[] exit = parseKey(this.myExit);
				this.setNorthWall(exit[0], exit[1], false);
			}
			// East wall
			else if(exitWall == 1) {
				this.myExit = this.getKey(this.myWidth, rnd.nextInt(this.myHeight) + 1);
				int[] exit = parseKey(this.myExit);
				this.setEastWall(exit[0], exit[1], false);
			}
			// South wall
			else if(exitWall == 2) {
				this.myExit = this.getKey(rnd.nextInt(this.myWidth) + 1, this.myHeight);
				int[] exit = parseKey(this.myExit);
				this.setSouthWall(exit[0], exit[1], false);
			}
			// West wall
			else if(exitWall == 3) {
				this.myExit = this.getKey(1, rnd.nextInt(this.myHeight) + 1);
				int[] exit = parseKey(this.myExit);
				this.setWestWall(exit[0], exit[1], false);
			}
		} catch (OutOfBoardException e) {
			System.err.println("Exit chosen out of board range!");
		}
		
	}
	
	/**
	 * This method allows to check if provided cell
	 * has some yet unvisited neighbors.
	 * @param x X coordinate for cell to be checked.
	 * @param y Y coordinate for cell to be checked.
	 * @return True if there are some unvisited neighbors, false otherwise.
	 */
	private boolean hasUnvisitedNeighbors(int x, int y) {
		int[] neighbors = new int[4];
		boolean[] possibleDirections = new boolean[4];
		for(int i = 0; i < 4; i++)
			possibleDirections[i] = true;
		
		// North neighbor
		if(y == 1) possibleDirections[0] = false; 
		neighbors[0] = this.getKey(x, y - 1);
		// East neighbor
		if(x == this.myWidth) possibleDirections[1] = false; 
		neighbors[1] = this.getKey(x + 1, y);
		// South neighbor
		if(y == this.myHeight) possibleDirections[2] = false; 
		neighbors[2] = this.getKey(x, y + 1);
		// West neighbor
		if(x == 1) possibleDirections[3] = false; 
		neighbors[3] = this.getKey(x - 1, y);
		
		for(int i = 0; i < 4; i++)
			if(possibleDirections[i] && !this.visitedCells.contains(neighbors[i]))
				return true;
		
		return false;
	}
	
	/**
	 * This method checks if there is wall set between two 
	 * provided cells.
	 * @param x1 X coordinate of first cell.
	 * @param y1 Y coordinate of first cell.
	 * @param x2 X coordinate of second cell.
	 * @param y2 Y coordinate of second cell.
	 * @return True if there is wall between provided cells, false otherwise.
	 * @throws CellsAreNotNeighborsException Thrown if provided cells are not neighbors.
	 * @throws OutOfBoardException Thrown if any of provided cells is outside the board.
	 */
	private boolean isWallBetweenCells(int x1,int y1,int x2,int y2) throws CellsAreNotNeighborsException, OutOfBoardException {
		if(Math.abs(x2 - x1) > 1 || Math.abs(y2 - y1) > 1 || (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1))
			throw new CellsAreNotNeighborsException();
		try {
			if(x2 > x1)
				return this.getEastWall(x1, y1);
			else if(x2 < x1)
				return this.getWestWall(x1, y1);
			else if(y1 < y2)
				return this.getSouthWall(x1, y1);
			else
				return this.getNorthWall(x1, y1);
		} catch (OutOfBoardException e) {
			throw new OutOfBoardException();
		}		
	}
	
	/**
	 * Sets the wall between two provided cells.
	 * @param x1 X coordinate of first cell.
	 * @param y1 Y coordinate of first cell.
	 * @param x2 X coordinate of second cell.
	 * @param y2 Y coordinate of second cell.
	 * @param exists Desired state of the wall.
	 * @throws CellsAreNotNeighborsException Thrown if provided cells are not neighbors.
	 * @throws OutOfBoardException Thrown if any of provided cells is outside the board.
	 */
	private void setWallBetweenCells(int x1,int y1,int x2,int y2, boolean exists) throws CellsAreNotNeighborsException, OutOfBoardException {
		if(Math.abs(x2 - x1) > 1 || Math.abs(y2 - y1) > 1 || (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1))
			throw new CellsAreNotNeighborsException();
		try {
			if(x2 > x1)
				this.setEastWall(x1, y1, exists);
			else if(x2 < x1)
				this.setWestWall(x1, y1, exists);
			else if(y1 < y2)
				this.setSouthWall(x1, y1, exists);
			else
				this.setNorthWall(x1, y1, exists);
		} catch (OutOfBoardException e) {
			throw new OutOfBoardException();
		}	
	}
	
	/**
	 * Returns key representing the location of
	 * randomly selected neighbor of provided cell.
	 * @param x X coordinate of cell for which neighbor is to be selected.
	 * @param y Y coordinate of cell for which neighbor is to be selected.
	 * @return Location of neighbor as location key.
	 */
	private int getRandomNeighbor(int x, int y) {
		Random rnd = new Random();
		boolean[] possibleDirections = new boolean[4];
		for(int i = 0; i < 4; i++)
			possibleDirections[i] = true;
		
		if(y == 1) possibleDirections[0] = false; // top wall
		if(x == this.myWidth) possibleDirections[1] = false; // right wall
		if(y == this.myHeight) possibleDirections[2] = false; // bottom wall
		if(x == 1) possibleDirections[3] = false; // left wall
		
		int direction;
		do {
			direction = rnd.nextInt(4);
		} while(!possibleDirections[direction]);
		
		switch(direction) {
		case 0:
			return this.getKey(x, y - 1);
		case 1:
			return this.getKey(x + 1, y);
		case 2:
			return this.getKey(x, y + 1);
		case 3:
			return this.getKey(x - 1, y);
		}
		return -1;
	}
	
	/**
	 * Provides a unique key for given coordinates.
	 * Accepted value range is (1,999).
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return Unique key.
	 */
	private int getKey(int x,int y) {
		return x * 10000 + y;
	}
	
	/**
	 * Parses a unique key for given coordinates.
	 * @param key Key to be parsed.
	 * @return Array of two integers [0] - x coordinate and [1] - y coordinate.
	 */
	private int[] parseKey(int key) {
		int y = key % 10000;
		int x = (key - y) / 10000;
		int[] coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
		return coordinates;
	}
	
	/**
	 * Prints to standard output the maze 
	 * with (if any) actual location of the sprite.
	 */
	public void printMaze() {
		try {
			
			for(int i = 1; i <= this.myWidth; i++)
				if(this.getNorthWall(i, 1)) {
					System.out.print(" _");
				}
				else {
					System.out.print("  ");
				}
					
			
			System.out.print("\n");
			
			for(int j = 1; j <= this.myHeight; j++) {
				for(int i = 1; i <= this.myWidth; i++) {
		
					if(spriteX == i && spriteY == j) {
						System.out.print(" *");
						continue;
					}
						
					if(this.getWestWall(i, j)) {
						System.out.print("|");
					}
					else {
						System.out.print(" ");
					}
					
					if(this.getSouthWall(i, j) && i < this.myWidth) {
						System.out.print("_");
					}

					else if(i == this.myWidth && this.getEastWall(i, j) && this.getSouthWall(i, j)) {
						System.out.print("_|");
					}
					else if(i == this.myWidth && this.getEastWall(i, j) && !this.getSouthWall(i, j)) {
						System.out.print(" |");
					}
					else if(i == this.myWidth && !this.getEastWall(i, j) && this.getSouthWall(i, j)) {
						System.out.print("_ ");
					}
					else if(i == this.myWidth && !this.getEastWall(i, j) && !this.getSouthWall(i, j)) {
						System.out.print("  ");
					}
					
					else {
						System.out.print(" ");
					}
				}
				
				System.out.print("\n");
			}
		} catch (OutOfBoardException e) {
			System.err.println("Printing went out of range!");
		}
	}
	
	/**
	 * Solves the maze using so called right hand rule.
	 * The way out is registered on a solution stack.
	 */
	public void solveMaze() {
		int[] start = this.parseKey(this.myEntry);
		int[] end = this.parseKey(this.myExit);
		int currentX = start[0];
		int currentY = start[1];
		
		/*
		 * Directions:
		 * 0 - North
		 * 1 - East
		 * 2 - South
		 * 3 - West
		 */
		int direction = 1;
		if(currentX == this.myWidth) direction = 3;
		if(currentY == this.myHeight) direction = 0;
		if(currentX == 1) direction = 1;
		if(currentY == 1) direction = 2;
		
		String[] directions = new String[4];
		directions[0] = "NORTH";
		directions[1] = "EAST";
		directions[2] = "SOUTH";
		directions[3] = "WEST";
		
		while(this.getKey(currentX, currentY) != this.getKey(end[0], end[1])) {
			System.out.println("Current cell [" + currentX + "," + currentY + "]");
			System.out.println("Current direction " + directions[direction]);
			this.spriteX = currentX;
			this.spriteY = currentY;
			this.printMaze();
			try {
				// Sprite directed north
				if(direction == 0 && !this.getEastWall(currentX, currentY)) {
					// Go east...
					currentX++;
					direction = 1;
					int currentKey = this.getKey(currentX, currentY);
					this.mySolution.push(currentKey);
					continue;
				}
				else if(direction == 0 && this.getEastWall(currentX, currentY)) {
					direction = 3;
					continue;
				}
				
				// Sprite directed east
				if(direction == 1 && !this.getSouthWall(currentX, currentY)) {
					// Go south...
					currentY++;
					direction = 2;
					int currentKey = this.getKey(currentX, currentY);
					this.mySolution.push(currentKey);
					continue;
				}
				else if(direction == 1 && this.getSouthWall(currentX, currentY)) {
					direction = 0;
					continue;
				}
				
				// Sprite directed south
				if(direction == 2 && !this.getWestWall(currentX, currentY)) {
					// Go west...
					currentX--;
					direction = 3;
					int currentKey = this.getKey(currentX, currentY);
					this.mySolution.push(currentKey);
					continue;
				}
				else if(direction == 2 && this.getWestWall(currentX, currentY)) {
					direction = 1;
					continue;
				}
				
				// Sprite directed west
				if(direction == 3 && !this.getNorthWall(currentX, currentY)) {
					// Go north...
					currentY--;
					direction = 0;
					int currentKey = this.getKey(currentX, currentY);
					this.mySolution.push(currentKey);
					continue;
				}
				else if(direction == 3 && this.getNorthWall(currentX, currentY)) {
					direction = 2;
					continue;
				}
			} catch (OutOfBoardException e) {
				System.err.println("Sprite went out of range!");
			}
		}
	}
	
	/**
	 * Returns the solution of the maze as a stack
	 * of Points starting from exit (bottom of stack)
	 * to entry (top of the stack).
	 * @return Solution back trace stack.
	 */
	public Stack<Point> getSolution() {
		Stack<Point> solution = new Stack<Point>();
		for(int i = this.mySolution.size() - 1; i <= 0; i--) {			
			int key = this.mySolution.elementAt(i);
			this.mySolution.remove(i);
			int[] coords = this.parseKey(key);
			solution.push(new Point(coords[0],coords[1]));
		}
		return solution;
	}
	
	/**
	 * Returns coordinates of entry point of the maze.
	 * @returnArray of two integers [0] - x coordinate and [1] - y coordinate.
	 */
	public int[] getEntry() {
		return this.parseKey(this.myEntry);	
	}
	
	/**
	 * Returns coordinates of exit point of the maze.
	 * @return Array of two integers [0] - x coordinate and [1] - y coordinate.
	 */
	public int[] getExit() {
		return this.parseKey(this.myExit);
	}
}
