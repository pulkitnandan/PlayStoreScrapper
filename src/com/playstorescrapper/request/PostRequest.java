package com.playstorescrapper.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

public class PostRequest {

	// PlayStore Constants

	private final String URL = "https://play.google.com/store/getreviews";
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
	private final String ACCEPT = "*/*";
	private final String ACCEPT_ENCODING = "gzip, deflate";
	private final String ACCEPT_LANGUAGE = "en-US,en;q=0.8";
	private final String CACHE_CONTROL = "no-cache";
	private final String CONTENT_LENGTH = "129";
	private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
	private final String ORIGIN = "https://play.google.com";
	private String APP_ID = null;
	private String COOKIE = null;

	public PostRequest(String appId, String cookie) {
		APP_ID = appId;
		COOKIE = cookie;
	}

	private void setupConnectionDefaults(HttpsURLConnection connection)
			throws ProtocolException {
		// setup request headers
		connection.setRequestMethod("POST");
		connection.setRequestProperty("accept", ACCEPT);
		connection.setRequestProperty("accept-encoding", ACCEPT_ENCODING);
		connection.setRequestProperty("accept-language", ACCEPT_LANGUAGE);
		connection.setRequestProperty("cache-connectiontrol", CACHE_CONTROL);
		connection.setRequestProperty("connectiontent-length", CONTENT_LENGTH);
		connection.setRequestProperty("connectiontent-type", CONTENT_TYPE);
		connection.setRequestProperty("origin", ORIGIN);
		connection.setRequestProperty("pragma", CACHE_CONTROL);
		connection.setRequestProperty("user-agent", USER_AGENT);
		// Send post request
		connection.setDoOutput(true);
	}

	public String jsonResponse(int pageNumber) throws Exception {

		String jsonString = "";
		String referer = "https://play.google.com/store/apps/details?id="
				+ APP_ID;

		URL obj = new URL(URL);

		int responseCode = 200;
		PrintWriter responseWriter = new PrintWriter("REVIEWS" + APP_ID
				+ ".JSON");
		responseWriter.println("");

		// while (responseCode == 200) {

		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		setupConnectionDefaults(con);

		con.setRequestProperty("cookie", COOKIE);
		con.setRequestProperty("referer", referer);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		String urlParameters = "reviewType=0&pageNum="
				+ pageNumber
				+ "&id="
				+ APP_ID
				+ "&reviewSortOrder=4&xhr=1&token=DA4mCNuRpGCuuJfXCDHrRcqDw2Y%3A1420428587710";

		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + URL);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		if (con.getContentEncoding().indexOf("gzip") != -1) {
			System.out.println("This is gzipped content  ");
			GZIPInputStream zippedInputStream = new GZIPInputStream(
					con.getInputStream());
			BufferedReader r = new BufferedReader(new InputStreamReader(
					zippedInputStream));
			String line = null;
			jsonString = "";
			int i = 0;
			while ((line = r.readLine()) != null) {
				if (i == 2 || i == 3) {
					jsonString += line;
				}
				i++;
			}
			System.out.println(jsonString);
			zippedInputStream.close();
			wr.close();
		}

		responseWriter.close();
		return jsonString;

	}

}
