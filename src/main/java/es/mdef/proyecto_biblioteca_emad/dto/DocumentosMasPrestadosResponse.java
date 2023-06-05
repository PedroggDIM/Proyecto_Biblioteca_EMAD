package es.mdef.proyecto_biblioteca_emad.dto;

import java.util.List;

public class DocumentosMasPrestadosResponse {

	private List<PrestamoAgrupadoPorDocumentoResponse> lstDocumentosMasPrestados;
	private String mensaje;

	

	public List<PrestamoAgrupadoPorDocumentoResponse> getLstDocumentosMasPrestados() {
		return lstDocumentosMasPrestados;
	}

	public void setLstDocumentosMasPrestados(List<PrestamoAgrupadoPorDocumentoResponse> lstDocumentosMasPrestados) {
		this.lstDocumentosMasPrestados = lstDocumentosMasPrestados;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
