package com.playstorescrapper.parser;

import com.google.gson.GsonBuilder;

public class JsonParser {

	private String reviews;

	public JsonParser(String reviews) {
		this.reviews = reviews;
	}

	public String parseJsonToHtml() {

		String reviewCode;
		GsonBuilder builder = new GsonBuilder();
		String object = builder.create().fromJson(reviews, Object.class)
				.toString();
		try{
		reviewCode = object.toString().substring(12, object.length() - 7);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			return "NOT FOUND";
		}
		System.out.println("\n" + reviewCode);
		return reviewCode;
	}

}
