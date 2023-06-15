package es.mdef.proyecto_biblioteca_emad.repositorios.impl;

import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;

public class PrestamoAgrupadoPorDocumento {

	private long cantidad;
	private DocumentoConId documentoConId;

	public PrestamoAgrupadoPorDocumento(DocumentoConId documentoConId, long count) {
		this.cantidad = count;
		this.documentoConId = documentoConId;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long count) {
		this.cantidad = count;
	}

	public DocumentoConId getDocumento() {
		return documentoConId;
	}

	public void setDocumento(DocumentoConId documentoConId) {
		this.documentoConId = documentoConId;
	}

	@Override
	public String toString() {
		return "PrestamoAgrupadoPorDocumento [cantidad=" + cantidad + ", documentoConId=" + documentoConId.getTitulo()+"("+documentoConId.getId()+")]\n";
	}

}
