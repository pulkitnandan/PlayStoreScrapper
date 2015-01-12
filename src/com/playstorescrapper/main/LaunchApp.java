package com.playstorescrapper.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;
import com.playstorescrapper.database.AppRatingDAO;
import com.playstorescrapper.database.DatabaseConnection;
import com.playstorescrapper.parser.JsonParser;
import com.playstorescrapper.parser.ParseHtml;
import com.playstorescrapper.parser.ParseReviews;
import com.playstorescrapper.request.PostRequest;

/**
 * Take input as package name of app and launches scrapping.
 */

public class LaunchApp {

	/**
	 * @param args
	 */
	static String packageId = null;
	static String cookie = null;

	private Boolean scrapPlaystore() {

		System.out.println("\n PlayStore Scrapping Started");
		ParseHtml htmlParser = new ParseHtml();
		Application app = htmlParser.parseAppPage(packageId);

		if (app == null){
			return false;
		}

		System.out.println("\n Finished scrapping for html page");

		AppRatingDAO appRatingDao = new AppRatingDAO(packageId);

		if (!appRatingDao.getStatus())
			return false;

		try {
			appRatingDao.insertApplicationData(app);
			System.out.println("\n App Data inserted successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		PostRequest postRequest = new PostRequest(packageId, cookie);

		String jsonResponse = "0";
		int reviewPage = 0;

		while (true) {
			try {
				System.out.println(reviewPage);
				jsonResponse = postRequest.jsonResponse(reviewPage++);

				if (jsonResponse == "NOT FOUND" || jsonResponse.length() < 50)
					break;

				JsonParser jsonParse = new JsonParser(jsonResponse);

				String htmlReviews = jsonParse.parseJsonToHtml();

				ParseReviews reviewParser = new ParseReviews();

				ArrayList<Review> reviewList = reviewParser
						.parseReviews(htmlReviews);

				appRatingDao.insertReviewData(reviewList, packageId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		System.out.println("Enter the packageId");
		packageId = in.nextLine();
		System.out.println("Package is " + packageId);

		cookie =  "HSID=Apn_YLCrJfFAm4KZz; SSID=ANn4fPEYMY7_V3Iky; APISID=6YoUgfrjGjtuMXWS/AMKOagtRUqrlQt9ix; SAPISID=2w0GelRW6j30ZsVz/ARceO1ZqdfikXGgvG; SID=DQAAAAEBAABUxNvcKgExpqMvRgJ9cIemKzLkv3HzdCbPx6n-1a6bH_-Gcmwua3BBDt8WQzjC0TnYjodJqU3E5RnCYiaKRNSE1FOtfbXyhKZltplHgKwpXcbCzO_sUnUpMOqM9QP1JIEORvSuQbJHteg2z0nk1sQlieVZKUaCMqnjqmlcrGNUK0wDqbYHaIc_h8bRbRh2ToH4Atp61KhL3ozZhh8PuyTYsbJTofoOzEN7yGtezlw_TBwtksvy4uRYcqchaVNgB8SxYukutA94WvbyumBIyK-MVvPLnPLC8v4LSI3X1fMLFYaAGSN0nhu3RI10DVjkGI9n5r2ZcS6R7vyUytMO8fQ7vi0nHxao_ZDOxCQ8tlWlEA; PREF=ID=f5908e6d7dd1b2ad:U=d89ab24a3ffa851c:LD=en:TM=1420716861:LM=1420765562:GM=1:S=n6EXmQ7ueN-wd4Jq; NID=67=JfJK9m9griCg8LsgeLE2_mEksQrEBYwcOXTaeHk3YI61qcoAj67TxQ3MMmr1SesKP9QB9Y5I2tbWxiOwDRw7ymkNEjeT86iMp8cM894Vth-pWsHizPPrgCSfKhBhOajQcODWLSNCfr2azcrIk6a-lKjkcgVwQOp6GIKsLCoHRW5-1J_LmZG6d0in4MBYGcPivi8y0YTR4iCHNH1jI1DLlNo8CMkN6S4-oYvW; PLAY_ACTIVE_ACCOUNT=ICrt_XL61NBE_S0rhk8RpG0k65e0XwQVdDlvB6kxiQ8=pulkitnandan@gmail.com; GOOGLE_ABUSE_EXEMPTION=ID=42b26e96df18210c:TM=1421053025:C=c:IP=14.98.22.60-:S=APGng0tAIhB7oKaONoXqib-Z_2WimatuqQ; PLAY_PREFS=CgJJThDC-fHqrSkiI2NsOmRldGFpbHMuZG91YmxlX2ZldGNoX3NvY2lhbF9kYXRhIidjbDpkZXRhaWxzLmhpZGVfZG93bmxvYWRfY291bnRfaW5fdGl0bGUiD2NvbnRlbnRfcmF0aW5ncyITbmV3X21lcmNoYW50X3NpZ251cCIbbm9jYWNoZTphY3RpdmVfYXBwc19icm93c2VyIh1ub2NhY2hlOmNhcnRfc2hvcnRfbmFtZV9tdXNpYyIZbm9jYWNoZTpjaGFydHM6Ym9va19kZWFscyIfbm9jYWNoZTpjaGFydHM6bW92aWVfcHJlX29yZGVycyIbbm9jYWNoZTplbmFibGVfcGxheV9jb3VudHJ5IjNub2NhY2hlOmVuYWJsZV91bHlzc2VzX2NvbXBhdGlibGVfc3Vic2NyaXB0aW9uX2NvZGUiFW5vY2FjaGU6ZW5jcnlwdGVkX2FwayIjbm9jYWNoZTpsaWZldGltZV9hcHBzX2ZyZWVfYWNxdWlyZXIiHm5vY2FjaGU6bGlmZXRpbWVfYm9va3NfYnJvd3NlciIWbm9jYWNoZTptdXNpY19zdWJfbm9uZSIZbm9jYWNoZTpuZXdfYm9va3NfYnJvd3NlciIibm9jYWNoZTpuZXdfdXNlcl9jbHVzdGVyX21hZ2F6aW5lcyIebm9jYWNoZTpuZXdfdXNlcl9jbHVzdGVyX211c2ljIh5ub2NhY2hlOm5ld191c2VyX2NsdXN0ZXJfb2NlYW4iIG5vY2FjaGU6bmV3X3VzZXJfY2x1c3Rlcl95b3V0dWJlIhxub2NhY2hlOm5ld191c2VyX2V4cF9kYXlfb2xkIhpub2NhY2hlOm5ld191c2VyX21hZ2F6aW5lcyIWbm9jYWNoZTpuZXdfdXNlcl9tdXNpYyIWbm9jYWNoZTpuZXdfdXNlcl9vY2VhbiIYbm9jYWNoZTpuZXdfdXNlcl95b3V0dWJlIg5ub2NhY2hlOm5vX2ZvcCIqbm9jYWNoZTpub25fc3ViX25vbl9tdXNpY191c2VyX3dpdGhvdXRfZm9wIiFub2NhY2hlOm5vdF9uZXdfdXNlcl9hbmRyb2lkX2FwcHMiGG5vY2FjaGU6b2FrdHJlZV9jb250cm9sMiIhbm9jYWNoZTpwbGF0b19hYl9hbGxfdXNlcnNfMjVfYWIxIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjJfbm9ybWFsIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjNfbm9ybWFsIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjRfbm9ybWFsIilub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjVfY29udHJvbCIjbm9jYWNoZTpwcmVmZXJfYmxhX2Zvcl9wbGF5X2NvdW50cnkiHW5vY2FjaGU6cmVjZW50X2Jvb2tzX2FjcXVpcmVyIjJub2NhY2hlOnRhcmdldGVkX3Byb21vOm5vX25leHVzXzZfbXVzaWNfYWxsX2FjY2VzcyIWbm9jYWNoZTp1c2VyX2NoYWxsZW5nZSIMb2ZmbGluZV9hcHBzIg9vZmZsaW5lX2FydGlzdHMiDW9mZmxpbmVfbXVzaWMortC1460p:S:ANO1ljINReI_Iweu; _gat=1; _ga=GA1.3.1001652468.1420879126";//in.nextLine();
		
		in.close();

		LaunchApp scrap = new LaunchApp();

		if (scrap.scrapPlaystore())
			System.out.println("PlayStore Scrapped");
		else{
			System.out.println("Something Went Wrong");
			flushData(packageId);
		}
	}

	private static void flushData(String name) {
		
		DatabaseConnection dC = new DatabaseConnection();
		if (dC.dropTables(name.replace('.', '_').substring(4, 13)) ){
			System.out.println("Tables Dropped successfully");
		} else
			System.out.println("Failed to drop tables");
	}


}
