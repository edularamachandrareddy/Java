/*
 * (Storing numbers in a linked list) write a program that lets the user
 * enter numbers from a graphical user interface and display them in a 
 * text area, as shown below. Use a linked list to store the numbers. Do
 * not store duplicate numbers. Add the buttons Sort, shuffle, and reverse
 * to sort, shuffle, and reverse the list.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
public class Exercise22_06 extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<String> list = new LinkedList<String>();
	private JButton sort = new JButton("Sort");
	private JButton shuffle = new JButton("Shuffle");
	private JButton reverse = new JButton("Reverse");
	private JButton clear = new JButton("Clear");
	private JPanel inputPanel = new JPanel();
	private JLabel inputLabel = new JLabel("Enter a number:");
	private JTextArea inputTextArea = new JTextArea();
	private JTextField inputTextField = new JTextField(20);
	private JPanel buttonPanel = new JPanel();
	
	private void display(){
		inputTextArea.setText(null);
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			inputTextArea.append(it.next() + " ");
		}
	}
	
	//Default Constructor
	public Exercise22_06(){

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(680, 480));
		panel.setLayout(new BorderLayout());
		
		//Add buttons
		buttonPanel.add(reverse);
		buttonPanel.add(sort);
		buttonPanel.add(shuffle);
		buttonPanel.add(clear);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		//add input area
		inputPanel.add(inputLabel);
		inputPanel.add(inputTextField);
		panel.add(inputPanel, BorderLayout.NORTH);
		
		//show inputs
		inputTextArea.setText(inputTextField.getText());
		panel.add(inputTextArea);
		panel.setVisible(true);
		add(panel);
		
		//Add listeners
		reverse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Reverse the list
				Collections.reverse(list);
				//Refresh the display
				display();
			}
		});
		sort.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Sort the list
				Collections.sort(list);
				//Refresh the display
				display();
			}
		});
		shuffle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Sort the list
				Collections.shuffle(list);
				//Refresh the display
				display();
			}
		});
		inputTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Process the input
				String[] words = (inputTextField.getText()).split("[ ]");
				for(String s: words){
					//check for uniqueness
					if(!list.contains(s)){
						list.add(s);
					}
				}
				inputTextField.setText(null);
				//Refresh the display;
				display();
			}
		});
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				list.clear();
				//Refresh the display
				display();
			}
		});
		
	}
	public static void main(String[] args) {
			Exercise22_06 applet = new Exercise22_06();
			JFrame frame = new JFrame();
		    //EXIT_ON_CLOSE == 3
		    frame.setDefaultCloseOperation(3);
		    frame.setTitle("Exercise22_06");
		    frame.add(applet, BorderLayout.CENTER);
		    applet.init();
		    applet.start();
		    frame.setSize(600, 400);
		    frame.setLocationRelativeTo(null); // Center the frame
		    frame.setVisible(true);
			}
}