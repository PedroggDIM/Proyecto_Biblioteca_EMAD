package es.mdef.proyecto_biblioteca_emad.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class Escrito extends Documento{

	private int ISBN;
	private int numpaginas;
	private int tamano;
	
	@Override
	public Categoria getCategoria() {		
		return Categoria.escrito;
	}	
	@Override
	public Categoria setCategoria() {		
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
