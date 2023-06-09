package es.mdef.proyecto_biblioteca_emad.REST;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "prestamo")
public class PrestamoModel extends RepresentationModel<PrestamoModel> {

	private Long id;
	private int idUsuario;

	private Date fechaInicio;
	private Date fechaFin;
	private boolean devuelto;
	private DocumentoModel documento;

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

	public DocumentoModel getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoModel documento) {
		this.documento = documento;
	}

	public boolean isDevuelto() {
		return devuelto;
	}

	public void setDevuelto(boolean devuelto) {
		this.devuelto = devuelto;
	}	

}
