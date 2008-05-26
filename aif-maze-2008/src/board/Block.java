package board;

import java.util.LinkedList;

public class Block {

	private int column;
	private int row;
	private int id;

	/* walls */
	boolean N, E, S, W;

	/* clock-wise: N:0, E:1, S:2, W:3 */
	LinkedList<Block> myNeighbors = new LinkedList<Block>();

	boolean wasVisited;

	public Block(int col_no, int row_no, int identificator) {

		column = col_no;
		row = row_no;
		id = identificator;

		N = E = S = W = true;
		wasVisited = false;
	}

	public boolean getWasVisited() {
		return this.wasVisited;
	}

	public void setWasVisited(boolean value) {
		this.wasVisited = value;
	}

	public int getId() {
		return this.id;
	}

	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void addNeighbor(int direction, Block neighbor) {
		if (direction == 0 || direction == 1 || direction == 2
				|| direction == 3) {
			myNeighbors.add(direction, neighbor);

		} else
			System.out.println("Error when using function addNeighbor(int "
					+ direction + ", Block " + neighbor.getId());

	}
}
