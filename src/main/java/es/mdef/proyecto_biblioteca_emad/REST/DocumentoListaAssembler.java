package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.Audiovisual;
import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.entidades.Escrito;
import es.mdef.proyecto_biblioteca_emad_libreria.Categoria;

@Component
public class DocumentoListaAssembler implements RepresentationModelAssembler<DocumentoConId, DocumentoModel> {

	@Override
	public DocumentoModel toModel(DocumentoConId entity) {
		DocumentoModel model = new DocumentoModel();
		model.setId(entity.getId());
		model.setTitulo(entity.getTitulo());
		model.setAutor(entity.getAutor());
		model.setSinopsis(entity.getSinopsis());
		model.setEstanteria(entity.getEstanteria());
		model.setFechaAlta(entity.getFechaAlta());
		model.setDisponible(entity.isDisponible());
		model.setNumCopias(entity.getNumCopias());
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

	public CollectionModel<DocumentoModel> toCollection(List<DocumentoConId> lista) {
		CollectionModel<DocumentoModel> collection = CollectionModel
				.of(lista.stream().map(this::toModel).collect(Collectors.toList()));
		collection.add(linkTo(methodOn(DocumentoController.class).all()).withRel("documentos"));
		return collection;
	}
}