import board.Board;



public class Main {

	/**
	 * The greatest maze project ever!
	 * 
	 * @author �ukasz Barcikowski
	 * @author Pawe� Rendak
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		tests();
	}
	
	
	
	static private void tests(){
		
		Board b = new Board(8,4);
		b.writeToConsoleBlocks();
		
	}

}