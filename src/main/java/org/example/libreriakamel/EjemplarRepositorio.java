package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepositorio extends JpaRepository<Ejemplar, Integer> {
}
