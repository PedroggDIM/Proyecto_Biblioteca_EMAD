package es.mdef.proyecto_biblioteca_emad.REST;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.proyecto_biblioteca_emad.ProyectoBibliotecaEmadApplication;
import es.mdef.proyecto_biblioteca_emad.entidades.Audiovisual;
import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;
import es.mdef.proyecto_biblioteca_emad.entidades.Escrito;
import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.DocumentoRepositorio;
import es.mdef.proyecto_biblioteca_emad.repositorios.PrestamoRepositorio;
import es.mdef.proyecto_biblioteca_emad_libreria.Categoria;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	private final PrestamoRepositorio prestamoRepositorio;
	private final DocumentoRepositorio repositorio;
	private final DocumentoAssembler assembler;
	private final DocumentoListaAssembler listaAssembler;
	private final Logger log;

	DocumentoController(DocumentoRepositorio repositorio, DocumentoAssembler assembler,
			DocumentoListaAssembler listaAssembler, PrestamoRepositorio prestamoRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.prestamoRepositorio = prestamoRepositorio;
		log = ProyectoBibliotecaEmadApplication.log;
	}

	@GetMapping("{id}")
	public DocumentoModel one(@PathVariable Long id) {
		DocumentoConId documentoConId = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "documento"));
		// compruebo datos get Documento
		log.info("Recuperado el " + documentoConId.getTitulo());
		log.info("Recuperado el " + documentoConId.isDisponible());
		log.info("Recuperado el " + documentoConId.getCategoria());
		return assembler.toModel(documentoConId);
	}

	@GetMapping
	public CollectionModel<DocumentoModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@GetMapping("porTituloDocumento")
	public CollectionModel<DocumentoModel> documentoPorTituloDocumento(@RequestParam String tituloDocumento) {
		return listaAssembler.toCollection(repositorio.findDocumentoByTitulo(tituloDocumento));
	}

	@PostMapping
	public DocumentoModel add(@RequestBody DocumentoModel model) {

		DocumentoConId documentoConId = repositorio.save(assembler.toEntity(model));
		log.info("Aniadido " + documentoConId);
		return assembler.toModel(documentoConId);
	}

	@PutMapping("{id}")
	public DocumentoModel edit(@PathVariable Long id, @RequestBody DocumentoModel model) {
		DocumentoConId documentoConId = repositorio.findById(id).map(doc -> {

			if (doc instanceof Escrito) {
				doc.setNumCopias(model.getNumCopias());
				doc.setTitulo(model.getTitulo());
				doc.setAutor(model.getAutor());
				doc.setEstanteria(model.getEstanteria());
				doc.setDisponible(model.isDisponible());
				doc.setSinopsis(model.getSinopsis());
				doc.setFechaAlta(model.getFechaAlta());
				((Escrito) doc).setISBN(model.getISBN());
				((Escrito) doc).setNumpaginas(model.getNumPaginas());
				((Escrito) doc).setTamano(model.getTamano());
			} else if (doc instanceof Audiovisual) {
				doc.setNumCopias(model.getNumCopias());
				doc.setTitulo(model.getTitulo());
				doc.setAutor(model.getAutor());
				doc.setEstanteria(model.getEstanteria());
				doc.setDisponible(model.isDisponible());
				doc.setSinopsis(model.getSinopsis());
				doc.setFechaAlta(model.getFechaAlta());
				((Audiovisual) doc).setISAN(model.getISAN());
				((Audiovisual) doc).setDuracion(model.getDuracion());
				((Audiovisual) doc).setTipo(model.getTipo());
			}
			return repositorio.save(doc);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "documento"));
		log.info("Actualizado " + documentoConId);
		return assembler.toModel(documentoConId);
	}

	@DeleteMapping("{id}")
	public boolean delete(@PathVariable Long id) {
		log.info("Borrar documento " + id);
		boolean eliminado = false;
		DocumentoConId documentoConId = repositorio.findById(id).get();
		List<Prestamo> listaPrestamo = prestamoRepositorio.findByDocumento(documentoConId);
		if (listaPrestamo.isEmpty()) {
			eliminado = true;
			repositorio.deleteById(id);
		} else {
			System.out.println("No se puede eliminar porque el libro ha sido prestado.");
		}
		return eliminado;
	}

}