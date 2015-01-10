package com.playstorescrapper.test;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.parser.ParseHtml;

public class Parser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ParseHtml pH = new ParseHtml();
		Application app = pH.parseAppPage("com.kiloo.subwaysurf");
		System.out.println(app.getPackageId() 
				+ " \n"
				+ app.getName() 
				+ " \n"
				+ app.getCategory()
				+ " \n"
				+ app.getDescription()
				+ " \n"
				+ app.getOverAllRatings()
				+ " \n"
				+ app.getNumberOfRaters()
				+ " \n"
				+ app.getNumberOfDownloads()
				+ " \n");
		
	for(String sUrl: app.getScreenShots())
			System.out.println(sUrl + "\n");
		
	}

}
