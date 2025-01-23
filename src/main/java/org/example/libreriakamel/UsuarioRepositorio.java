package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
}
