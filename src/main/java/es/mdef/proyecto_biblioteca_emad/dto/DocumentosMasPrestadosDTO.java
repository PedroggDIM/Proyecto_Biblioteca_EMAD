package es.mdef.proyecto_biblioteca_emad.dto;

import java.util.Date;

public class DocumentosMasPrestadosDTO {

	private int n;
	private Date fechaInicio;
	private Date fechaFin;

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
