package principal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.youTubeDataApi.service.Comentario;
import com.youTubeDataApi.service.Comment;
import com.youTubeDataApi.service.Video;
import com.youTubeDataApi.service.Util;
import com.youTubeDataApi.service.GuardarVideo;


public class Principal {
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	static Util util = new Util();
	static Video svideo =  new Video();
	static Comment comment = new Comment();
	

	public static void main(String[] args) {
		
		try {

			YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {}
			}).setApplicationName("YouTubeDataTestApi").build();

			String queryTerm = util.getInputQuery();

			List<SearchResult> searchResultList = svideo.getVideosByFilter(queryTerm, youtube);
			processData(searchResultList, youtube);
			
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
		String fechaPublicacion;
		String fechaVideo;
		String Chanelid;
		String comChanelid;
		CommentThread commentThread;
		List<CommentThread> commentResultList;
		List<Comentario> listaComentario;
		ArrayList<Video> listaVideos = new ArrayList<Video>();
		Video video; 	//se crea objeto_video
		Comentario comm; 	//se crea objeto_comentario
		while(searchResultIterator.hasNext()){
			try{
			//System.out.println("************VIDEO***************");
			SearchResult singleVideo = searchResultIterator.next();
			titleVideo = singleVideo.getSnippet().getTitle();
			Chanelid = singleVideo.getSnippet().getChannelId();
			canalVideo = singleVideo.getSnippet().getChannelTitle();
			idVideo = singleVideo.getId().getVideoId();
			//System.out.println(titleVideo);
			//System.out.println(canalVideo);
			System.out.println("ID Video: "+idVideo);
			video = new Video();	//se instancia objeto_video
			video.settVideo(titleVideo);
			video.setcVideo(canalVideo);
			video.setIdVideo(idVideo);
			video.setChanelId(Chanelid);
			//System.out.println("--------------------------------------------");
			commentResultList = comment.getCommentsByVideoId(idVideo, youtube);
			commentResultIterator = commentResultList.iterator();
			fechaVideo = singleVideo.getSnippet().get("publishedAt").toString();
			//System.out.println("*******COMENTARIO*********");
			listaComentario = new ArrayList<Comentario>();
			while(commentResultIterator.hasNext()){
				commentThread = commentResultIterator.next();
				canalPublica = commentThread.getSnippet().getTopLevelComment().getSnippet().get("authorDisplayName").toString();
				comentario = commentThread.getSnippet().getTopLevelComment().getSnippet().get("textDisplay").toString();
				fechaPublicacion = commentThread.getSnippet().getTopLevelComment().getSnippet().get("publishedAt").toString();
				comChanelid = commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorChannelId().getValue().toString();
				//System.out.println(commentThread);
				//System.out.println(canalPublica);
				//System.out.println(comentario);
				//System.out.println("---------------------------------------");
				comm = new Comentario();//se instancia objeto_comentario
				comm.setCanalPublica(canalPublica);
				comm.setComentario(comentario);
				comm.setFechaCom(fechaPublicacion);
				comm.setComChanelid(comChanelid);
				listaComentario.add(comm); 	//agregar objeto_comentario a lista_comentarios
			}
			video.setComentario(listaComentario);//agregamos a una lista_comentarios a obejto_video
			listaVideos.add(video);//se agrega obejto_video a lista_videos
			}catch(Exception e){
				continue;
			}
			}
		//se manda la lista_videos al metodo que guardara todo en la DB
		GuardarVideo.guardarv(listaVideos);
		System.out.println("FIN");
	}

}
