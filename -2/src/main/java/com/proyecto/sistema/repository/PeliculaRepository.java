package com.proyecto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.sistema.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}