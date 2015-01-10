package com.playstorescrapper.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;

public class ParseHtml {
	
	private String url = "http://play.google.com/store/apps/details?id=";

	public ParseHtml() {
		// TODO Auto-generated constructor stub
	}

	public Application parseAppPage(String appName){
		Application application = new Application();
		Document doc = null;
		application.setPackageId(appName);
		try {
			doc = Jsoup.connect(url + appName).get();
			System.out.println("Successfully Parsed URI");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Elements itemProps = doc.select("[itemprop]");
		
		application.setName(itemProps.eq(4).text());
		application.setCategory(itemProps.eq(8).text());
		application.setDescription(itemProps.eq(27).text());
		
		Double overallRatings = Double.parseDouble(itemProps.eq(28).text().substring(0, 2)); 
		application.setOverAllRatings(overallRatings);
		
		int ratersStringLength = itemProps.eq(28).text().length();
		String numberOfRaters = itemProps.eq(28).text().substring(4, ratersStringLength - 6);
		application.setNumberOfRaters(numberOfRaters);
		
		application.setNumberOfDownloads(itemProps.eq(33).text());
		
		Elements screenShots = doc.select("[src]");
		ArrayList<String> screenShotsUrl = new ArrayList<String>();
		
		for(Element item : screenShots)
			if(item.hasClass("screenshot"))
				screenShotsUrl.add(item.attr("abs:src"));
		
		application.setScreenShots(screenShotsUrl);
		
		return application;
	}
	
	public ArrayList<Review> parseReviews(){
		
		return null;
		
	}
	
}
