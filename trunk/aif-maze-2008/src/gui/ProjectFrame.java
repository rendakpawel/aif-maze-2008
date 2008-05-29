package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class ProjectFrame extends JFrame {

	private static final long serialVersionUID = -5204259716797205248L;
	private JMenuBar jJMenuBar = null;
	private JMenuItem jMenuItem = null;
	/**
	 * This method initializes 
	 * 
	 */
	public ProjectFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(320, 254));	
        this.setContentPane(getJMenuItem());
        this.setJMenuBar(getJJMenuBar());
        this.setTitle("AIF Maze 2008");
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuItem());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("Close");
			jMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.exit(0);
				}
			});
		}
		return jMenuItem;
	}

}  //  @jve:decl-index=0:visual-constraint="45,15"
