package com.playstorescrapper.bean;

public class Review {

	private int rating;
	private String reviewComment;
	private String reviewer;
	private String googlePlusId;

	public int getRating() {
		return rating;
	}

	public void setRating(int d) {
		this.rating = d;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getGooglePlusId() {
		return googlePlusId;
	}

	public void setGooglePlusId(String googlePlusId) {
		this.googlePlusId = googlePlusId;
	}

	public Review() {
		// TODO Auto-generated constructor stub
	}

}
