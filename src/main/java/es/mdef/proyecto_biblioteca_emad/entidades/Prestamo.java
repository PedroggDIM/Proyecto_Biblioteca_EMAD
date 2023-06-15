package es.mdef.proyecto_biblioteca_emad.entidades;

import es.mdef.proyecto_biblioteca_emad_libreria.PrestamoImp;

public class Prestamo extends PrestamoImp {
	public static final String ID = "id";
	public static final String DOCUMENTO = "documento";
	public static final String FECHA_INICIO = "fechaInicio";
	public static final String FECHA_FIN = "fechaFin";

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}