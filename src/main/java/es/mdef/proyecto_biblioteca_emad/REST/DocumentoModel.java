package es.mdef.proyecto_biblioteca_emad.REST;

import java.sql.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import es.mdef.proyecto_biblioteca_emad.entidades.Documento.Categoria;

@Relation(itemRelation = "documento")
public class DocumentoModel extends RepresentationModel<DocumentoModel>{
    
	private Long id;
	private String titulo;
	private String autor;
	private String sinopsis;
	private int estanteria;
	private Date fechaAlta;
	private boolean disponible;
	
	private Categoria categoria;
	//atributos de categoria escrito
    private int ISBN;
    private int numPaginas;
    private int tamano;
    //atributos de categoria audiovisual
    private int ISAN;
    private int duracion;
    private String tipo;
    
    //getters y setters
    
    
	public String getTitulo() {
		return titulo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public int getEstanteria() {
		return estanteria;
	}
	public void setEstanteria(int estanteria) {
		this.estanteria = estanteria;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public int getNumPaginas() {
		return numPaginas;
	}
	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}
	public int getTamano() {
		return tamano;
	}
	public void setTamano(int tamano) {
		this.tamano = tamano;
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
