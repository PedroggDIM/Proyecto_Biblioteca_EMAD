package es.mdef.proyecto_biblioteca_emad.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.proyecto_biblioteca_emad.entidades.DocumentoConId;

@RepositoryRestResource(path = "documentos", collectionResourceRel = "documentos")
public interface DocumentoRepositorio extends JpaRepository<DocumentoConId, Long> {
	List<DocumentoConId> findDocumentoByTitulo(String tituloDocumento);
}