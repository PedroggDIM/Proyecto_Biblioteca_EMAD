package es.mdef.proyecto_biblioteca_emad.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad_libreria.Documento;

@RepositoryRestResource(path = "prestamos", collectionResourceRel = "prestamos")
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
	
	public List<Prestamo> findByDocumento(Documento documento);

}