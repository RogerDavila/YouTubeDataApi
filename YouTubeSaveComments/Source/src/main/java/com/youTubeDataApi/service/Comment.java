package com.youTubeDataApi.service;

import java.io.IOException;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;

public class Comment {

	Util util = new Util();

	public List<CommentThread> getCommentsByVideoId(String videoId, YouTube youtube){
		List<CommentThread> commentsResultList = null;
		try {
			Long max = 20l;
			String apiKey = util.getApiKey();
			CommentThreadListResponse commentsListResponse =
					youtube.commentThreads().list("snippet").setMaxResults(max).setVideoId(videoId).setKey(apiKey).setTextFormat("plainText").execute();
			commentsResultList = commentsListResponse.getItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentsResultList;
	}
}
