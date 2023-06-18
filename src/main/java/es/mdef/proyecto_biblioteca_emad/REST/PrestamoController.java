package es.mdef.proyecto_biblioteca_emad.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.proyecto_biblioteca_emad.ProyectoBibliotecaEmadApplication;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.PrestamoRepositorio;
import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.repositorios.DocumentoRepositorio;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
	private final DocumentoRepositorio docRepositorio;
	private final PrestamoRepositorio repositorio;
	private final PrestamoAssembler assembler;
	private final PrestamoListaAssembler listaAssembler;
	private final Logger log;

	PrestamoController(DocumentoRepositorio docRepositorio, PrestamoRepositorio repositorio,
			PrestamoAssembler assembler, PrestamoListaAssembler listaAssembler) {
		this.docRepositorio = docRepositorio;
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.log = ProyectoBibliotecaEmadApplication.log;

	}

	@GetMapping("{id}")
	public PrestamoModel one(@PathVariable Long id) {
		Prestamo prestamo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "prestamo"));
		log.info("Recuperado " + prestamo);
		return assembler.toModel(prestamo);
	}

	@GetMapping
	public CollectionModel<PrestamoModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public PrestamoModel add(@RequestBody PrestamoModel model) {
		System.out.println(model);

		PrestamoModel result = null;
		String mensaje;
		Prestamo prestamo = assembler.toEntity(model);

		DocumentoConId doc = (DocumentoConId) prestamo.getDocumento();
		int copias = doc.getNumCopias();
		if (copias > 0) {
			copias--;
			doc.setNumCopias(copias);
			if (copias == 0) {
				doc.setDisponible(false);
			}
			docRepositorio.save(doc);

			Prestamo newPrestamo = repositorio.save(prestamo);
			result = assembler.toModel(newPrestamo);

			mensaje = "Aniadido " + newPrestamo.toString();
		} else {
			mensaje = "El documento " + doc.getId() + " no se puede prestar porque no hay copias disponibles";
		}

		log.info(mensaje);
		return result;
	}

	@PutMapping("{id}")
	public PrestamoModel edit(@PathVariable Long id, @RequestBody PrestamoModel model) {
	    Prestamo prestamo = repositorio.findById(id).map(pre -> {
	        pre.setIdUsuario(model.getIdUsuario());
	        pre.setFechaInicio(model.getFechaInicio());
	        pre.setFechaFin(model.getFechaFin());
	        pre.setDevuelto(model.isDevuelto());

	        Link linkdoc = model.getLink("documento").get();
	        String[] aux = linkdoc.getHref().split("/");
	        long docId = Long.parseLong(aux[aux.length - 1]);
	        pre.setDocumento(docRepositorio.findById(docId).get());

	        if (pre.isDevuelto()) {
	            DocumentoConId documento = (DocumentoConId) pre.getDocumento();
	            int copias = documento.getNumCopias();
	            copias++;
	            documento.setNumCopias(copias);
	            documento.setDisponible(true);

	            docRepositorio.save(documento);
	        }

	        return repositorio.save(pre);
	    }).orElseThrow(() -> new RegisterNotFoundException(id, "prestamo"));

	    log.info("Actualizado " + prestamo);
	    return assembler.toModel(prestamo);
	}
	
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado prestamo " + id);
		repositorio.deleteById(id);

	}

}
