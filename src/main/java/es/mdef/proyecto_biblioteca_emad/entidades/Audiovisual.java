package es.mdef.proyecto_biblioteca_emad.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Audiovisual extends Documento{

	private int ISAN;
    private int duracion;
    private String tipo;
    
    @Override
	public Categoria getCategoria() {
		return Categoria.audiovisual;
	}
    @Override
	public Categoria setCategoria() {		
		return Categoria.audiovisual;
	}
    
	public int getISAN() {
		return ISAN;
	}
	public void setISAN(int iSAN) {
		ISAN = iSAN;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}    
    
}
