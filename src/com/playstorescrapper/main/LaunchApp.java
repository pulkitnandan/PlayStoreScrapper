package com.playstorescrapper.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;
import com.playstorescrapper.database.AppRatingDAO;
import com.playstorescrapper.database.DatabaseConnection;
import com.playstorescrapper.parser.JsonParser;
import com.playstorescrapper.parser.ParseHtml;
import com.playstorescrapper.parser.ParseReviews;
import com.playstorescrapper.request.PostRequest;

/**
 * Take input as package name of app and launches scrapping.
 */

public class LaunchApp {

	/**
	 * @param args
	 */
	static String packageId = null;
	static String cookie = null;

	private Boolean scrapPlaystore() {

		System.out.println("\n PlayStore Scrapping Started");
		ParseHtml htmlParser = new ParseHtml();
		Application app = htmlParser.parseAppPage(packageId);

		if (app == null){
			return false;
		}

		System.out.println("\n Finished scrapping for html page");

		AppRatingDAO appRatingDao = new AppRatingDAO(packageId);

		if (!appRatingDao.getStatus())
			return false;

		try {
			appRatingDao.insertApplicationData(app);
			System.out.println("\n App Data inserted successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		PostRequest postRequest = new PostRequest(packageId, cookie);

		String jsonResponse = "0";
		int reviewPage = 0;

		while (true) {
			try {
				System.out.println(reviewPage);
				jsonResponse = postRequest.jsonResponse(reviewPage++);

				if (jsonResponse == "NOT FOUND" || jsonResponse.length() < 50)
					break;

				JsonParser jsonParse = new JsonParser(jsonResponse);

				String htmlReviews = jsonParse.parseJsonToHtml();

				ParseReviews reviewParser = new ParseReviews();

				ArrayList<Review> reviewList = reviewParser
						.parseReviews(htmlReviews);

				appRatingDao.insertReviewData(reviewList, packageId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		System.out.println("Enter the packageId");
		packageId = in.nextLine();
		System.out.println("Package is " + packageId);

		in.close();

		LaunchApp scrap = new LaunchApp();

		if (scrap.scrapPlaystore())
			System.out.println("PlayStore Scrapped");
		else{
			System.out.println("Something Went Wrong");
			flushData(packageId);
		}
	}

	private static void flushData(String name) {
		
		DatabaseConnection dC = new DatabaseConnection();
		if (dC.dropTables(name.replace('.', '_').substring(4, 13)) ){
			System.out.println("Tables Dropped successfully");
		} else
			System.out.println("Failed to drop tables");
	}


}
