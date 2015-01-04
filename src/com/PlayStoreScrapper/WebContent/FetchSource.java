package com.PlayStoreScrapper.WebContent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class FetchSource{

private static void getUrlSource(String url) throws IOException {
            URL playStore = new URL(url);
            URLConnection playStoreConnect = playStore.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
            		playStoreConnect.getInputStream(), "UTF-8"));
            String inputLine;
            PrintWriter writer = new PrintWriter("the-file-name1.txt", "UTF-8");
            int i = 0;
            inputLine = in.readLine();
            while (inputLine != null){
            	inputLine = in.readLine();
            	i++;
            	writer.println(inputLine);
            }
            
            System.out.println(i);
            writer.close();
            in.close();
        }

public static void main(String[] args) throws IOException{
	getUrlSource("https://play.google.com/store/apps/details?id=com.ea.game.simcitymobile_row");
}

}