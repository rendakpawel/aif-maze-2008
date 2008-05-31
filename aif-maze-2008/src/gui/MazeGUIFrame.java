package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	static int BIGGEST_VALUE = 257;

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
		this.setSize(new Dimension(585, 483));
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
			jLabelHeight.setBounds(new Rectangle(141, 10, 70, 20));
			jLabelHeight.setText("Height:");
			jLabelHeight.setHorizontalAlignment(SwingConstants.LEFT);
			jLabelWidth = new JLabel();
			jLabelWidth.setBounds(new Rectangle(10, 10, 70, 20));
			jLabelWidth.setText("Width:");
			jLabelWidth.setHorizontalAlignment(SwingConstants.LEFT);
			labelMessage = new JLabel();
			labelMessage.setText("Please press \"generate\" to start");
			labelMessage.setSize(new Dimension(286, 20));
			labelMessage.setLocation(new Point(284, 10));
			labelMessage.setHorizontalAlignment(SwingConstants.RIGHT);
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
			jbGenerate.setBounds(new Rectangle(10, 420, 277, 25));
			jbGenerate.setHorizontalAlignment(SwingConstants.CENTER);
			jbGenerate.setText("Generate");
			jbGenerate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(mazePanel!=null){
						jScrollPane.remove(mazePanel);
						mazePanel = null;
						jScrollPane.repaint();
						jScrollPane.invalidate();
					}				
					jbSolve.setEnabled(true);
					jScrollPane.setViewportView(getMazePanel());
					jScrollPane.repaint();
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
			mazePanel.setBounds(new Rectangle(0, 0, mazePanel.getWidth(),
					mazePanel.getHeight()));
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
			jtfWidth.setLocation(new Point(80, 10));
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
			jtfHeight.setLocation(new Point(210, 10));
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
			jScrollPane.setBounds(new Rectangle(10, 35, 560, 380));
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
			jbSolve.setBounds(new Rectangle(293, 420, 277, 25));
			jbSolve.setEnabled(false);
			jbSolve.setText("Solve");
			jbSolve.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mazePanel.drawSolveMaze();
					jScrollPane.repaint();
					setMessage("Maze solved!");
				}
			});
		}
		return jbSolve;
	}

}  //  @jve:decl-index=0:visual-constraint="157,0"
