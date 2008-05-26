package board;

import java.util.LinkedList;

public class Block {

	/* walls */
	boolean N, E, S, W;

	/* clock-wise: N:0, E:1, S:2, W:3 */
	LinkedList<Block> myNeighbors = new LinkedList<Block>();

	boolean wasVisited;

	public Block() {
		N = E = S = W = true;
		wasVisited = false;
	}

	public boolean getWasVisited() {
		return this.wasVisited;
	}

	public void setWasVisited(boolean value) {
		this.wasVisited = value;
	}
	
	public void addNeighbor(int direction, Block neighbor) {
		if (direction == 0 || direction == 1 || direction == 2 	|| direction == 3)
			myNeighbors.add(direction, neighbor);
	}
}
