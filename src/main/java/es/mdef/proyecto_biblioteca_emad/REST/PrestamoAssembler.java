package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.DocumentoRepositorio;

@Component
public class PrestamoAssembler implements RepresentationModelAssembler<Prestamo, PrestamoModel> {
	@Autowired
	private DocumentoRepositorio repositorio;

	@Autowired
	private DocumentoAssembler documentoAssembler;

	@Override
	public PrestamoModel toModel(Prestamo entity) {
		PrestamoModel model = new PrestamoModel();
		model.setId(entity.getId());
		model.setIdUsuario(entity.getIdUsuario());
		model.setFechaInicio(entity.getFechaInicio());
		model.setFechaFin(entity.getFechaFin());
		model.setDevuelto(entity.isDevuelto());

		model.add(linkTo(methodOn(PrestamoController.class).one(entity.getId())).withSelfRel());

		DocumentoConId documentoConId = (DocumentoConId) entity.getDocumento();
		if (documentoConId != null) {

			model.setDocumento(documentoAssembler.toModel(documentoConId));

			model.add(linkTo(methodOn(DocumentoController.class).one(documentoConId.getId())).withRel("documento"));
		}

		return model;
	}

	public Prestamo toEntity(PrestamoModel model) {

		Prestamo prestamo = new Prestamo();
		prestamo.setIdUsuario(model.getIdUsuario());
		prestamo.setFechaInicio(model.getFechaInicio());
		prestamo.setFechaFin(model.getFechaFin());
		Link linkdoc = model.getLink("documento").get();
		String[] aux = linkdoc.getHref().split("/");
		long docId = Long.parseLong(aux[aux.length - 1]);
		prestamo.setDocumento(repositorio.findById(docId).get());
        prestamo.setDevuelto(model.isDevuelto());
		return prestamo;
	}

}
