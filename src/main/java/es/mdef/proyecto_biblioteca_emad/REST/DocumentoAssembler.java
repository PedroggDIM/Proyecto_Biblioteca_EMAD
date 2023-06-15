package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.Audiovisual;
import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.entidades.Escrito;
import es.mdef.proyecto_biblioteca_emad_libreria.Categoria;

@Component
public class DocumentoAssembler implements RepresentationModelAssembler<DocumentoConId, DocumentoModel> {
	@Override
	public DocumentoModel toModel(DocumentoConId entity) {
		DocumentoModel model = new DocumentoModel();

		model.setId(entity.getId());
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

	public DocumentoConId toEntity(DocumentoModel model) {
		DocumentoConId documentoConId = null;
		if (model.getCategoria() == Categoria.escrito) {
			Escrito escrito = new Escrito();
			escrito.setISBN(model.getISBN());
			escrito.setNumpaginas(model.getNumPaginas());
			escrito.setTamano(model.getTamano());
			documentoConId = escrito;
		} else if (model.getCategoria() == Categoria.audiovisual) {
			Audiovisual audiovisual = new Audiovisual();
			audiovisual.setISAN(model.getISAN());
			audiovisual.setDuracion(model.getDuracion());
			audiovisual.setTipo(model.getTipo());
			documentoConId = audiovisual;
		}

		documentoConId.setId(model.getId());
		documentoConId.setNumCopias(model.getNumCopias());
		documentoConId.setTitulo(model.getTitulo());
		documentoConId.setAutor(model.getAutor());
		documentoConId.setSinopsis(model.getSinopsis());
		documentoConId.setEstanteria(model.getEstanteria());
		documentoConId.setFechaAlta(model.getFechaAlta());
		documentoConId.setDisponible(model.isDisponible());
		return documentoConId;
	}
}
