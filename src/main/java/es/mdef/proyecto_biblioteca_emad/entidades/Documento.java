package es.mdef.proyecto_biblioteca_emad.entidades;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DOCUMENTOS")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="categoria", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("null")
public class Documento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String titulo;
	private String autor;
	private String sinopsis;
	private int estanteria;
	private Date fechaAlta;
	private boolean disponible;
	
	@Column(nullable = true)
	private int numCopias;
	
	
	public static enum Categoria {
		escrito, audiovisual
	}
	
	public Categoria getCategoria() {
		return null;
	}
	public Categoria setCategoria() {		
		return null;
	}

	// En Documento en un List recupero todos los préstamos que tiene un usuario.
	@OneToMany(mappedBy = "documento")
	List<Prestamo> prestamos;

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	// Getters y Setters de la clase
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
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
	
	public int getNumCopias() {
		return numCopias;
	}
	
	public void setNumCopias(int numCopias) {
		this.numCopias = numCopias;
	}	

}
