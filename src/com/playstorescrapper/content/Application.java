package com.playstorescrapper.content;

import java.util.ArrayList;

public class Application {

	private String name;
	private String packageId;
	private String category;
	private ArrayList<String> screenShots;
	private Double overAllRatings;
	private int numberOfDownloads;
	private int numberOfRaters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getScreenShots() {
		return screenShots;
	}

	public void setScreenShots(ArrayList<String> screenShots) {
		this.screenShots = screenShots;
	}

	public Double getOverAllRatings() {
		return overAllRatings;
	}

	public void setOverAllRatings(Double overAllRatings) {
		this.overAllRatings = overAllRatings;
	}

	public int getNumberOfDownloads() {
		return numberOfDownloads;
	}

	public void setNumberOfDownloads(int numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}

	public int getNumberOfRaters() {
		return numberOfRaters;
	}

	public void setNumberOfRaters(int numberOfRaters) {
		this.numberOfRaters = numberOfRaters;
	}

	public Application() {

	}

}
