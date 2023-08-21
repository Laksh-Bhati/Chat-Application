package com.ChatApp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{

	JTextField text;
	JPanel panelTwo;
	static Box verticalBox = Box.createVerticalBox();
	static JFrame frameOne = new JFrame();
	static DataOutputStream dataOutput;
	Server()
	{
		//First Java Panel where the User Name is Visible
		frameOne.setLayout(null);
		JPanel panelOne = new JPanel();
		panelOne.setBackground(new Color(52,53,65));
		panelOne.setBounds(0, 0, 450, 40);
		frameOne.add(panelOne);
		panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.X_AXIS));
		panelOne.add(Box.createHorizontalGlue());
		
		JLabel yourName = new JLabel("Laksh");
//		yourName.setBounds(50, 0, 100, 18);
		yourName.setForeground(Color.WHITE);
		yourName.setFont(new Font("Monopause", Font.BOLD, 23));
		panelOne.add(yourName);
		panelOne.add(Box.createHorizontalGlue());
		
		//Second Panel where the Input texts are Overridden
		panelTwo = new JPanel();
		panelTwo.setBounds(0, 50, 450, 450);
		panelTwo.setBackground(Color.BLACK);
		frameOne.add(panelTwo);
		
		//TextField At the Bottom of the Java Frame
		text = new JTextField();
		text.setBounds(5, 515, 310, 40);
		text.setLayout(new BoxLayout(text, BoxLayout.X_AXIS));
		text.setForeground(Color.WHITE);
		text.setBackground(new Color(52,53,65));
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
		frameOne.add(text);
		
		//Send Button Visible Beside TextField
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(320, 515, 123, 40);
		sendButton.setBackground(new Color(52,53,65));
		sendButton.setForeground(Color.WHITE);
		sendButton.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
		sendButton.addActionListener(this);
		frameOne.add(sendButton);

		//Size and adjustments Done to Java Frame
		frameOne.setSize(465, 600);
		frameOne.setLocation(200, 30);	
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		frameOne.getContentPane().setBackground(Color.BLACK);
		
		frameOne.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		
	try {
		String messageOut = text.getText();
		
		JPanel messagePanel = formatLabel(messageOut);
		
		panelTwo.setLayout(new BorderLayout());
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(messagePanel, BorderLayout.LINE_END);
		rightPanel.setBackground(Color.BLACK);
		
		verticalBox.add(rightPanel);
		verticalBox.add(Box.createVerticalStrut(12));
		
		panelTwo.add(verticalBox, BorderLayout.PAGE_START);
		
		dataOutput.writeUTF(messageOut);
		
		text.setText("");
		
		//Making the text visible on the middle Panel & refreshing the middle panel
		frameOne.repaint();
		frameOne.invalidate();
		frameOne.validate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JPanel formatLabel(String messageOut)
	{
		JPanel textPanel = new JPanel();
		
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		JLabel messageOutput = new JLabel("<html><p style=\"width: 150px\">" + messageOut + "<html>");
		textPanel.add(messageOutput);
		messageOutput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		messageOutput.setBackground(new Color(52,53,65));
		messageOutput.setOpaque(true);
		messageOutput.setForeground(Color.WHITE);
		messageOutput.setBorder(new EmptyBorder(15, 15, 15, 35));
		textPanel.setBackground(Color.BLACK);
		
		//Setting Time Format below Text Message Box
		Calendar calendarClass = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
		
		JLabel textTime = new JLabel();
		textTime.setText(sdf.format(calendarClass.getTime()));
		textTime.setForeground(Color.WHITE);
		
		textPanel.add(textTime);
		
		return textPanel;
	}

	public static void main(String[] args) {
		
		new Server();
		System.gc();
		
		try {
			
			ServerSocket mainSocket = new ServerSocket(6018);
			while(true) {
				Socket socketOne = mainSocket.accept();
				DataInputStream dataInput = new DataInputStream(socketOne.getInputStream());
				dataOutput = new DataOutputStream(socketOne.getOutputStream());
				
				while(true) {
					String serverMessage = dataInput.readUTF();
					JPanel socketPanel = formatLabel(serverMessage);
					
					JPanel leftPanel = new JPanel(new BorderLayout());
					leftPanel.add(socketPanel, BorderLayout.LINE_START);
					leftPanel.setBackground(Color.BLACK);
					verticalBox.add(leftPanel);
					frameOne.validate();
				}
			}
			
		} catch(Exception e) {					
			e.printStackTrace();
		}
		
	}

}
