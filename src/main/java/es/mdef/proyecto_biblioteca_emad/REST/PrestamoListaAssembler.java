package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;

@Component
public class PrestamoListaAssembler implements RepresentationModelAssembler<Prestamo, PrestamoModel> {

	@Autowired
	private DocumentoAssembler documentoAssembler;

	@Override
	public PrestamoModel toModel(Prestamo entity) {
		PrestamoModel model = new PrestamoModel();
		model.setId(entity.getId());
		model.setIdUsuario(entity.getIdUsuario());
		model.setFechaInicio(entity.getFechaInicio());
		model.setFechaFin(entity.getFechaFin());

		model.add(linkTo(methodOn(PrestamoController.class).one(entity.getId())).withSelfRel());

		DocumentoConId documentoConId = (DocumentoConId) entity.getDocumento();
		if (documentoConId != null) {

			model.setDocumento(documentoAssembler.toModel(documentoConId));
			model.add(linkTo(methodOn(DocumentoController.class).one(documentoConId.getId())).withRel("documento"));
		}

		return model;
	}

	public CollectionModel<PrestamoModel> toCollection(List<Prestamo> lista) {
		CollectionModel<PrestamoModel> collection = CollectionModel
				.of(lista.stream().map(this::toModel).collect(Collectors.toList()));
		collection.add(linkTo(methodOn(PrestamoController.class).all()).withRel("prestamos"));
		return collection;
	}
}