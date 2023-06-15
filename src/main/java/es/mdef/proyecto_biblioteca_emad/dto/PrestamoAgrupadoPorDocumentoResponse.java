package es.mdef.proyecto_biblioteca_emad.dto;

import es.mdef.proyecto_biblioteca_emad.REST.DocumentoModel;

public class PrestamoAgrupadoPorDocumentoResponse {

	private long suma;
	private DocumentoModel documentoModel;

	public PrestamoAgrupadoPorDocumentoResponse(long count, DocumentoModel documentoModel) {
		this.suma = count;
		this.documentoModel = documentoModel;
	}

	public long getSuma() {
		return suma;
	}

	public void setSuma(long count) {
		this.suma = count;
	}

	public DocumentoModel getDocumentoModel() {
		return documentoModel;
	}

	public void setDocumentoModel(DocumentoModel documentoModel) {
		this.documentoModel = documentoModel;
	}

}
