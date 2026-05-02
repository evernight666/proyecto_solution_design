package com.proyecto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.sistema.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByCorreoAndPassword(String correo, String password);

}