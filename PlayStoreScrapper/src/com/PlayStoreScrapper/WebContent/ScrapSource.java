package com.PlayStoreScrapper.WebContent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScrapSource {
	
	private static void scrapSource(String fileName){
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("the-file-name1.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public ScrapSource() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException{
		scrapSource("https://play.google.com/store/apps/details?id=com.ea.game.simcitymobile_row");
	}

}
