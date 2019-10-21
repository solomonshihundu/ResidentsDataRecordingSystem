package com.iridium.RRS;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Database 
{
	
	/*
	 * server url: access point name,database IPadress, port number and database name
	 */
	public static final String URL = "jdbc:sqlserver://127.0.0.1:8000;Database = ResidentRecords";
	
	
	/*
	 * password to access the SQL server
	 */
	public static final String PASSWORD = "titanic100";
	
	
	/*
	 * SQL server user name 
	 */
	public static final String USER = "sa";
	
	
	/*
	 * string variable stores the currently being executed command
	 */
	private String command;
	
	
	/*
	 * S Buffer will store the data being retrieved from the database
	 */
	public static StringBuffer myOutput;
	
	/*
	 * 
	 */
	public static String createTable;
	
	/*
	 * String holds the update record command
	 */
	public String update_cmd;
	
	/*
	 * String holds the show all available records command
	 */
	public String showAll_cmd;
	
	/*
	 * String holds the search record command
	 */
	public String search_cmd;
	
/*
 * String variables corresponding to the input fields
 * temporarily stores the respective data input for recording 
 * into the database
 */
	public static String name;
	public static String id;
	public static String adress;
	public static String contact;
	public static String land_reg;
	
	/*
	 * flag to indicate the current operation
	 */
	private int currentState =0;
	
	
	/*
	 * constructor contains the main program execution
	 */
	public Database()
	{
		
		Connection conn = null;
		Statement stmt = null;
			
		myOutput = new StringBuffer();
		
		//assigns flags to the different states of operation
		funcStates();
		
		/*
		 * Method ensures the id and contact fields have integer values by
		 * attempting to parse the values to integers. 
		 * 
		 *  Assigns values from the text fields to temporary string variables
		 */
		validation();
		
		/*
		 * SQL queries to interact with the database accordingly 
		 */
		
		/*
		 * insert command prompts the server to add a new record to the database
		 * corresponding to values of the input text fields
		 */
		update_cmd = "insert into ResidentsRecord (ID,NAME,LAND_REGISTRATION,ADRESS,CONTACT) values " +
				"('"+id+"','"+name+"','"+land_reg+"','"+adress+"','"+contact+"')";
		
		
		/*
		 * prompts the server to retrieve all records in the specified table in the database
		 */
		showAll_cmd = "select*from ResidentsRecord";
		
		
		/*
		 * prompts the server to retrieve a single record specified by entered ID number
		 */
		search_cmd = "select*from ResidentsRecord where id = "+ Main.searchID;
		
			
		try
		{
			
			
			/*
			 * Attempt to make a connection to the database
			 */
			
			//initialization of the SQL driver 
			Driver myDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
			
			//driver is registered in the program to make it usable in our system
			DriverManager.registerDriver(myDriver);
			
			
			//attempt to connect to a database specified by URL parameter 
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			
			
			
			if(conn != null)
			{
				System.out.println("Connection to database was successful !");
			}
			else{
				System.out.println("Sorry, connection to database failed ! ");
			}
		
			//the SQL statement is initialized
			stmt = conn.createStatement();
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			/*
			 * Checks if the desired operation is to update the System with a new record 
			 * if true a new record is added to the database
			 */
			
			
			if(currentState == 0)
			{	
				command = update_cmd;
				int numRow = stmt.executeUpdate(command);

				System.out.print(numRow);
			}
			
			
			/*
			 * Checks if the desired operation is to search for a record
			 * Retrieves the desired data from the database
			 * appends this data to a S Buffer for display
			 */
			else if(currentState == 1)
			{
				command = search_cmd;
				ResultSet rs = stmt.executeQuery(command);
				
				while(rs.next())
				{
					Main.textArea.setText(null);
					myOutput.append("Name : "+rs.getString("NAME").toString()+"\nID number : "
							+rs.getString("ID").toString()+"\nLand Registration : "
							+rs.getString("LAND_REGISTRATION").toString()+"\nAdress : "
							+rs.getString("ADRESS").toString()+"\nConact : "+rs.getString("CONTACT").toString()+"\n\n");
				
				}
			
			}
			
			/*
			 * Checks if the desired operation is to retrieve all 
			 * the existing records in the database table
			 * 
			 * Appends this data to the S Buffer for display
			 */
			else if(currentState == 2)
			{
				
				command = showAll_cmd;
				ResultSet rs = stmt.executeQuery(command);
				
				while(rs.next())
				{
					myOutput.append("Name :                          "+rs.getString("NAME").toString()+"\nID number :                  "
				+rs.getString("ID").toString()+"\nLand Registration :     "+rs.getString("LAND_REGISTRATION").toString()
				+"\nAdress :                         "+rs.getString("ADRESS").toString()+"\nContact :                        "+rs.getString("CONTACT").toString()+"\n\n");
					
				}
			}
			
			/*
			 * connection and statement are closed to relive resources 
			 * as their task is complete
			 */
			conn.close();
			stmt.close();
		}
		catch(SQLException e)
		{
	//		JOptionPane.showMessageDialog(null, "System was uable to connect to server !");
			e.printStackTrace();
		}
		
	}
	public void validation()
	{
			if(currentState == 0)
			{
		try
		{
		int tempID = (Integer.parseInt(Main.idText.getText()));
		id = String.valueOf(tempID);
		
		int tempContact = (Integer.parseInt(Main.contactText.getText()));
		contact = String.valueOf(tempContact);
		
		}
		catch(NumberFormatException numEx)
		{
			JOptionPane.showMessageDialog(null, "Invalid Entry");
			
		}
		finally
		{
			name = Main.nameText1.getText()+" "+Main.nameText2.getText();
			adress = Main.adressText.getText();
			land_reg = Main.regText.getText();
		}
			}
	}
	public void funcStates()
	{
		if(Main.state.equals("update"))
		{
			currentState = 0;
		}
		else if(Main.state.equals("search"))
		{
			currentState =1;
		}
		else if(Main.state.equals("showAll"))
		{
			currentState = 2;
		}
		
	}

}
