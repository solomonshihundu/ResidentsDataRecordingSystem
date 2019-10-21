 package com.iridium.RRS;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Main {

	//instance of our frame
	private JFrame frame;
	
	//sir name text field
	public static JTextField nameText1;
	
	//first name text field
	public static JTextField nameText2;
	
	//id number text field
	public static JTextField idText;
	
	//land registration number text field
	public static JTextField regText;
	
	//adress text field
	public static JTextField adressText;
	
	//search text field
	private JTextField searchText;
	
	//text area instance to output desired record
	public static JTextArea textArea;
	
	/*integer variable to hold search id number to compare against
	*existing id records
	*/
	public static int searchID = 0;
	/*
	 * String variable serves as flag of the command being executed
	 */
	public static String state = "showAll";
	
	//contact text field
	public static JTextField contactText;
	
	//button instance to show all records in the database
	private JButton showAllButton;
	
	/*main method calls the RSR constructor to start the application
	 * allows the frame to be visible and un-resizable
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run(){
				try {
					
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					window.frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * constructor calls the private initialize method 
	 */
	public Main() {
		initialize();
	}
    /*
     * private method sets values for all declared fields 
     *  
     */
	private void initialize() {
		
		/*
		 * A new frame is created and all necessary parameters set
		 */
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*
		 * JPanel to hold output textArea
		 */
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(343,25,417,290);
		/*
		displayPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, new Color(100,0,255), null),"",
				TitledBorder.TOP,TitledBorder.LEFT,new Font("Copperplate Gothic Bold",Font.BOLD+Font.ITALIC,9),SystemColor.controlShadow));
				*/
		/*
		 * JPanel layout set to absolute layout
		 */
		displayPanel.setLayout(null);
		
			//JPanel is added to the frame
		frame.getContentPane().add(displayPanel);
		
		/*
		 * Font options for different text displays
		 */
		
		//general font 
		Font myFont = new Font("Serif",Font.BOLD,12);
		
		//warnings and notices
		Font cautionFont = new Font("Times new Roman",+Font.ITALIC,10);
		
		//headings and titles 
		Font subtitleFont = new Font("Copperplate Gothic Bold",Font.PLAIN,15);
		
		
		JLabel nameLabel1 = new JLabel("Last Name");
		nameLabel1.setBounds(8, 65, 61, 14);
		nameLabel1.setFont(myFont);
		frame.getContentPane().add(nameLabel1);
		
		JLabel nameLabel2 = new JLabel("Other Name");
		nameLabel2.setBounds(8, 92, 77, 14);
		nameLabel2.setFont(myFont);
		frame.getContentPane().add(nameLabel2);
		
		JLabel idLabel = new JLabel("ID Number");
		idLabel.setBounds(8, 124, 61, 14);
		idLabel.setFont(myFont);
		frame.getContentPane().add(idLabel);
		
		JLabel landLabel = new JLabel("Land Registration");
		landLabel.setBounds(8, 152, 100, 14);
		landLabel.setFont(myFont);
		frame.getContentPane().add(landLabel);
		
		/*
		 * textFileds initializations
		 */
		nameText1 = new JTextField();
		nameText1.setBounds(118, 60, 138, 20);
		frame.getContentPane().add(nameText1);
		nameText1.setColumns(10);
		
		nameText2 = new JTextField();
		nameText2.setBounds(118, 89, 138, 20);
		frame.getContentPane().add(nameText2);
		nameText2.setColumns(10);
		
		idText = new JTextField();
		idText.setBounds(118, 121, 138, 20);
		frame.getContentPane().add(idText);
		idText.setColumns(10);
		
		regText = new JTextField();
		regText.setBounds(118, 152, 138, 20);
		frame.getContentPane().add(regText);
		regText.setColumns(10);
		
		JLabel adressLabel = new JLabel("Adress");
		adressLabel.setBounds(8, 188, 48, 14);
		adressLabel.setFont(myFont);
		frame.getContentPane().add(adressLabel);
		
		adressText = new JTextField();
		adressText.setBounds(118, 183, 138, 20);
		frame.getContentPane().add(adressText);
		adressText.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(20, 30, 390, 275);
		
		/*
		 * scrollpane added to display panel enables viewing of all records
		 * output textArea will be added to this field
		 */
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(0,0,415,302);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setVisible(true);
		displayPanel.add(scroll);
		
		
		JLabel subtitle = new JLabel("Persomal Information");
		subtitle.setBounds(21, 11, 227, 14);
		subtitle.setFont(subtitleFont);
		frame.getContentPane().add(subtitle);
		
		/*
		 * save button prompts the system to access the serve and attempts to create a new record 
		 * in the database
		 * 
		 */
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(8, 327, 89, 23);
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				state = "update";
				
				if(!nameText1.getText().equals("")&&!nameText2.getText().equals("")&&!idText.getText().equals("")
						&&!adressText.getText().equals("")&&!regText.getText().equals("")&&!contactText.getText().equals(""))
				{
					//a new record is created with the values of the text fields
					new Database();
					JOptionPane.showMessageDialog(null, "Entry saved successfully");
					
				}
				else
				{
					
					JOptionPane.showMessageDialog(null, "Please ensure all sections are filled !");	

				
				}
				nameText1.setText(null);
				nameText2.setText(null);
				adressText.setText(null);
				regText.setText(null);
				idText.setText(null);
				contactText.setText(null);
				
			}
		});
		
		//save button is added to frame
		frame.getContentPane().add(saveButton);
		
		/*
		 * reset button set all fields to null
		 */
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textArea.setText(null);		
				
				nameText1.setText(null);
				nameText2.setText(null);
				idText.setText(null);
				adressText.setText(null);
				contactText.setText(null);
				regText.setText(null);
				searchText.setText(null);
				
			}

		});
		resetButton.setBounds(133, 327, 89, 23);
		frame.getContentPane().add(resetButton);
		
		/*
		 * search button to prompt system to retrieve desired record by specifying id number 
		 */
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) 
			{

				state = "search";
				
				try
				{
					//entered id is parsed to integer to facilitate data type compatibility
				searchID = Integer.parseInt(searchText.getText().toString());
				
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null, "  Invalid ID format !");
					searchText.setText(null);
				}
				
				new Database();
				
				if(Database.myOutput.toString().equals(""))
				{
					JOptionPane.showMessageDialog(null, "No such existing record !");
				}
				else
				{
				textArea.setText(Database.myOutput.toString());
				}
			}

		});
		searchButton.setBounds(537, 327, 89, 23);
		frame.getContentPane().add(searchButton);
		
		JLabel noticeLabel = new JLabel("All  fields  above  must  be  filled  to  proceed !");
		noticeLabel.setBounds(8, 288, 203, 14);
		noticeLabel.setFont(cautionFont);
		noticeLabel.setForeground(Color.RED);
		frame.getContentPane().add(noticeLabel);
		/*
		 * text field accepts an integer number to be used as unique identifer 
		 *in retrieving desired record 
		 */
		searchText = new JTextField();
		searchText.setBounds(445, 328, 86, 20);
		frame.getContentPane().add(searchText);
		searchText.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(8, 313, 310, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel = new JLabel("search by ID ");
		lblNewLabel.setFont(cautionFont);
		lblNewLabel.setBounds(374, 331, 61, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel contactLabel = new JLabel("Contact");
		contactLabel.setFont(myFont);
		contactLabel.setBounds(8, 220, 46, 14);
		frame.getContentPane().add(contactLabel);
		/*
		 * Accepts an integer number to allow entry of contact details
		 */
		contactText = new JTextField();
		contactText.setBounds(118, 214, 138, 20);
		frame.getContentPane().add(contactText);
		contactText.setColumns(10);
		
		/*
		 * Button when pressed accesses the server database and returns all
		 * saved records in the database table ResidentsRecords
		 */
		showAllButton = new JButton("show all records");
		showAllButton.setBounds(648, 327, 109, 23);
		showAllButton.setFont(cautionFont);
		showAllButton.setForeground(Color.black);
		showAllButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				state = "showAll";
				
				new Database();
				textArea.setText(Database.myOutput.toString());
			
			}
		});
		frame.getContentPane().add(showAllButton);
		
	}
}
