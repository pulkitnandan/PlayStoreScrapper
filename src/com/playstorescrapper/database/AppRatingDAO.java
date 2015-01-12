package com.playstorescrapper.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;

public class AppRatingDAO {

	private String appName = null;
	private Boolean status = false;
	public static int reviewId = 0;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void insertApplicationData(Application app) throws SQLException {
		DatabaseConnection dC = new DatabaseConnection();

		Statement statement = null;
		PreparedStatement preparedStatement = null;
		// statements allow to issue SQL queries to the database
		statement = dC.getConnection().createStatement();

		// preparedStatements can use variables and are more efficient
		preparedStatement = dC.getConnection().prepareStatement(
				"insert into " + this.getAppName()
						+ " values (?,?, ?, ?, ?, ?, ? )");

		preparedStatement.setString(1, app.getName());
		preparedStatement.setString(2, app.getPackageId());
		preparedStatement.setString(3, app.getCategory());
		preparedStatement.setString(4, app.getDescription());
		preparedStatement.setFloat(5, app.getOverAllRatings().floatValue());
		preparedStatement.setString(6, app.getNumberOfDownloads());
		preparedStatement.setString(7, app.getNumberOfRaters());
		preparedStatement.executeUpdate();

		// Insert ScreenShot urls;
		int screenShotId = 0;
		for (String screenShotUrl : app.getScreenShots()) {
			String insertSql = "insert into " + this.getAppName()
					+ "_screenshots values ( " + (screenShotId++) + " , ' "
					+ screenShotUrl + " ' )";
			statement.addBatch(insertSql);
		}
		statement.executeBatch();
		dC.shutdown();

	}

	public void insertReviewData(ArrayList<Review> reviews, String appName)
			throws SQLException {
		DatabaseConnection dC = new DatabaseConnection();
		PreparedStatement preparedStatement = null;
		preparedStatement = dC.getConnection().prepareStatement(
				"insert into " + this.getAppName() + "_reviews"
						+ " values (?,?, ?, ?, ?)");
		for (Review review : reviews) {

			System.out.println("\n\n" + review.getReviewer() + " \n"
					+ review.getReviewComment() + " \n" + review.getRating()
					+ "\n " + review.getGooglePlusId());

			preparedStatement.setInt(1, reviewId++);
			preparedStatement.setDouble(2, review.getRating());
			preparedStatement.setString(3, review.getReviewComment());
			preparedStatement.setString(4, review.getReviewer());
			preparedStatement.setString(5, review.getGooglePlusId());
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
		dC.shutdown();

	}

	public AppRatingDAO() {
		// TODO Auto-generated constructor stub
	}

	public AppRatingDAO(String name) {
		DatabaseConnection dC = new DatabaseConnection();
		this.setAppName(name.replace('.', '_').substring(4, 13));
		if (dC.createTables(this.getAppName())) {
			System.out.println("Tables created successfully");
			status = true;
		} else
			System.out.println("Failed to create tables");
	}

	public Boolean getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

}
