package es.mdef.proyecto_biblioteca_emad.REST;

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
import es.mdef.proyecto_biblioteca_emad.entidades.Documento;
import es.mdef.proyecto_biblioteca_emad.entidades.Documento.Categoria;
import es.mdef.proyecto_biblioteca_emad.entidades.Escrito;
import es.mdef.proyecto_biblioteca_emad.repositorios.DocumentoRepositorio;

@CrossOrigin(origins = "*",methods={RequestMethod.GET,RequestMethod.POST})
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	private final DocumentoRepositorio repositorio;
	private final DocumentoAssembler assembler;
	private final DocumentoListaAssembler listaAssembler;
	private final PrestamoListaAssembler rsListaAssembler;
	private final Logger log;

	DocumentoController(DocumentoRepositorio repositorio, DocumentoAssembler assembler,
			DocumentoListaAssembler listaAssembler, PrestamoListaAssembler rsListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.rsListaAssembler = rsListaAssembler;
		log = ProyectoBibliotecaEmadApplication.log;
	}

	@GetMapping("{id}")
	public DocumentoModel one(@PathVariable Long id) {
		Documento documento = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "documento"));
		// compruebo datos get Documento
		log.info("Recuperado el " + documento.getTitulo());
		log.info("Recuperado el " + documento.isDisponible());
		log.info("Recuperado el " + documento.getCategoria());
		return assembler.toModel(documento);
	}

	@GetMapping
	public CollectionModel<DocumentoModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@GetMapping("porTituloDocumento")
	public CollectionModel<DocumentoModel> documentoPorTituloDocumento(@RequestParam String tituloDocumento) {
		return listaAssembler.toCollection(repositorio.findDocumentoByTitulo(tituloDocumento));
	}

//   Metodo para recuperar todos los prestamos que tiene un documento	
//		@GetMapping("{id}/prestamos")
//		public CollectionModel<PrestamoListaModel> prestamosDeDocumento(@PathVariable Long id) {
//			Documento documento = repositorio.findById(id)
//					.orElseThrow(() -> new RegisterNotFoundException(id, "documento"));
//		    return rsListaAssembler.toCollection(documento.getPrestamos());
//		}

	@PostMapping
	public DocumentoModel add(@RequestBody DocumentoModel model) {
		Documento documento = repositorio.save(assembler.toEntity(model));
		log.info("Aniadido " + documento);
		return assembler.toModel(documento);
	}

	@PutMapping("{id}")
	public DocumentoModel edit(@PathVariable Long id, @RequestBody DocumentoModel model) {
		Documento documento = repositorio.findById(id).map(doc -> {

			if (model.getCategoria() == Categoria.escrito) {
				Escrito escrito = new Escrito();
				escrito.setTitulo(model.getTitulo());
				escrito.setAutor(model.getAutor());
				escrito.setEstanteria(model.getEstanteria());
				escrito.setDisponible(model.isDisponible());
				escrito.setSinopsis(model.getSinopsis());
				escrito.setFechaAlta(model.getFechaAlta());
				escrito.setISBN(model.getISBN());
				escrito.setNumpaginas(model.getNumPaginas());
				escrito.setTamano(model.getTamano());
				doc = escrito;
			} else if (model.getCategoria() == Categoria.audiovisual) {
				Audiovisual audiovisual = new Audiovisual();
				audiovisual.setTitulo(model.getTitulo());
				audiovisual.setAutor(model.getAutor());
				audiovisual.setEstanteria(model.getEstanteria());
				audiovisual.setDisponible(model.isDisponible());
				audiovisual.setSinopsis(model.getSinopsis());
				audiovisual.setFechaAlta(model.getFechaAlta());
				audiovisual.setISAN(model.getISAN());
				audiovisual.setDuracion(model.getDuracion());
				audiovisual.setTipo(model.getTipo());
				doc = audiovisual;
			}
			doc.setId(id);
			return repositorio.save(doc);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "documento"));
		log.info("Actualizado " + documento);
		return assembler.toModel(documento);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado documento " + id);
		repositorio.deleteById(id);
	}
	// Metodo personalizado: retorna las familias de las preguntas en las que ha
	// participado o hecho el usuario.
//		@GetMapping({"{id}/familias"})
//		public CollectionModel<FamiliaListaModel> familias(@PathVariable long id){
//			List<Pregunta>preguntas = repositorioPreguntas.findPreguntaByUsuario(id);
//			Set<Familia>familias = new HashSet<>();
//			for (Pregunta pregunta : preguntas) {
//				familias.add(pregunta.getFamilia());
//			}
//			return familiaListaAssembler.toCollection(new ArrayList<>(familias));
//		}	

}