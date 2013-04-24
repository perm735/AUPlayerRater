package com.seventhirtyfive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

public class Debug735 {
	private Object mMutex = new Object();
	private final int X_WINDOW_SIZE = 1000;
	private final int Y_WINDOW_SIZE = 400;
	private final int Y_MENU_SIZE = 20;
	private Color mMenuColor = new Color(32,32,128);
	private Color mMainColor = new Color(128, 128, 128);
	
	private final int Y_STEP = 20;
	private final int Y_HEIGHT = Y_STEP;
	private int mYPos = 10;
	
	private String mWindowName;
	private JFrame mJFrame;
	private JLabel mJMain;
	private JMenuBar mJMenuBarMain;
	
	public Debug735() {
		mWindowName = "Debug";
	}
	
	public Debug735(Color mainColor, String windowName) {
		mWindowName = windowName;
		mMainColor = mainColor;
	}
	
	public final void init() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                doIt();
            }
        });
	}
	
	public void log( String exp ){
		synchronized(mMutex) {
			System.out.println("735: " + exp );
		}
	}
	public void error( String exp ){
		synchronized(mMutex) {
			System.out.println("735ERROR: " + exp );
		}
	}
	
	public void doIt(){
        mJFrame = new JFrame(mWindowName);
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.
        mJMenuBarMain = new JMenuBar();
        mJMenuBarMain.setOpaque(true);
        mJMenuBarMain.setBackground(mMenuColor);
        mJMenuBarMain.setPreferredSize(new Dimension(X_WINDOW_SIZE, Y_MENU_SIZE));
        
        //Create main component
        mJMain = new JLabel();
        mJMain.setOpaque(true);
        mJMain.setBackground(mMainColor);
        mJMain.setPreferredSize(new Dimension(X_WINDOW_SIZE*5, Y_WINDOW_SIZE*150));

        JScrollPane scrollPane = new JScrollPane(mJMain);
        scrollPane.setPreferredSize(new Dimension(X_WINDOW_SIZE+20, Y_WINDOW_SIZE+20));
        
        //Set the menu bar and add the label to the content pane.
        mJFrame.setJMenuBar(mJMenuBarMain);
        mJFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        mJFrame.setLocation(10, 10);

        //Display the window.
        mJFrame.pack();
        mJFrame.setVisible(true);
	}
	
	public void addString(String msg) {
        JTextField txtF = new JTextField();
        txtF.setText(msg);
        txtF.setVisible(true);
        txtF.setBounds(new Rectangle(10,mYPos,X_WINDOW_SIZE*5,Y_HEIGHT));
        txtF.setBackground(mMainColor);
        txtF.setBorder(BorderFactory.createEmptyBorder());
        txtF.setForeground(Color.WHITE);
        mJMain.add(txtF);
        
        mYPos+=Y_STEP;
	}
	
	public void addComponent( PlayerRow c ) {
	    c.setOrigin(new Point(10,mYPos));
	    mJFrame.add(c);
	    mYPos+=Y_STEP;
	}
	
	public void update() {
	    mJFrame.repaint();
	}
	
	public void clear() {
	    mJFrame.removeAll();
	    mJFrame.repaint();
	}
	
	public class PlayerRow extends JComponent {
	    /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final int X_SIZE = 800;
	    private final int Y_SIZE = 20;
	    private Point mOrigin;
	    private AUPlayer mPlayer;
	    
	    /**
	     * row layout
	     *   first name   last name     team name
	     * |-- 40 pix --|-- 80 pix --|-- 20 pix --|
	     */
	    private final int FIRSTNAME_WIDTH = 40;
	    private final int LASTNAME_WIDTH = FIRSTNAME_WIDTH*2;
	    private final int TEAM_WIDTH = 20;
	    
	    public PlayerRow( AUPlayer playerInfo ) {
	        mPlayer = playerInfo;
	    }
	    
	    public void setOrigin(Point p) {
	        mOrigin = p;
	    }
	    
	    public void init() {
	        setBounds(mOrigin.x, mOrigin.y, X_SIZE, Y_SIZE);
	        
	        // create player field
	        setLayout(new FlowLayout(FlowLayout.LEFT));
	        JLabel firstName = new JLabel(mPlayer.getFirstName(), SwingConstants.LEFT);
	        add(firstName);
            JLabel lastName = new JLabel(mPlayer.getLastName(), SwingConstants.LEFT);
            add(lastName);
            JLabel teamName = new JLabel(mPlayer.getTeam().toString(), SwingConstants.LEFT);
            add(teamName);
	    }
	}
}
