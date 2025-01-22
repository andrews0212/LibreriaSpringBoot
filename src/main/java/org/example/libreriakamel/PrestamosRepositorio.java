package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamosRepositorio extends JpaRepository<Prestamo, Integer> {
}
