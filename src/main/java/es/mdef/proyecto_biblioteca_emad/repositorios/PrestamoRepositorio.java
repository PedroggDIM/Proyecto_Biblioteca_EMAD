package es.mdef.proyecto_biblioteca_emad.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.proyecto_biblioteca_emad.entidades.Prestamo;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {

}