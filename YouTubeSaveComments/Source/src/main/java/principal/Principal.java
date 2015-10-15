package principal;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.SearchResult;
import com.youTubeDataApi.service.Comment;
import com.youTubeDataApi.service.Util;
import com.youTubeDataApi.service.Video;

public class Principal {
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	static Util util = new Util();
	static Video video =  new Video();
	static Comment comment = new Comment();

	public static void main(String[] args) {
		
		try {

			YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {}
			}).setApplicationName("YouTubeDataTestApi").build();

			String queryTerm = util.getInputQuery();

			List<SearchResult> searchResultList = video.getVideosByFilter(queryTerm, youtube);
			processData(searchResultList, youtube);
			
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static void processData(List<SearchResult> searchResultList, YouTube youtube){
		Iterator<SearchResult> searchResultIterator = searchResultList.iterator();
		Iterator<CommentThread> commentResultIterator;
		String titleVideo;
		String canalVideo;
		String idVideo;
		String canalPublica;
		String comentario;
		CommentThread commentThread;
		List<CommentThread> commentResultList;
		while(searchResultIterator.hasNext()){
			System.out.println("************VIDEO***************");
			SearchResult singleVideo = searchResultIterator.next();
			titleVideo = singleVideo.getSnippet().getTitle();
			canalVideo = singleVideo.getSnippet().getChannelTitle();
			idVideo = singleVideo.getId().getVideoId();
			System.out.println(titleVideo);
			System.out.println(canalVideo);
			System.out.println(idVideo);
			System.out.println("--------------------------------------------");
			commentResultList = comment.getCommentsByVideoId(idVideo, youtube);
			commentResultIterator = commentResultList.iterator();
			System.out.println("*******COMENTARIO*********");
			while(commentResultIterator.hasNext()){
				commentThread = commentResultIterator.next();
				canalPublica = commentThread.getSnippet().getTopLevelComment().getSnippet().get("authorDisplayName").toString();
				comentario = commentThread.getSnippet().getTopLevelComment().getSnippet().get("textDisplay").toString();
				System.out.println(canalPublica);
				System.out.println(comentario);
				System.out.println("---------------------------------------");
			}
			
		}
		
	}

}
