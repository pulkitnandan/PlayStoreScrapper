package com.playstorescrapper.parser;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.playstorescrapper.bean.Review;

public class ParseReviews {

	public ParseReviews() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Review> parseReviews(String reviewList) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		Document doc = null;
		doc = Jsoup.parseBodyFragment(reviewList);
		Elements authorNames = doc.getElementsByClass("author-name");
		Elements reviewsText = doc.getElementsByClass("review-body");
		Elements reviewsRating = doc.getElementsByClass("tiny-star");

		int k = 0;
		System.out.println(reviewsText.size());
		for (int i = 0; i < authorNames.size() ; i++) {
			 Element authorName  =  authorNames.get(i);
			Review review = new Review();

			review.setReviewer(authorName.text());
			int authorHtmlLength = authorName.html().length();
			int authorNameLength = authorName.text().length();
			
			review.setReviewComment(getReviewBody(reviewsText.get(k)));
			review.setRating(getReviewRatings(reviewsRating.get(k)));

			if (authorName.text().length() == 13 && authorName.text().equals("A Google User")){
				review.setReviewer("A Google User");
				review.setGooglePlusId("ID Doesnot EXISTS1");
				k++;
				System.out.println("app name");
			} else if(authorName.text().length() == 0)
			{
					continue;
			}
			else{
				review.setGooglePlusId(authorName.html().substring(34, authorHtmlLength - authorNameLength - 6));
				k++;
			}
		
				System.out.println("\n" + review.getReviewer() + " "
						+ review.getReviewComment() + " " + review.getRating()
						+ " " + review.getGooglePlusId());

				reviews.add(review);
		}

		return reviews;
	}

	private int getReviewRatings(Element element) {
		String rating = element.attr("aria-label");
		return Integer.parseInt(rating.substring(7, 8));
	}

	private String getReviewBody(Element element) {
		String reviewBody = element.text();
		return reviewBody.substring(0, reviewBody.length() - 12);
	}

}
