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

		int i = 0;
		for (Element authorName : authorNames) {
			Review review = new Review();

			review.setReviewer(authorName.text());
			System.out.println(authorName.html() + "\n");
			if (authorName.html().equals("A Google User"))
				review.setGooglePlusId("ID Doesnot EXISTS");
			else
				review.setGooglePlusId(authorName.html().substring(34, 55));
			review.setReviewComment(getReviewBody(reviewsText.get(i)));
			review.setRating(getReviewRatings(reviewsRating.get(i++)));

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
