package es.mdef.proyecto_biblioteca_emad.repositorios.spec;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class PrestamoSpecs {
	
	public static Specification<Prestamo> enuentraEntreFechas(Date fechaInicial, Date fechaFinal) {
		return new Specification<Prestamo>() {
			private static final long serialVersionUID = 6194698709625465812L;

			@Override
			public Predicate toPredicate(Root<Prestamo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Predicate miPredicado = criteriaBuilder.and(
						criteriaBuilder.greaterThanOrEqualTo(root.get(Prestamo.FECHA_INICIO), fechaInicial),
						criteriaBuilder.not(criteriaBuilder.equal(root.get(Prestamo.FECHA_FIN), fechaFinal)));

				return miPredicado;
			}
		};
	}
	
	

}
