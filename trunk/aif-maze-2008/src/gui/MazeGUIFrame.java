package gui;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.JScrollPane;

/**
 * Frame which provides setting basic parameters for MazePanel
 * and displays it.
 * 
 * @author Pawe³ Rendak
 * 
 */
public class MazeGUIFrame extends JFrame {
	
	final static long serialVersionUID = -432234234234L;
	
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="10,55"
	private JButton jbGenerate = null;
	private MazePanel mazePanel = null;
	private JLabel labelMessage = null;
	private JLabel jLabelWidth = null;
	private JTextField jtfWidth = null;
	private JLabel jLabelHeight = null;
	private JTextField jtfHeight = null;
	private JScrollPane jScrollPane = null;
	private JButton jbSolve = null;

	/**
	 * Smallest value for width and length of the maze.
	 */
	static int SMALLEST_VALUE = 2;

	/**
	 * Biggest value for width and length of the maze. For very big numbers
	 * computation can be very long.
	 */
	static int BIGGEST_VALUE = 101;

	/**
	 * Default value for width and height of the maze.
	 */
	static int DEFAULT_VALUE = 30;

	/**
	 * This method initializes
	 * 
	 */
	public MazeGUIFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(486, 430));
		this.setMaximumSize(new Dimension(451, 390));
		this.setMinimumSize(new Dimension(451, 390));
		this.setResizable(false);
		this.setTitle("AiF 2008 Maze Barcikowski & Rendak");
		this.setLocation(new Point(100, 50));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setContentPane(getJPanel());
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabelHeight = new JLabel();
			jLabelHeight.setBounds(new Rectangle(12, 72, 42, 21));
			jLabelHeight.setText("height:");
			jLabelHeight.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelWidth = new JLabel();
			jLabelWidth.setBounds(new Rectangle(11, 45, 43, 26));
			jLabelWidth.setText("width:");
			jLabelWidth.setHorizontalAlignment(SwingConstants.CENTER);
			labelMessage = new JLabel();
			labelMessage.setText("Please press \"generate\" to start");
			labelMessage.setSize(new Dimension(388, 25));
			labelMessage.setLocation(new Point(25, 1));
			labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setSize(new Dimension(481, 366));
			jPanel.add(getJbGenerate(), null);
			jPanel.add(labelMessage, null);
			jPanel.add(jLabelWidth, null);
			jPanel.add(getJtfWidth(), null);
			jPanel.add(jLabelHeight, null);
			jPanel.add(getJtfHeight(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getJbSolve(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jbGenerate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbGenerate() {
		if (jbGenerate == null) {
			jbGenerate = new JButton();
			jbGenerate.setBounds(new Rectangle(12, 104, 91, 35));
			jbGenerate.setHorizontalAlignment(SwingConstants.LEADING);
			jbGenerate.setText("generate");
			jbGenerate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(mazePanel!=null){
						jScrollPane.remove(mazePanel);
						mazePanel = null;
						jScrollPane.repaint();
					}				
					jbSolve.setEnabled(true);
					jScrollPane.setViewportView(getMazePanel());
					setMessage("Maze generated!");
				}
			});
		}
		return jbGenerate;
	}

	/**
	 * This method initializes mazePanel
	 * 
	 * @return MazePanel
	 */
	private MazePanel getMazePanel() {
		if (mazePanel == null) {
			mazePanel = new MazePanel(getIntFromJTextfield(jtfWidth),
					getIntFromJTextfield(jtfHeight));
			mazePanel.generateMaze();
			mazePanel.drawGeneratedMaze();
			mazePanel.setVisible(true);
			mazePanel.setBounds(new Rectangle(125, 25, mazePanel.getWidth(),
					mazePanel.getHeight()));
			// mazePanel.repaint();
		}
		return mazePanel;
	}

	/**
	 * This method initializes jtfWidth
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfWidth() {
		if (jtfWidth == null) {
			jtfWidth = new JTextField();
			jtfWidth.setHorizontalAlignment(JTextField.CENTER);
			jtfWidth.setLocation(new Point(58, 48));
			jtfWidth.setSize(new Dimension(35, 20));
			jtfWidth.setText(Integer.toString(DEFAULT_VALUE));
		}
		return jtfWidth;
	}

	/**
	 * This method initializes jtfHeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfHeight() {
		if (jtfHeight == null) {
			jtfHeight = new JTextField();
			jtfHeight.setHorizontalAlignment(JTextField.CENTER);
			jtfHeight.setLocation(new Point(58, 71));
			jtfHeight.setSize(new Dimension(35, 20));
			jtfHeight.setText(Integer.toString(DEFAULT_VALUE));
		}
		return jtfHeight;
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
	 * Function gets integer value from JTextField. If value was not integer it
	 * returns default value defined in this class and sets JTextField 'text' to
	 * the same default value. Unless its value is in SMALLEST - BIGGEST value
	 * bounds then return (and set text) default value.
	 * 
	 * @param jTextField
	 *            JTextField from which gets value.
	 * @return Returns integer value obtained from 'text' field or default
	 *         value.
	 */
	private int getIntFromJTextfield(JTextField jTextField) {
		try {
			int tmp = Integer.parseInt(jTextField.getText());

			if ((SMALLEST_VALUE < tmp) && (tmp < BIGGEST_VALUE)) {
				return tmp;
			} else {
				setIntValueToJTextfield(jTextField, DEFAULT_VALUE);
				return DEFAULT_VALUE;
			}
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(141, 65, 324, 325));
			jScrollPane.setMinimumSize(new Dimension(324, 325));
			jScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jbSolve
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbSolve() {
		if (jbSolve == null) {
			jbSolve = new JButton();
			jbSolve.setBounds(new Rectangle(13, 164, 91, 35));
			jbSolve.setEnabled(false);
			jbSolve.setText("solve");
			jbSolve.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mazePanel.drawSolveMaze();
					setMessage("Maze solved!");
				}
			});
		}
		return jbSolve;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
