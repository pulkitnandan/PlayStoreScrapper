package com.playstorescrapper.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

public class PostRequest {

	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";

	
	/**
	 * @param args
	 */
	
	private void sendPost() throws Exception {
		 
		String url = "https://play.google.com/store/getreviews";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("accept-encoding", "gzip, deflate");
		con.setRequestProperty("accept-language", "en-US,en;q=0.8");
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("content-length", "129");
		con.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		String cookie = "PREF=ID=c2d3d0832d82dafb:U=4acd42280a5cccec:FF=0:LD=en:TM=1418046655:LM=1419439613:GM=1:S=-_Oez1RydpcBW2uy; enabledapps.uploader=0; HSID=AyIWzrvlSgDwrYyQP; SSID=AkPgm-9ojbwqL_oFm; APISID=JOet9s2JI8p8SY8T/Ahgvr1qH-p6tJoMsN; SAPISID=VvwHvn568QMlgUeo/AngpI00wUrpN7TPe5; SID=DQAAAAIBAABwqMfbBx29P2VFRMMVhiT8wdmYqhh0f5HRfeMuz56li1WhdMf5Lb5v-r9ZD2Sp1dEvq84bxezF6S4I6_vtGYpqv5cS07woVMZaqvih_esJ6XykxBmZ9VeNHvknXAtqQgKYGjRCl5a2IuBbw67VDqiziIJcOr4q1AESWKJsLn50X_Tv8ZxJ2YK4_QciERLd6PeuHrLTV3a6ogsNSydMXoaPw0YZCpE4sjodWT-7QwRC-CrggvFuO9AHEDszptnh1CcrL3dBkdhAV2vSlgZRz-tgEf8c4FsyhIB0w4uWtG5vg2TqvTQ-wB-RpRDCqhL6QIaaXg86Y9owoF7WcMcwBO2kTJmUv594r0kz3_TNDLO61A; NID=67=CtFi7zrLBFQ3SPq896mQq2pqp3VWXXqxPSf98YOATWSeI51FHwFv32-mnX3IMgrZn6-rX90-z_9Ghtkq7slka5hL_6dv90RvNAFz52aniq_aQKcmUdlF7m-Oc2qTliPfwGI1GovbFvWZyVPL8vQ67wpAPgumj_X91Ty7HCpUh3hwQPUZPeWeLyEgnAzF; PLAY_ACTIVE_ACCOUNT=ICrt_XL61NBE_S0rhk8RpG0k65e0XwQVdDlvB6kxiQ8=pulkitnandan@gmail.com; PLAY_PREFS=CgJJThDcsobAqykiI2NsOmRldGFpbHMuZG91YmxlX2ZldGNoX3NvY2lhbF9kYXRhIidjbDpkZXRhaWxzLmhpZGVfZG93bmxvYWRfY291bnRfaW5fdGl0bGUiD2NvbnRlbnRfcmF0aW5ncyITbmV3X21lcmNoYW50X3NpZ251cCIcbm9jYWNoZTphY3RpdmVfYXBwc19hY3F1aXJlciIdbm9jYWNoZTpjYXJ0X3Nob3J0X25hbWVfbXVzaWMiGW5vY2FjaGU6Y2hhcnRzOmJvb2tfZGVhbHMiH25vY2FjaGU6Y2hhcnRzOm1vdmllX3ByZV9vcmRlcnMiG25vY2FjaGU6ZW5hYmxlX3BsYXlfY291bnRyeSIzbm9jYWNoZTplbmFibGVfdWx5c3Nlc19jb21wYXRpYmxlX3N1YnNjcmlwdGlvbl9jb2RlIhVub2NhY2hlOmVuY3J5cHRlZF9hcGsiHm5vY2FjaGU6bGlmZXRpbWVfYm9va3NfYnJvd3NlciIWbm9jYWNoZTptdXNpY19zdWJfbm9uZSIZbm9jYWNoZTpuZXdfYm9va3NfYnJvd3NlciIZbm9jYWNoZTpuZXdfbXVzaWNfYnJvd3NlciIibm9jYWNoZTpuZXdfdXNlcl9jbHVzdGVyX21hZ2F6aW5lcyIebm9jYWNoZTpuZXdfdXNlcl9jbHVzdGVyX211c2ljIh5ub2NhY2hlOm5ld191c2VyX2NsdXN0ZXJfb2NlYW4iIG5vY2FjaGU6bmV3X3VzZXJfY2x1c3Rlcl95b3V0dWJlIhxub2NhY2hlOm5ld191c2VyX2V4cF9kYXlfb2xkIhpub2NhY2hlOm5ld191c2VyX21hZ2F6aW5lcyIWbm9jYWNoZTpuZXdfdXNlcl9tdXNpYyIWbm9jYWNoZTpuZXdfdXNlcl9vY2VhbiIYbm9jYWNoZTpuZXdfdXNlcl95b3V0dWJlIhlub2NhY2hlOm5ld192aWRlb19icm93c2VyIg5ub2NhY2hlOm5vX2ZvcCIqbm9jYWNoZTpub25fc3ViX25vbl9tdXNpY191c2VyX3dpdGhvdXRfZm9wIiFub2NhY2hlOm5vdF9uZXdfdXNlcl9hbmRyb2lkX2FwcHMiGG5vY2FjaGU6b2FrdHJlZV9jb250cm9sMiIhbm9jYWNoZTpwbGF0b19hYl9hbGxfdXNlcnNfMjVfYWIxIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjJfbm9ybWFsIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjNfbm9ybWFsIihub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjRfbm9ybWFsIilub2NhY2hlOnBsYXRvX2FiX2FsbF91c2Vyc18yNV9hYjVfY29udHJvbCIjbm9jYWNoZTpwcmVmZXJfYmxhX2Zvcl9wbGF5X2NvdW50cnkiL25vY2FjaGU6cHJlaW5zdGFsbC1jcm9zcy1zZWxsLWZyb20tbGFzZXItYWx3YXlzIh1ub2NhY2hlOnJlY2VudF9ib29rc19hY3F1aXJlciIybm9jYWNoZTp0YXJnZXRlZF9wcm9tbzpub19uZXh1c182X211c2ljX2FsbF9hY2Nlc3MiFm5vY2FjaGU6dXNlcl9jaGFsbGVuZ2UiDG9mZmxpbmVfYXBwcyIPb2ZmbGluZV9hcnRpc3RzIg1vZmZsaW5lX211c2ljKLjH666rKQ:S:ANO1ljL8hWaX9aw3; _ga=GA1.3.1320954387.1418514133";
		con.setRequestProperty("cookie", cookie);
		con.setRequestProperty("origin", "https://play.google.com");
		con.setRequestProperty("pragma", "no-cache");
		con.setRequestProperty("referer", "https://play.google.com/store/apps/details?id=com.ea.game.simcitymobile_row");
		con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		con.setRequestProperty("x-client-data", "CJW2yQEIpbbJAQiptskBCMS2yQEInobKAQjuiMoBCMWUygE=");
 
		String urlParameters = "reviewType=0&pageNum=1&id=com.ea.game.simcitymobile_row&reviewSortOrder=4&xhr=1&token=DA4mCNuRpGCuuJfXCDHrRcqDw2Y%3A1420428587710";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		
		   if(con.getContentEncoding().indexOf("gzip") != -1){
			      System.out.println("This is gzipped content  " );
			      StringWriter responseBody = new StringWriter();
			      PrintWriter responseWriter = new PrintWriter("thesddss.txt");
			      GZIPInputStream zippedInputStream =  new GZIPInputStream(con.getInputStream());
			        BufferedReader r = new BufferedReader(new InputStreamReader(zippedInputStream));
			        String line = null;
			          while( (line =r.readLine()) != null){
			            responseWriter.println(line);
			          }
			          System.out.println(responseBody.toString());
			          responseWriter.close();
			      }
		
		/*

 
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
*/ 
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new PostRequest().sendPost();
	}

}
