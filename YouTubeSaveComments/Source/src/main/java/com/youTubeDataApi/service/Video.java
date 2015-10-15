package com.youTubeDataApi.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

public class Video {

	private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
	Util util = new Util();

	public List<SearchResult> getVideosByFilter(String queryTerm, YouTube youtube) throws IOException{
		YouTube.Search.List search = youtube.search().list("id,snippet");
		
		List<SearchResult> searchResultList = null;
		
		String apiKey = util.getApiKey();
		search.setKey(apiKey);
		search.setQ(queryTerm);

		search.setType("video");

		search.setFields("items(id/videoId,snippet)");
		search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
		SearchListResponse searchResponse = search.execute();

		searchResultList = searchResponse.getItems();
		
		return searchResultList;
	}
	
}