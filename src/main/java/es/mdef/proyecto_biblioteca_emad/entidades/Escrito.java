package es.mdef.proyecto_biblioteca_emad.entidades;

import es.mdef.proyecto_biblioteca_emad_libreria.Categoria;
import es.mdef.proyecto_biblioteca_emad_libreria.Escritable;

public class Escrito extends DocumentoConId implements Escritable {

	private int ISBN;
	private int numpaginas;
	private int tamano;

	@Override
	public Categoria getCategoria() {
		return Categoria.escrito;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public int getNumpaginas() {
		return numpaginas;
	}

	public void setNumpaginas(int numpaginas) {
		this.numpaginas = numpaginas;
	}

	public int getTamano() {
		return tamano;
	}

	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

}