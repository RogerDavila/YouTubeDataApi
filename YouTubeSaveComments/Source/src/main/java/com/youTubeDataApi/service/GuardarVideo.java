package com.youTubeDataApi.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;

import com.mysql.jdbc.Connection;
import com.youTubeDataApi.service.Conexion;
import com.youTubeDataApi.service.Video;
import com.youTubeDataApi.service.Comentario;

public class GuardarVideo {
	

	public static void guardarv(ArrayList<Video> listaVideos){
		ArrayList<String> addedvideo = new ArrayList<String>();
		ArrayList<String> addedcanal = new ArrayList<String>();
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		//addedvideo = null;
		//addedcanal = null;
		Video video;
		Comentario coment =  null;
		//video sea unico
		//validar canal
		try{
			con = new Conexion().Conectar();
			System.out.println("Conexion creada correctamente");
			st = (Statement)con.createStatement();
			query = "SELECT ID FROM VIDEOS;";
			rs = st.executeQuery(query);
			String id = null;
			 while (rs.next())
		      {
		        id = rs.getString("ID");
		        addedvideo.add(""+id);
		      }
			query = "SELECT ID FROM CANALES;";
			rs = st.executeQuery(query);
			while (rs.next())
			{
				id = rs.getString("ID");
				addedcanal.add(id);
			    }
			 
			 
			 for(int i= 0; i < listaVideos.size(); i++){
				 video = listaVideos.get(i);
					 if(addedcanal!=null && !addedcanal.isEmpty()){
						 if(!addedcanal.contains(video.getChanelId().toString())){
							 query = "INSERT INTO CANALES " + "VALUES ('"+video.getChanelId()+"','"+video.getcVideo()+"')";
							 st.executeUpdate(query);
							 addedcanal.add(video.getChanelId());
						 }
					 }else{
						 query = "INSERT INTO CANALES " + "VALUES ('"+video.getChanelId()+"','"+video.getcVideo()+"')";
						 st.executeUpdate(query);
						 addedcanal.add(video.getChanelId().toString());
					 }
					 
					 video = listaVideos.get(i);
					 if(addedvideo!=null && !addedvideo.isEmpty()){
						 if(!addedvideo.contains(video.getIdVideo())){
							 query = "INSERT INTO VIDEOS(ID, NOMBRE, CANAL_ID) " + "VALUES ('"+video.getIdVideo()+"','"+video.gettVideo()+"','"+video.getChanelId()+"')";
						     st.executeUpdate(query);
						     addedvideo.add(video.getIdVideo().toString());
						     for(int j=0; j< video.getComentario().size(); j++){
						    	 coment = video.getComentario().get(j);
						    	 if(addedcanal!=null && !addedcanal.isEmpty()){
									 if(!addedcanal.contains(coment.getComChanelid())){
										 query = "INSERT INTO CANALES " + "VALUES ('"+coment.getComChanelid()+"','"+coment.getCanalPublica()+"')";
										 st.executeUpdate(query);
										 addedcanal.add(coment.getComChanelid());
									 }
								 }else{
									 query = "INSERT INTO CANALES " + "VALUES ('"+coment.getComChanelid()+"','"+coment.getCanalPublica()+"')";
									 st.executeUpdate(query);
									 addedcanal.add(coment.getComChanelid());
								 }
						    	 try{
						    	 query = "INSERT INTO COMENTARIOS(COMENTARIO, VIDEO_ID, CANAL_ID) " + "VALUES ('"+coment.getComentario().replace("'", " ")+"','"+video.getIdVideo()+"','"+coment.getComChanelid()+"')";
						    	 System.out.println(query);
						    	 st.executeUpdate(query);
						    	 }catch(Exception e){
									 continue;
								}
						     }
						 }
					 }else{
						 query = "INSERT INTO VIDEOS(ID, NOMBRE, CANAL_ID) " + "VALUES ('"+video.getIdVideo()+"','"+video.gettVideo()+"','"+video.getChanelId()+"')";
					     st.executeUpdate(query);
					     addedvideo.add(video.getIdVideo());
					     for(int j=0; j< video.getComentario().size(); j++){
					    	 coment = video.getComentario().get(j);
					    	 if(addedcanal!=null && !addedcanal.isEmpty()){
								 if(!addedcanal.contains(coment.getComChanelid())){
									 query = "INSERT INTO CANALES " + "VALUES ('"+coment.getComChanelid()+"','"+coment.getCanalPublica()+"')";
									 st.executeUpdate(query);
									 addedcanal.add(coment.getComChanelid());
								 }
							 }else{
								 query = "INSERT INTO CANALES " + "VALUES ('"+coment.getComChanelid()+"','"+coment.getCanalPublica()+"')";
								 st.executeUpdate(query);
								 addedcanal.add(coment.getComChanelid());
							 }
					    	 try{
					    	 query = "INSERT INTO COMENTARIOS(COMENTARIO, VIDEO_ID, CANAL_ID) " + "VALUES ('"+coment.getComentario().replace("'", " ")+"','"+video.getIdVideo()+"','"+coment.getComChanelid()+"')";
					    	 System.out.println(query);
					    	 st.executeUpdate(query);
					    	 }catch(Exception e){
								 continue;
							}
					 }
				 }
		     }
		}catch(SQLException e){
			System.out.println(e);
		}
		}
	}

