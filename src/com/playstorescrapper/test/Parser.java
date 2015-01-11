package com.playstorescrapper.test;

import java.util.ArrayList;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;
import com.playstorescrapper.parser.ParseHtml;
import com.playstorescrapper.parser.ParseReviews;

public class Parser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		  ParseHtml pH = new ParseHtml();
		  
		  Application app = pH.parseAppPage("com.enparadigm.participantconnect");
		  
		  System.out.println(app.getPackageId() + " \n" + app.getName() + " \n"
		  + app.getCategory() + " \n" + app.getDescription() + " \n" +
		  app.getOverAllRatings() + " \n" + app.getNumberOfRaters() + " \n" +
		  app.getNumberOfDownloads() + " \n");
		  
		  for (String sUrl : app.getScreenShots()) System.out.println(sUrl +
		  "\n");
		 
		ParseReviews pR = new ParseReviews();
		Request req = new Request();
		req.testRequests();

		ArrayList<Review> reviews = pR.parseReviews(req.htmlReviews);

		for (Review review : reviews) {
			System.out.println("\n" + review.getReviewer() + " "
					+ review.getReviewComment() + " " + review.getRating()
					+ " " + review.getGooglePlusId());
		}

	}

}
