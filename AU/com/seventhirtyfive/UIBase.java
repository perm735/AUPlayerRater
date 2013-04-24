package com.seventhirtyfive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

public class UIBase {
	protected final int X_WINDOW_SIZE = 600;
	protected final int Y_WINDOW_SIZE = 580;
	protected final int Y_MENU_SIZE = 20;
	protected String mWindowName;
	protected Color mMenuColor;
	protected Color mMainColor;
	
	public UIBase(String windowName, Color menuColor, Color mainColor) {
		init(windowName, menuColor, mainColor);
	}
	
	public UIBase(String windowName) {
		init(windowName, new Color(154, 165, 127), new Color(248, 213, 131));
	}
	
	private void init(String windowName, Color menuColor, Color mainColor) {
		mWindowName = windowName;
		mMenuColor = menuColor;
		mMainColor = mainColor;
	}
	
	public void doIt(){
        JFrame frame = new JFrame(mWindowName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(mMenuColor);
        greenMenuBar.setPreferredSize(new Dimension(X_WINDOW_SIZE, Y_MENU_SIZE));

        //Create a yellow label to put in the content pane.
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(mMainColor);
        yellowLabel.setPreferredSize(new Dimension(X_WINDOW_SIZE, Y_WINDOW_SIZE));
        
        JTextField txtF = new JTextField();
        txtF.setText("Testing");
        txtF.setVisible(true);
        txtF.setBounds(new Rectangle(10,10,200,20));
        txtF.setBackground(mMainColor);
        txtF.setBorder(BorderFactory.createEmptyBorder());
        yellowLabel.add(txtF);

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

}
