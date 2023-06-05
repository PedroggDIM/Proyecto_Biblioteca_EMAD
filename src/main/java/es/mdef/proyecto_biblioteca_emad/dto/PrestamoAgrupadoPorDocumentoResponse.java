package es.mdef.proyecto_biblioteca_emad.dto;

import es.mdef.proyecto_biblioteca_emad.REST.DocumentoModel;

public class PrestamoAgrupadoPorDocumentoResponse {

	private long count;
	private DocumentoModel documentoModel;

	public PrestamoAgrupadoPorDocumentoResponse(long count, DocumentoModel documentoModel) {
		this.count = count;
		this.documentoModel = documentoModel;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public DocumentoModel getDocumentoModel() {
		return documentoModel;
	}

	public void setDocumentoModel(DocumentoModel documentoModel) {
		this.documentoModel = documentoModel;
	}

}
