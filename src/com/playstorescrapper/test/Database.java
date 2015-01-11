package com.playstorescrapper.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;
import com.playstorescrapper.database.AppRatingDAO;

public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Application testApp = new Application();
		testApp.setCategory("testCategory");
		testApp.setDescription("Lorem Ipsum Vodum Codum Ndem");
		System.out.println();
		testApp.setName("cdkh.cwejh.cd".replace('.', '_').substring(4, 13));
		testApp.setNumberOfDownloads("56628");
		testApp.setNumberOfRaters("87,866");
		testApp.setOverAllRatings(5.4);
		testApp.setPackageId("com.playstore.packageid");
		testApp.setScreenShots(createTestScreenShots());

		ArrayList<Review> testReviews = createTestRatings();

		AppRatingDAO testDao = new AppRatingDAO(testApp.getName());
		System.out.println("Tables on FLY");

		try {
			testDao.insertApplicationData(testApp);
			System.out.println("App Data Insertion Success \n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			testDao.insertReviewData(testReviews, testApp.getName());
			System.out.println("Reviews Data Insertion Success \n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static ArrayList<Review> createTestRatings() {
		ArrayList<Review> reviews = new ArrayList<Review>();
		for (int i = 0; i < 10; i++) {
			Review review = new Review();
			review.setReviewer("TestName" + i);
			review.setRating(i);
			review.setReviewComment("App is Appy" + i);
			review.setGooglePlusId("1983181389" + i);
			reviews.add(review);
		}

		return reviews;
	}

	private static ArrayList<String> createTestScreenShots() {
		String testImageUrl = "http://www.image.com";
		ArrayList<String> screenShotList = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
			screenShotList.add(testImageUrl + i);

		return screenShotList;
	}

}
