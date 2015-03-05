package FBCrawl;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import facebook4j.FacebookException;


    public class JButtonDemo2 {
    	JFrame jtfMainFrame;
    	JButton jbnButton1, jbnButton2;
    	JTextField jtfInput; 
    	JPanel jplPanel;
    	
    	public JButtonDemo2(final fbCrawl fbCrawl) {
        		
        	jtfMainFrame = new JFrame("Which Button Demo"); 
        	jtfMainFrame.setSize(50, 50);
    		jbnButton1 = new JButton("Read IDs"); 
    		jbnButton2 = new JButton("Read events");
    		jtfInput = new JTextField(20);
    		jplPanel = new JPanel();
    		
    		jbnButton1.setMnemonic(KeyEvent.VK_I);	//Set ShortCut Keys
    		jtfInput.setText("Amsterdam");
    	     jbnButton1.addActionListener(new ActionListener() {
    	            public void actionPerformed(ActionEvent e) {
    	            	
    	            	String input = jtfInput.getText();
						try {
							fbCrawl.readIDs(input);
						} catch (FileNotFoundException
								| UnsupportedEncodingException
								| FacebookException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    	            	
    	            }

    	        });

        	jbnButton2.setMnemonic(KeyEvent.VK_I);
            jbnButton2.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String file_name = jtfInput.getText();
            		try {
						fbCrawl.crawlAndIndex(file_name);
					}
					 catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            });

    		jplPanel.setLayout(new FlowLayout());
    		jplPanel.add(jtfInput);
    		jplPanel.add(jbnButton1); 
    		jplPanel.add(jbnButton2);
        		
        	jtfMainFrame.getContentPane().add(jplPanel, BorderLayout.CENTER);

        	jtfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        	jtfMainFrame.pack();
        	jtfMainFrame.setVisible(true);
    }




    }