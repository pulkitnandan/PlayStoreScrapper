package com.playstorescrapper.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.playstorescrapper.content.Application;
import com.playstorescrapper.content.Rating;

public class AppRatingDAO {

	public void ApplicationDao(Application app) throws SQLException {
		int applicationId = 0;
		DatabaseConnection dC = new DatabaseConnection();

		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		// statements allow to issue SQL queries to the database
		statement = dC.getConnection().createStatement();

		resultSet = statement.executeQuery("select id from application");

		if (resultSet.next())
			applicationId = resultSet.getInt("id") + 1;

		// preparedStatements can use variables and are more efficient
		preparedStatement = dC.getConnection().prepareStatement(
				"insert into  application values (?,?, ?, ?, ?, ? , ?, ?)");

		preparedStatement.setInt(1, applicationId);
		preparedStatement.setString(2, app.getName());
		preparedStatement.setString(3, app.getPackageId());
		preparedStatement.setString(4, app.getCategory());
		preparedStatement.setFloat(5, app.getOverAllRatings().floatValue());
		preparedStatement.setInt(6, app.getNumberOfDownloads());
		preparedStatement.setInt(7, app.getNumberOfRaters());
		preparedStatement.executeUpdate();

		preparedStatement.executeQuery();

		// Insert ScreenShot urls;
		int screenShotId = 0;
		statement = null;
		statement = dC.getConnection().createStatement();
		for (String screenShotUrl : app.getScreenShots()) {
			String insertSql = "insert into  appScreenShots values ("
					+ applicationId + "," + screenShotId + "," + screenShotUrl
					+ ")";
			statement.addBatch(insertSql);
			statement.executeBatch();
		}

	}

	private void createTable(String appName) throws SQLException {
		DatabaseConnection dC = new DatabaseConnection();
		Connection conn = dC.getConnection();
		Statement stmt = conn.createStatement();
		String createSql = "CREATE TABLE  " + appName
				+ " (reviewId INTEGER not NULL, "
				+ " ratingStar INTEGER not NULL, "
				+ " reviewComment VARCHAR(255), " + " reviewer VARCHAR(25), "
				+ " googlePlusId VARCHAR(30), " + " PRIMARY KEY ( id ))";
		stmt.executeUpdate(createSql);
	}

	public void RatingsDao(ArrayList<Rating> ratings, String appId)
			throws SQLException {
		DatabaseConnection dC = new DatabaseConnection();

		Statement statement = null;
		// statements allow to issue SQL queries to the database
		statement = dC.getConnection().createStatement();

		// Insert Ratings urls;
		statement = null;
		statement = dC.getConnection().createStatement();
		int ratingId = 0;
		for (Rating rating : ratings) {
			String insertSql = "insert into" + appId + " values (" + " "
					+ ratingId++ + " ," + rating.getRatingStar() + " ,"
					+ rating.getReviewComment() + " ," + rating.getReviewer()
					+ " ," + rating.getGooglePlusId() + " )";
			statement.addBatch(insertSql);
			statement.executeBatch();
		}

	}

	public AppRatingDAO() {
		// TODO Auto-generated constructor stub
	}

}
