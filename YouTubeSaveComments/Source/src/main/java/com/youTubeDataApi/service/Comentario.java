package com.youTubeDataApi.service;

public class Comentario {
	
	String canalPublica;
	String comentario;
	String fechaCom;
	String ComChanelid;
	
	public String getComChanelid() {
		return ComChanelid;
	}
	public void setComChanelid(String comChanelid) {
		ComChanelid = comChanelid;
	}
	public String getCanalPublica() {
		return canalPublica;
	}
	public void setCanalPublica(String canalPublica) {
		this.canalPublica = canalPublica;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFechaCom() {
		return fechaCom;
	}
	public void setFechaCom(String fechaCom) {
		this.fechaCom = fechaCom;
	}

}
