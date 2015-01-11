package com.playstorescrapper.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.playstorescrapper.bean.Application;

public class ParseHtml {

	private String url = "http://play.google.com/store/apps/details?id=";

	public ParseHtml() {
		System.out.println("\n Parsing app's page for details");
	}

	public Application parseAppPage(String appName) {
		Application application = new Application();
		Document doc = null;
		application.setPackageId(appName);
		
		try {
			doc = Jsoup.connect(url + appName).get();
			System.out.println("\n App Page Fetched Succesfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		Elements itemProps = doc.select("[itemprop]");
		Elements score = doc.getElementsByClass("score");
		Elements starsCount = doc.getElementsByClass("stars-count");
		Elements numberOfDownload = doc.select("div[itemprop=numDownloads]");
		Elements description = doc.select("div[itemprop=description]");
		
		for(Element item: numberOfDownload){
			application.setNumberOfDownloads(item.text());
			break;
		}	

		
		for(Element item: score){
			application.setOverAllRatings(Double.parseDouble(item.text()));
			break;
		}	
		
		String stars = null;
		for(Element item: starsCount){
			stars = item.text();
			break;
		}
		application.setNumberOfRaters(stars.substring(1, stars.length() - 1));


		application.setName(itemProps.eq(4).text());
		application.setCategory(itemProps.eq(8).text());
		
		for(Element item: description){
			application.setDescription(item.text());
			break;
		}
		
		Elements screenShots = doc.select("[src]");
		ArrayList<String> screenShotsUrl = new ArrayList<String>();

		for (Element item : screenShots)
			if (item.hasClass("screenshot"))
				screenShotsUrl.add(item.attr("abs:src"));

		application.setScreenShots(screenShotsUrl);

		return application;
	}

}
