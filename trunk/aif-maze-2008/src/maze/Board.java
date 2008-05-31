package maze;

import exceptions.OutOfBoardException;

/**
 * This class represents a board that has functionality of handling it's walls
 * (i.e. enabling or disabling walls).
 * 
 * @author £ukasz Barcikowski
 * @author Pawe³ Rendak
 * 
 */
public class Board {

	/**
	 * Boolean representation of vertical walls of the grid.
	 */
	private boolean[][] verticalWalls;

	/**
	 * Boolean representation of vertical walls of the grid.
	 */
	private boolean[][] horizontalWalls;

	/**
	 * The width of the grid.
	 */
	protected int myWidth;

	/**
	 * The height of the grid.
	 */
	protected int myHeight;

	/**
	 * Initializes the board by creating new grid and filling it with blocks.
	 * 
	 * @param width
	 *            Width of the board.
	 * @param height
	 *            Height of the board.
	 */
	public Board(int width, int height) {

		this.myHeight = height;
		this.myWidth = width;
		this.verticalWalls = new boolean[this.myWidth + 2][this.myHeight + 2];
		this.horizontalWalls = new boolean[this.myWidth + 2][this.myHeight + 2];

		for (int x = 0; x < width + 2; x++) {
			for (int y = 0; y < height + 2; y++) {
				this.horizontalWalls[x][y] = true;
				this.verticalWalls[x][y] = true;
			}
		}
	}

	/**
	 * Checks the existence of east wall of the cell with coordinates (x,y).
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @return True if wall exist, false otherwise.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	public boolean getEastWall(int x, int y) throws OutOfBoardException {
		try {
			return this.verticalWalls[x + 1][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}

	}

	/**
	 * Checks the existence of west wall of the cell with coordinates (x,y).
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @return True if wall exist, false otherwise.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	public boolean getWestWall(int x, int y) throws OutOfBoardException {
		try {
			return this.verticalWalls[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Checks the existence of north wall of the cell with coordinates (x,y).
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @return True if wall exist, false otherwise.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	public boolean getNorthWall(int x, int y) throws OutOfBoardException {
		try {
			return this.horizontalWalls[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Checks the existence of south wall of the cell with coordinates (x,y).
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @return True if wall exist, false otherwise.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	public boolean getSouthWall(int x, int y) throws OutOfBoardException {
		try {
			return this.horizontalWalls[x][y + 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Sets given wall to existing or non existing state. Value "true" indicates
	 * that wall exist, "false" means that there is no wall present.
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @param exists
	 *            Existence of the wall.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	protected void setEastWall(int x, int y, boolean exists)
			throws OutOfBoardException {
		try {
			this.verticalWalls[x + 1][y] = exists;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Sets given wall to existing or non existing state. Value "true" indicates
	 * that wall exist, "false" means that there is no wall present.
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @param exists
	 *            Existence of the wall.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	protected void setWestWall(int x, int y, boolean exists)
			throws OutOfBoardException {
		try {
			this.verticalWalls[x][y] = exists;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Sets given wall to existing or non existing state. Value "true" indicates
	 * that wall exist, "false" means that there is no wall present.
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @param exists
	 *            Existence of the wall.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	protected void setNorthWall(int x, int y, boolean exists)
			throws OutOfBoardException {
		try {
			this.horizontalWalls[x][y] = exists;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Sets given wall to existing or non existing state. Value "true" indicates
	 * that wall exist, "false" means that there is no wall present.
	 * 
	 * @param x
	 *            X coordinate of the cell.
	 * @param y
	 *            Y coordinate of the cell.
	 * @param exists
	 *            Existence of the wall.
	 * @throws OutOfBoardException
	 *             Occurs when desired cell does not belong to board.
	 */
	protected void setSouthWall(int x, int y, boolean exists)
			throws OutOfBoardException {
		try {
			this.horizontalWalls[x][y + 1] = exists;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new OutOfBoardException();
		} catch (NullPointerException e) {
			throw new OutOfBoardException();
		}
	}

	/**
	 * Resets board to initial state.
	 */
	protected void resetBoard() {
		for (int x = 0; x < this.myWidth + 2; x++) {
			for (int y = 0; y < this.myHeight + 2; y++) {
				this.horizontalWalls[x][y] = true;
				this.verticalWalls[x][y] = true;
			}
		}
	}
	
	/**
	 * Gets height of the board.
	 * @return Returns integer value - myHeight.
	 */
	public int getHeight() {
		return this.myHeight;

	}
	
	/**
	 * Gets width of the board.
	 * @return Returns integer value - myWidth.
	 */
	public int getWidth() {
		return this.myWidth;
	}
}
