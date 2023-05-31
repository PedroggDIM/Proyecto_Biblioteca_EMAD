package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.Documento;
import es.mdef.proyecto_biblioteca_emad.entidades.Documento.Categoria;
import es.mdef.proyecto_biblioteca_emad.entidades.Escrito;
import es.mdef.proyecto_biblioteca_emad.entidades.Audiovisual;

@Component
public class DocumentoAssembler implements RepresentationModelAssembler<Documento, DocumentoModel> {
	@Override
	public DocumentoModel toModel(Documento entity) {
		DocumentoModel model = new DocumentoModel();
	    
		model.setNumCopias(entity.getNumCopias());
		model.setTitulo(entity.getTitulo());
		model.setAutor(entity.getAutor());
		model.setSinopsis(entity.getSinopsis());
		model.setEstanteria(entity.getEstanteria());
		model.setFechaAlta(entity.getFechaAlta());
		model.setDisponible(entity.isDisponible());
		model.setCategoria(entity.getCategoria());
		if (entity.getCategoria() == Categoria.escrito) {
			model.setISBN(((Escrito) entity).getISBN());
			model.setNumPaginas(((Escrito) entity).getNumpaginas());
			model.setTamano(((Escrito) entity).getTamano());
		} else if (entity.getCategoria() == Categoria.audiovisual) {
			model.setISAN(((Audiovisual) entity).getISAN());
			model.setDuracion(((Audiovisual) entity).getDuracion());
			model.setTipo(((Audiovisual) entity).getTipo());
		}
		model.add(linkTo(methodOn(DocumentoController.class).one(entity.getId())).withSelfRel());
		return model;
	}

	public Documento toEntity(DocumentoModel model) {
		Documento documento = new Documento();
		if (model.getCategoria() == Categoria.escrito) {
			Escrito escrito = new Escrito();
			escrito.setISBN(model.getISBN());	
			escrito.setNumpaginas(model.getNumPaginas());
			escrito.setTamano(model.getTamano());
			documento = escrito;
		} else if (model.getCategoria() == Categoria.audiovisual) {
			Audiovisual audiovisual = new Audiovisual();
			audiovisual.setISAN(model.getISAN());		
			audiovisual.setDuracion(model.getDuracion());
			audiovisual.setTipo(model.getTipo());
			documento = audiovisual;
		}
		documento.setNumCopias(model.getNumCopias());
		documento.setTitulo(model.getTitulo());
		documento.setAutor(model.getAutor());
		documento.setSinopsis(model.getSinopsis());
		documento.setEstanteria(model.getEstanteria());
		documento.setFechaAlta(model.getFechaAlta());
		documento.setDisponible(model.isDisponible());
		return documento;
	}
}
