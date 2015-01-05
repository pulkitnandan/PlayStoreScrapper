package com.playstorescrapper.webcontent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SessionInfo {
	
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";

	private void sendGet() throws Exception {
		 
		String url = "https://play.google.com/store/apps/details?id=com.ea.game.simcitymobile_row";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		System.out.println(" Timeout " + con.getConnectTimeout() + "\n" + con.getContentEncoding() + "\n" + con.getContentLength() + "\n" + 
				con.getContentType() + "\n" + con.getDate() + "\n" + con.getExpiration() + "\n" + con.getHeaderField(0) + "\n" + con.getHeaderField("cookie")
				+ "\n" + con.getIfModifiedSince() + "\n" + con.getReadTimeout() + "\n" + con.getContent() + "\n" + con.getHeaderFields() + "\n"
				 + "\n" );
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
	
	public SessionInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SessionInfo http = new SessionInfo();
		 
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();

	}

}
