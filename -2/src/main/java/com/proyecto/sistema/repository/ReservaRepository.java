package com.proyecto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.sistema.model.Reserva;

import java.util.List;

@Repository // 👈 IMPORTANTE (aunque a veces es opcional)
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByAsientoAndHorario(int asiento, String horario);

    List<Reserva> findByPeliculaAndHorario(String pelicula, String horario);
}