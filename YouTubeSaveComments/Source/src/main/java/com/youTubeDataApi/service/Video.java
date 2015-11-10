package com.youTubeDataApi.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.youTubeDataApi.service.Comentario;

import java.io.IOException;
import java.util.List;

public class Video{
	
	String tVideo;
	public List<Comentario> getComentario() {
		return comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	String cVideo;
	String idVideo;
	String chanelId;
	public String getChanelId() {
		return chanelId;
	}

	public void setChanelId(String chanelId) {
		this.chanelId = chanelId;
	}

	List<Comentario> comentario;

	public String gettVideo() {
		return tVideo;
	}

	public void settVideo(String tVideo) {
		this.tVideo = tVideo;
	}

	public String getcVideo() {
		return cVideo;
	}

	public void setcVideo(String cVideo) {
		this.cVideo = cVideo;
	}

	public String getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}

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