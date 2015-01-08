package com.playstorescrapper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Saves Data to a database.
 */

public class DatabaseConnection {

	/**
	 * @param args
	 */
	private static Connection connection = null;
	public Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		DatabaseConnection.connection = connection;
	}

	public void createConnection(){
		  try {
	            // The newInstance() call is a work around for some
	            // broken Java implementations

	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception ex) {
	            // handle the error
	        }
		 
		  connection = null;

		  try {
		      connection =
		         DriverManager.getConnection("jdbc:mysql://localhost/test?" +
		                                     "user=monty&password=greatsqldb");

		      // Do something with the Connection

		
		  } catch (SQLException ex) {
		      // handle any errors
		      System.out.println("SQLException: " + ex.getMessage());
		      System.out.println("SQLState: " + ex.getSQLState());
		      System.out.println("VendorError: " + ex.getErrorCode());
		  }
	}
	
	public static void main(String[] args) {
		new DatabaseConnection().createConnection();
	}

}
