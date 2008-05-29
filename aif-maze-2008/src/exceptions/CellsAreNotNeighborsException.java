package exceptions;

/**
 * Exception for non-neighboring cells.
 * Thrown while comparing cells of a board
 * that are not strict neighbors.
 * (i.e. north,east,south,west)
 * @author £ukasz Barcikowski
 */
public class CellsAreNotNeighborsException extends Throwable {

	private static final long serialVersionUID = -7929442365930066772L;

}
