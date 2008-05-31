import gui.MazeGUIFrame;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	/**
	 * The greatest maze project ever!
	 * 
	 * @author £ukasz Barcikowski
	 * @author Pawe³ Rendak
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MazeGUIFrame window = new MazeGUIFrame();
		window.setVisible(true);
	}
}
