
public class Main {

	/**
	 * The greatest maze project ever!
	 * 
	 * @author £ukasz Barcikowski
	 * @author Pawe³ Rendak
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		Maze maze = new Maze(20,20);
		System.out.println("\n\nSOLVING MAZE...\n\n");
		maze.solveMaze();
	}
}
