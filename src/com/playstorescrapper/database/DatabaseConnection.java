package com.playstorescrapper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	private static String dbURL = "jdbc:oracle:thin:@localhost:1521";
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String user = "SYSTEM";
	private static String pass = "Oracle4One";
	private Connection conn = null;

	public DatabaseConnection() {
	}

	public Connection getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbURL, user, pass);
		} catch (SQLException | ClassNotFoundException sqle) {
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

	// Doesn't works for Oracle DB
	public Boolean createDatabase(String appId) {
		Statement stmt = null;
		try {
			Connection con = this.getConnection();
			if (con != null)
				stmt = con.createStatement();
			String sql = "CREATE DATABASE " + appId + " ;";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.shutdown();
		}
		System.out.println("Database created successfully...");
		return false;
	}

	public Boolean createTables(String appName) {
		try {
			Connection conn = this.getConnection();
			Statement stmt = null;
			stmt = conn.createStatement();
			System.out.println(appName);

			String createSql = "CREATE TABLE "
					+ appName
					+ " ( name VARCHAR2(55), "
					+ " packageId VARCHAR2(55), "
					+ " category VARCHAR2(30), " 
					+ " description VARCHAR2(255), "
					+ " overAllRatings DECIMAL(2,1), "
					+ " numberOfDownloads VARCHAR2(60), "
					+ " numberOfRaters VARCHAR2(30)" 
					+ " )";
			stmt.executeUpdate(createSql);
			createSql = "CREATE TABLE  " + appName + "_screenshots"
					+ " (screeShotId INTEGER not NULL, "
					+ " screenShotUrl VARCHAR(255)" + " )";
			stmt.executeUpdate(createSql);
			createSql = "CREATE TABLE  " + appName + "_reviews"
					+ " (reviewId INTEGER not NULL, " + " ratings INTEGER, "
					+ " reviewComment VARCHAR(255), "
					+ " reviewer VARCHAR(25), " + " googlePlusId VARCHAR(30))";
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