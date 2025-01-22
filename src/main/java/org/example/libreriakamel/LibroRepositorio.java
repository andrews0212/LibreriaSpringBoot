package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
}
