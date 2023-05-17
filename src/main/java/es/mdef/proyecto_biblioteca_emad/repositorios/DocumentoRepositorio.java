package es.mdef.proyecto_biblioteca_emad.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import es.mdef.proyecto_biblioteca_emad.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
	List<Documento> findDocumentoByTitulo(String tituloDocumento);
}