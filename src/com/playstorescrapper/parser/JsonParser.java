package com.playstorescrapper.parser;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

public class JsonParser {

	private ArrayList<String> reviews;

	public JsonParser(ArrayList<String> reviews) {
		this.reviews = reviews;
	}

	public ArrayList<String> parseJsonToHtml() {

		ArrayList<String> parsedReviews = new ArrayList<String>();
		for (String review : reviews) {
			GsonBuilder builder = new GsonBuilder();
			String object = builder.create().fromJson(review, Object.class)
					.toString();
			String reviewCode = object.toString().substring(12,
					object.length() - 7);
			System.out.println("\n" + reviewCode);
			parsedReviews.add(reviewCode);
		}
		return parsedReviews;
	}

}
