package gui;

import exceptions.CellsAreNotNeighborsException;
import exceptions.OutOfBoardException;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import maze.Maze;

public class MazeFrame extends JFrame {

	/**
	 * Default value for width and height of the maze.
	 */
	static int DEFAULT_VALUE = 10;

	private Maze myMaze = null;
	
	private JMenuBar jJMenuBar = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanelMenu = null;
	private JButton jButton = null;
	private JTextField jtfWidth = null;
	private JTextField jtfHeight = null;
	private JLabel jLabelWidth = null;
	private JLabel jLabelHeight = null;
	private JLabel labelMessage = null;
	private MazePanel mazePanel = null;
	private JLabel jLabelSolution = null;
	private JRadioButton jrbSolution = null;
	private JRadioButton jrbStep = null;
	private JLabel jLabelStep = null;
	private ButtonGroup buttonGroupGenerate = null; // @jve:decl-index=0:

	private JScrollPane jScrollPane = null;

	private JPanel jPanel = null;

	/**
	 * This method initializes
	 * 
	 */
	public MazeFrame(Maze maze) {
		super();
		this.myMaze = maze;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		buttonGroupGenerate = new ButtonGroup();
		this.setSize(new Dimension(611, 267));
		this.setTitle("AiF Project Maze 2008");
		this.setContentPane(getJSplitPane());
		this.setJMenuBar(getJJMenuBar());

	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setPreferredSize(new Dimension(0, 24));
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setEnabled(true);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(80);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanelMenu == null) {
			jLabelStep = new JLabel();
			jLabelStep.setBounds(new Rectangle(513, 16, 80, 18));
			jLabelStep.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelStep.setText("step by step");
			jLabelStep.setVisible(false);
			jLabelSolution = new JLabel();
			jLabelSolution.setBounds(new Rectangle(434, 15, 58, 20));
			jLabelSolution.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelSolution.setText("solution");
			labelMessage = new JLabel();
			labelMessage.setText("Please press \"generate\" to start");
			labelMessage.setSize(new Dimension(539, 18));
			labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
			labelMessage.setLocation(new Point(31, 45));
			jLabelHeight = new JLabel();
			jLabelHeight.setBounds(new Rectangle(189, 10, 45, 23));
			jLabelHeight.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelHeight.setText("height:");
			jLabelWidth = new JLabel();
			jLabelWidth.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelWidth.setSize(new Dimension(41, 24));
			jLabelWidth.setLocation(new Point(113, 10));
			jLabelWidth.setText("width:");
			jPanelMenu = new JPanel();
			jPanelMenu.setLayout(null);
			jPanelMenu.add(getJButton(), null);
			jPanelMenu.add(getJtfWidth(), null);
			jPanelMenu.add(getJtfHeight(), null);
			jPanelMenu.add(jLabelWidth, null);
			jPanelMenu.add(jLabelHeight, null);
			jPanelMenu.add(labelMessage, null);
			jPanelMenu.add(jLabelSolution, null);
			jPanelMenu.add(getJrbSolution(), null);
			jPanelMenu.add(getJrbStep(), null);
			jPanelMenu.add(jLabelStep, null);
		}
		return jPanelMenu;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setLocation(new Point(12, 10));
			jButton.setText("generate");
			jButton.setSize(new Dimension(90, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					Maze greatMaze = createMaze(getIntFromJTextfield(jtfWidth),
							getIntFromJTextfield(jtfHeight));

					generateMaze(greatMaze);
					mazePanel = getMazePanel(greatMaze);
					showMazePanel();
				}
			});
		}
		return jButton;
	}

	private void showMazePanel() {
		jScrollPane.setViewportView(mazePanel);
	//	jSplitPane.setDividerLocation(80);
		setMessage("MazePanel shown, its dimensions:"+mazePanel.getWidth()+"x"+mazePanel.getHeight());
	}

	private Maze createMaze(int width, int height) {
		Maze maze = new Maze(width, height);
		setMessage("Maze of size: " + maze.getWidth() + "x" + maze.getHeight()
				+ " created!");
		return maze;
	}

	private void generateMaze(Maze maze) {
		try {
			maze.generateMaze();
			setMessage("Maze of size: " + maze.getWidth() + "x"
					+ maze.getHeight() + " generated!");
		} catch (CellsAreNotNeighborsException e) {
			setMessage("[Algorithm error] Generation tried to operate on non-neighboring cells!");
		} catch (OutOfBoardException e) {
			setMessage("[Algorithm error] Generation went out of board range!");
		}

		maze.printMaze();

	}

	/**
	 * Function gets integer value from JTextField. If value was not integer it
	 * returns default value defined in this class and sets JTextField 'text' to
	 * the same default value.
	 * 
	 * @param jTextField
	 *            JTextField from which gets value.
	 * @return Returns integer value obtained from 'text' field or default
	 *         value.
	 */
	private int getIntFromJTextfield(JTextField jTextField) {
		try {
			return Integer.parseInt(jTextField.getText());
		} catch (NumberFormatException e) {
			setIntValueToJTextfield(jTextField, DEFAULT_VALUE);
			return DEFAULT_VALUE;
		}
	}

	/**
	 * This function replace old string of JTextField's 'text' field with new
	 * string.
	 * 
	 * @param jTextField
	 *            JTextField which 'text' field is to be changed.
	 * @param value
	 *            New string which replaces old 'text'.
	 */
	private void setIntValueToJTextfield(JTextField jTextField, int value) {
		String tmp = Integer.toString(value);
		jTextField.setText(tmp);
	}

	/**
	 * This method initializes jtfWidth
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfWidth() {
		if (jtfWidth == null) {
			jtfWidth = new JTextField();
			jtfWidth.setLocation(new Point(160, 10));
			jtfWidth.setText("10");
			jtfWidth.setHorizontalAlignment(JTextField.CENTER);
			jtfWidth.setSize(new Dimension(26, 26));
		}
		return jtfWidth;
	}

	/**
	 * Sets string message to labelMessage label. It is used to present
	 * information to the user.
	 * 
	 * @param msg
	 *            String message to be set.
	 */
	private void setMessage(String msg) {
		labelMessage.setText(msg);
	}

	/**
	 * This method initializes jtfHeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfHeight() {
		if (jtfHeight == null) {
			jtfHeight = new JTextField();
			jtfHeight.setLocation(new Point(240, 10));
			jtfHeight.setHorizontalAlignment(JTextField.CENTER);
			jtfHeight.setText("10");
			jtfHeight.setSize(new Dimension(26, 26));
		}
		return jtfHeight;
	}

	/**
	 * This method initializes mazePanel
	 * 
	 * @return MazePanel
	 */
	private MazePanel getMazePanel(Maze maze) {
		if (mazePanel == null) {
			mazePanel = new MazePanel(maze);
			mazePanel.setLayout(new GridBagLayout());
		}
		return mazePanel;
	}

	/**
	 * This method initializes jrbSolution
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJrbSolution() {
		if (jrbSolution == null) {
			jrbSolution = new JRadioButton();
			jrbSolution.setLocation(new Point(416, 17));
			jrbSolution.setHorizontalAlignment(SwingConstants.CENTER);
			jrbSolution.setSize(new Dimension(16, 16));
			jrbSolution.setSelected(true);
			buttonGroupGenerate.add(jrbSolution);
		}
		return jrbSolution;
	}

	/**
	 * This method initializes jrbStep
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJrbStep() {
		if (jrbStep == null) {
			jrbStep = new JRadioButton();
			jrbStep.setPreferredSize(new Dimension(16, 16));
			jrbStep.setSize(new Dimension(16, 16));
			jrbStep.setHorizontalAlignment(SwingConstants.CENTER);
			jrbStep.setLocation(new Point(496, 18));
			jrbStep.setVisible(false);
			buttonGroupGenerate.add(jrbStep);
		}
		return jrbStep;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getMazePanel(this.myMaze));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel == null) {
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
