package es.mdef.proyecto_biblioteca_emad.repositorios;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.impl.PrestamoAgrupadoPorDocumento;

public interface PrestamoRepositorioMetPersonalizado {

	public List<PrestamoAgrupadoPorDocumento> prestamoGroupByDocumento(Specification<Prestamo> miEspecificacion, int maximoRerultado);
}
