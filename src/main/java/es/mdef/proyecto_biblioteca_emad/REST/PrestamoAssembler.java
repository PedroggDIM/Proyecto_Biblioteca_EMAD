package es.mdef.proyecto_biblioteca_emad.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;

@Component
public class PrestamoAssembler implements RepresentationModelAssembler<Prestamo, PrestamoModel>{

	@Override
	public PrestamoModel toModel(Prestamo entity) {
	    PrestamoModel model = new PrestamoModel();
	    model.setIdUsuario(entity.getIdUsuario());	  	
	    model.setFechaInicio(entity.getFechaInicio());	   
	    model.setFechaFin(entity.getFechaFin());
	    // obtengo en los prestamos el documento
	    model.setDocumento(entity.getDocumento());
	    
	    model.add(

				linkTo(methodOn(PrestamoController.class).one(entity.getId())).withSelfRel(),	
				linkTo(methodOn(DocumentoController.class).one(entity.getDocumento().getId())).withRel("documento")			
		        );
	    
		return model;
	}
	
	public Prestamo toEntity(PrestamoModel model) {
		Prestamo prestamo = new Prestamo();	
		prestamo.setIdUsuario(model.getIdUsuario());	
	    prestamo.setFechaInicio(model.getFechaInicio());
	    prestamo.setFechaFin(model.getFechaFin());
	    // obtengo en el prestamo el documento
	    prestamo.setDocumento(model.getDocumento());	    
		return prestamo;
	}

}