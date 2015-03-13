package FBCrawl;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.lang.Object;

import javax.swing.*;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import facebook4j.FacebookException;


    public class JButtonDemo2 {
    	JFrame jtfMainFrame;
    	JButton jbnButton1, jbnButton2, jbnButton3, jbnButton4;
    	JTextField jtfInput; 
    	JPanel jplPanel;
    	
    	public JButtonDemo2(final fbCrawl fbCrawl) {
        		
        	jtfMainFrame = new JFrame("Which Button Demo"); 
        	jtfMainFrame.setSize(300,300);
    		jbnButton1 = new JButton("Read IDs"); 
    		jbnButton2 = new JButton("Read events");
    		jbnButton3 = new JButton("search events");
    		jbnButton4 = new JButton("count events");
    		jtfInput = new JTextField(20);
    		jplPanel = new JPanel();
    		
    		
    		jbnButton1.setMnemonic(KeyEvent.VK_I);	//Set ShortCut Keys
    		jtfInput.setText("Amsterdam");
    		
    		UtilDateModel modelFrom = new UtilDateModel();
    		UtilDateModel modelTo = new UtilDateModel();
    		//model.setDate(20,04,2014);
    		// Need this...
    		Properties p = new Properties();
    		p.put("text.today", "Today");
    		p.put("text.month", "Month");
    		p.put("text.year", "Year");
    		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
    		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
    		// Don't know about the formatter, but there it is...
    		final JDatePickerImpl datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
    		final JDatePickerImpl datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
    		 
    	
    	     jbnButton1.addActionListener(new ActionListener() {
    	            public void actionPerformed(ActionEvent e) {
    	            	
    	            	String input = jtfInput.getText();
    	            	Date selectedDateFrom = (Date) datePickerFrom.getModel().getValue();
    	            	Date selectedDateTo = (Date) datePickerTo.getModel().getValue();
    	            	
    	            	long timeFrom = selectedDateFrom.getTime();
    	            	long timeTo = selectedDateTo.getTime();
    	            	
    	            	timeFrom = timeFrom/1000L;
    	            	timeTo = timeTo/1000L;
    	            
    	           
						try {
							System.out.println(timeTo);
							System.out.println(timeFrom);
							fbCrawl.readIDs(input, timeFrom, timeTo);
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
            
            jbnButton3.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String search_term = jtfInput.getText();
            		try {
						fbCrawl.search(search_term);
					}
					 catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            });
            
            jbnButton4.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String search_term = jtfInput.getText();
            		try {
						fbCrawl.countDailyEvents(search_term);
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
    		jplPanel.add(jbnButton3);
    		jplPanel.add(jbnButton4);
    		jplPanel.add(datePickerFrom);
    		jplPanel.add(datePickerTo);
        		
        	jtfMainFrame.getContentPane().add(jplPanel, BorderLayout.CENTER);

        	jtfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        	jtfMainFrame.pack();
        	jtfMainFrame.setVisible(true);
    }




    }