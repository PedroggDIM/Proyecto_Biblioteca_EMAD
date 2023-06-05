package es.mdef.proyecto_biblioteca_emad.repositorios.impl;

import es.mdef.proyecto_biblioteca_emad.entidades.Documento;

public class PrestamoAgrupadoPorDocumento {

	private long count;
	private Documento documento;

	public PrestamoAgrupadoPorDocumento(Documento documento, long count) {
		this.count = count;
		this.documento = documento;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		return "PrestamoAgrupadoPorDocumento [count=" + count + ", documento=" + documento.getTitulo()+"("+documento.getId()+")]\n";
	}

}
