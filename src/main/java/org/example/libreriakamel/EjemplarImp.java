package org.example.libreriakamel;

import jakarta.validation.Valid;
import org.example.libreriakamel.DTO.Ejemplar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ejemplar")
@Validated
public class EjemplarImp {

    private LibroRepositorio libroRepositorio;
    private EjemplarRepositorio ejemplarRepositorio;
    @Autowired
    public EjemplarImp(EjemplarRepositorio ejemplarRepositorio, LibroRepositorio libroRepositorio) {
        this.ejemplarRepositorio = ejemplarRepositorio;
        this.libroRepositorio = libroRepositorio;
    }

    @GetMapping
    public ResponseEntity<List<Ejemplar>> getEjemplar(){
        List<Ejemplar> lista = this.ejemplarRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> getEjemplarJson(@PathVariable int id){
        Ejemplar l = this.ejemplarRepositorio.findById(id).get();
        return ResponseEntity.ok(l);
    }

    //POST --> INSERT
    @Transactional
    @PostMapping("/addEjemplar")
    public ResponseEntity<Ejemplar> addEjemplar(@Valid @RequestBody Ejemplar ejemplar){
        System.out.println("Entra aqui");
        try {
            Ejemplar e = this.ejemplarRepositorio.save(ejemplar);
            return ResponseEntity.ok().body(e);
        } catch (Exception ex) {
            ex.printStackTrace();  // Esto te mostrará detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/EjemplarForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ejemplar> addEjemplarForm(
                                              @RequestParam String isbn,
                                              @RequestParam String estado)
                                              {
        Ejemplar Ejemplar = new Ejemplar();
        Ejemplar.setEstado(estado);
        Ejemplar.setIsbn(libroRepositorio.findById(isbn).get());
        this.ejemplarRepositorio.save(Ejemplar);
        return ResponseEntity.created(null).body(Ejemplar);
    }

    //PUT --> UPDATE
    //falta actualizar ficheros
    @PutMapping("/{id}")
    public ResponseEntity<Ejemplar> updateEjemplar(@RequestBody Ejemplar ejemplar, @PathVariable int id) {
        // Buscar el Ejemplar por ID
        Optional<Ejemplar> ejemplarExistenteOpt = ejemplarRepositorio.findById(id);
        if (ejemplarExistenteOpt.isPresent()) {
            Ejemplar ejemplarExistente = ejemplarExistenteOpt.get();

            // Actualizar los campos del Ejemplar existente
            ejemplarExistente.setIsbn(ejemplar.getIsbn()); // Si el isbn es un objeto Libro, ya debe estar configurado
            ejemplarExistente.setEstado(ejemplar.getEstado());

            // Si hay otros campos en Ejemplar, agrégales actualizaciones aquí

            // Guardar el Ejemplar actualizado
            Ejemplar ejemplarPersistido = ejemplarRepositorio.save(ejemplarExistente);

            // Devolver el Ejemplar actualizado
            return ResponseEntity.ok().body(ejemplarPersistido);
        } else {
            // Si el Ejemplar no se encuentra, devolver un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable int id){
        ejemplarRepositorio.deleteById(id);
        String mensaje = "Ejemplar con id: "+id+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }




}
