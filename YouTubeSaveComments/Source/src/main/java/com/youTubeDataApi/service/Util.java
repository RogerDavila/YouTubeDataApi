package com.youTubeDataApi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Util {

	private String PROPERTIES_FILENAME = "youtube.properties";
	
	public String getInputQuery() throws IOException {

		String inputQuery = "";

		System.out.print("Please enter a search term: ");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		inputQuery = bReader.readLine();

		if (inputQuery.length() < 1) {
			// If nothing is entered, defaults to "YouTube Developers Live."
			inputQuery = "YouTube Developers Live";
		}
		return inputQuery;
	}	
	
	public String getApiKey(){
		  Properties properties = new Properties();
		  String apiKey = null;
		  
		    try {
		    	InputStream in = Video.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
		      properties.load(in);
		      apiKey = properties.getProperty("youtube.apikey");
		    } catch (IOException e) {
		      System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
		          + " : " + e.getMessage());
		      System.exit(1);
		    }
		    return apiKey;
	  }
}
