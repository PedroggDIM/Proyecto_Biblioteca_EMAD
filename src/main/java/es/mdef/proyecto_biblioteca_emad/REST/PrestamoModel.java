package es.mdef.proyecto_biblioteca_emad.REST;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.proyecto_biblioteca_emad.entidades.Documento;

@Relation(itemRelation = "prestamo")
public class PrestamoModel extends RepresentationModel<PrestamoModel>{

	private Long id;
	private int idUsuario;	
	
	private Date fechaInicio;
	private Date fechaFin;
	
	private Documento documento;
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	//Getter y Setter
	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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
