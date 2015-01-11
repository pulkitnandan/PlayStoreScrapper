package com.playstorescrapper.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.playstorescrapper.bean.Application;
import com.playstorescrapper.bean.Review;
import com.playstorescrapper.database.AppRatingDAO;
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

		if (app == null)
			return false;

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

		System.out.println("Enter your cookie");
		cookie =  "PREF=ID=c2d3d0832d82dafb:U=4acd42280a5cccec:FF=0:LD=en:TM=1418046655:LM=1419439613:GM=1:S=-_Oez1RydpcBW2uy; enabledapps.uploader=0; HSID=AyIWzrvlSgDwrYyQP; SSID=AkPgm-9ojbwqL_oFm; APISID=JOet9s2JI8p8SY8T/Ahgvr1qH-p6tJoMsN; SAPISID=VvwHvn568QMlgUeo/AngpI00wUrpN7TPe5; SID=DQAAAAIBAABwqMfbBx29P2VFRMMVhiT8wdmYqhh0f5HRfeMuz56li1WhdMf5Lb5v-r9ZD2Sp1dEvq84bxezF6S4I6_vtGYpqv5cS07woVMZaqvih_esJ6XykxBmZ9VeNHvknXAtqQgKYGjRCl5a2IuBbw67VDqiziIJcOr4q1AESWKJsLn50X_Tv8ZxJ2YK4_QciERLd6PeuHrLTV3a6ogsNSydMXoaPw0YZCpE4sjodWT-7QwRC-CrggvFuO9AHEDszptnh1CcrL3dBkdhAV2vSlgZRz-tgEf8c4FsyhIB0w4uWtG5vg2TqvTQ-wB-RpRDCqhL6QIaaXg86Y9owoF7WcMcwBO2kTJmUv594r0kz3_TNDLO61A; NID=67=CtFi7zrLBFQ3SPq896mQq2pqp3VWXXqxPSf98YOATWSeI51FHwFv32-mnX3IMgrZn6-rX90-z_9Ghtkq7slka5hL_6dv90RvNAFz52aniq_aQKcmUdlF7m-Oc2qTliPfwGI1GovbFvWZyVPL8vQ67wpAPgumj_X91Ty7HCpUh3hwQPUZPeWeLyEgnAzF; PLAY_ACTIVE_ACCOUNT=ICrt_XL61NBE_S0rhk8RpG0k65e0XwQVdDlvB6kxiQ8=pulkitnandan@gmail.com; PLAY_PREFS=CgJJThDI4q2mrCkiI2NsOmRldGFpbHMuZG91YmxlX2ZldGNoX3NvY2lhbF9kYXRhIidjbDpkZXRhaWxzLmhpZGVfZG93bmxvYWRfY291bnRfaW5fdGl0bGUiD2NvbnRlbnRfcmF0aW5ncyITbmV3X21lcmNoYW50X3NpZ251cCIbbm9jYWNoZTphY3RpdmVfYXBwc19icm93c2VyIh1ub2NhY2hlOmNhcnRfc2hvcnRfbmFtZV9tdXNpYyIZbm9jYWNoZTpjaGFydHM6Ym9va19kZWFscyIfbm9jYWNoZTpjaGFydHM6bW92aWVfcHJlX29yZGVycyIbbm9jYWNoZTplbmFibGVfcGxheV9jb3VudHJ5IjNub2NhY2hlOmVuYWJsZV91bHlzc2VzX2NvbXBhdGlibGVfc3Vic2NyaXB0aW9uX2NvZGUiFW5vY2FjaGU6ZW5jcnlwdGVkX2FwayIebm9jYWNoZTpsaWZldGltZV9ib29rc19icm93c2VyIhZub2NhY2hlOm11c2ljX3N1Yl9ub25lIhlub2NhY2hlOm5ld19ib29rc19icm93c2VyIhlub2NhY2hlOm5ld19tdXNpY19icm93c2VyIiJub2NhY2hlOm5ld191c2VyX2NsdXN0ZXJfbWFnYXppbmVzIh5ub2NhY2hlOm5ld191c2VyX2NsdXN0ZXJfbXVzaWMiHm5vY2FjaGU6bmV3X3VzZXJfY2x1c3Rlcl9vY2VhbiIgbm9jYWNoZTpuZXdfdXNlcl9jbHVzdGVyX3lvdXR1YmUiHG5vY2FjaGU6bmV3X3VzZXJfZXhwX2RheV9vbGQiGm5vY2FjaGU6bmV3X3VzZXJfbWFnYXppbmVzIhZub2NhY2hlOm5ld191c2VyX211c2ljIhZub2NhY2hlOm5ld191c2VyX29jZWFuIhhub2NhY2hlOm5ld191c2VyX3lvdXR1YmUiGW5vY2FjaGU6bmV3X3ZpZGVvX2Jyb3dzZXIiDm5vY2FjaGU6bm9fZm9wIipub2NhY2hlOm5vbl9zdWJfbm9uX211c2ljX3VzZXJfd2l0aG91dF9mb3AiIW5vY2FjaGU6bm90X25ld191c2VyX2FuZHJvaWRfYXBwcyIYbm9jYWNoZTpvYWt0cmVlX2NvbnRyb2wyIiFub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjEiKG5vY2FjaGU6cGxhdG9fYWJfYWxsX3VzZXJzXzI1X2FiMl9ub3JtYWwiKG5vY2FjaGU6cGxhdG9fYWJfYWxsX3VzZXJzXzI1X2FiM19ub3JtYWwiKG5vY2FjaGU6cGxhdG9fYWJfYWxsX3VzZXJzXzI1X2FiNF9ub3JtYWwiKW5vY2FjaGU6cGxhdG9fYWJfYWxsX3VzZXJzXzI1X2FiNV9jb250cm9sIiNub2NhY2hlOnByZWZlcl9ibGFfZm9yX3BsYXlfY291bnRyeSIvbm9jYWNoZTpwcmVpbnN0YWxsLWNyb3NzLXNlbGwtZnJvbS1sYXNlci1hbHdheXMiHW5vY2FjaGU6cmVjZW50X2Jvb2tzX2FjcXVpcmVyIjJub2NhY2hlOnRhcmdldGVkX3Byb21vOm5vX25leHVzXzZfbXVzaWNfYWxsX2FjY2VzcyIWbm9jYWNoZTp1c2VyX2NoYWxsZW5nZSIMb2ZmbGluZV9hcHBzIg9vZmZsaW5lX2FydGlzdHMiDW9mZmxpbmVfbXVzaWMoyOKtpqwp:S:ANO1ljJVdV4VRYfR; _ga=GA1.3.1320954387.1418514133; _gat=1";//in.nextLine();
		
		in.close();

		LaunchApp scrap = new LaunchApp();

		if (scrap.scrapPlaystore())
			System.out.println("PlayStore Scrapped");
		else
			System.out.println("Something Went Wrong");
	}

}
