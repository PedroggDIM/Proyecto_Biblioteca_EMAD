package es.mdef.proyecto_biblioteca_emad.entidades;

import es.mdef.proyecto_biblioteca_emad_libreria.DocumentoImp;

public abstract class DocumentoConId extends DocumentoImp {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}