package es.mdef.proyecto_biblioteca_emad.repositorios.impl;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import es.mdef.proyecto_biblioteca_emad.repositorios.PrestamoRepositorioMetPersonalizado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;


@Component
public class PrestamoRepositorioMetPersonalizadoImpl implements PrestamoRepositorioMetPersonalizado {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<PrestamoAgrupadoPorDocumento> prestamoGroupByDocumento(Specification<Prestamo> miEspecificacion, int maximoResultado) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PrestamoAgrupadoPorDocumento> query = criteriaBuilder.createQuery(PrestamoAgrupadoPorDocumento.class);
		Root<Prestamo> root = query.from(Prestamo.class);
		
		
		Expression<Long> cuentaDocumento = criteriaBuilder.count(root.get(Prestamo.ID));
		
		// Generamos la query
		query.multiselect(root.get(Prestamo.DOCUMENTO), cuentaDocumento);
		query.where(miEspecificacion.toPredicate(root, query, criteriaBuilder));
		query.groupBy(root.get(Prestamo.DOCUMENTO ));
		query.orderBy(criteriaBuilder.desc(cuentaDocumento));

		TypedQuery<PrestamoAgrupadoPorDocumento> ejecutaQuery = entityManager.createQuery(query);
		ejecutaQuery.setMaxResults(maximoResultado);
		List<PrestamoAgrupadoPorDocumento> lstPrestamoGroupByDocumento = ejecutaQuery.getResultList();

		return lstPrestamoGroupByDocumento;
	}
	
}