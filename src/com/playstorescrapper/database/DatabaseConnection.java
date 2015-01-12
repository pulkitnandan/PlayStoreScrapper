package com.playstorescrapper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	private static String dbURL = "jdbc:mysql://localhost:3306/playstore";
	private static String user = "root";
	private static String pass = "";
	private Connection conn = null;

	public DatabaseConnection() {
	}

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(dbURL, user, pass);
		} catch (SQLException | ClassNotFoundException sqle) {
		  sqle.printStackTrace();
		}
		return this.conn;
	}

	public void shutdown() {
		try {

			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException sqlExcept) {

		}

	}

	public Boolean createTables(String appName) {
		try {
			Connection conn = this.getConnection();
			Statement stmt = null;
			stmt = conn.createStatement();
			System.out.println(appName);

			String createSql = "CREATE TABLE " + appName
					+ " ( name VARCHAR(55), " + " packageId VARCHAR(55), "
					+ " category VARCHAR(30), "
					+ " description VARCHAR(6553), "
					+ " overAllRatings DECIMAL(2,1), "
					+ " numberOfDownloads VARCHAR(60), "
					+ " numberOfRaters VARCHAR(30)" + " )";
			stmt.executeUpdate(createSql);
			createSql = "CREATE TABLE  " + appName + "_screenshots"
					+ " (screeShotId INTEGER not NULL, "
					+ " screenShotUrl VARCHAR(6553)" + " )";
			stmt.executeUpdate(createSql);
			createSql = "CREATE TABLE  " + appName + "_reviews"
					+ " (reviewId INTEGER not NULL, " + " ratings INTEGER, "
					+ " reviewComment VARCHAR(6553), "
					+ " reviewer VARCHAR(50), " + " googlePlusId VARCHAR(50))";
			stmt.executeUpdate(createSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.shutdown();
		}
		return true;
	}
	
	public Boolean dropTables(String appName) {
		try {
			Connection conn = this.getConnection();
			Statement stmt = null;
			stmt = conn.createStatement();
			System.out.println(appName);

			String createSql = "DROP TABLE " 
					+ appName;;
			stmt.executeUpdate(createSql);
			createSql = "DROP TABLE  " + appName + "_screenshots";
			stmt.executeUpdate(createSql);
			createSql = "DROP TABLE  " + appName + "_reviews";;
			stmt.executeUpdate(createSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.shutdown();
		}
		return true;
	}


}