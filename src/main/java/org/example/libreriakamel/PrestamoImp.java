package org.example.libreriakamel;

import org.example.libreriakamel.DTO.Prestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/prestamo")
public class PrestamoImp {
    PrestamosRepositorio repositorio;

    @Autowired
    public PrestamoImp(PrestamosRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerPrestamo() {
        List<Prestamo> prestamos = repositorio.findAll();
        return ResponseEntity.ok().body(prestamos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamoPorId(@PathVariable int id) {
        Prestamo prestamo = repositorio.findById(id).get();
        return ResponseEntity.ok().body(prestamo);
    }

    @PostMapping("/addPrestamo")
    public ResponseEntity<Prestamo> addPrestamo(@RequestBody Prestamo prestamo) {
        Prestamo prestamoPersistido = repositorio.save(prestamo);
        return ResponseEntity.ok().body(prestamoPersistido);
    }

}
