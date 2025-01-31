package org.example.libreriakamel;

import jakarta.validation.Valid;
import org.example.libreriakamel.DTO.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
@Validated
public class LibroImp {

    LibroRepositorio libroRepositorio;
    @Autowired
    public LibroImp(LibroRepositorio libroRepositorio) {
        this.libroRepositorio = libroRepositorio;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getLibro(){
        List<Libro> lista = this.libroRepositorio.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> getLibroJson(@PathVariable String isbn){
        Libro l = this.libroRepositorio.findById(isbn).get();
        return ResponseEntity.ok(l);
    }

    //POST --> INSERT
    @PostMapping("/libro")
    public ResponseEntity<Libro> addLibro(@Valid @RequestBody Libro libro){
        System.out.println("Entra aqui");
        Libro libroPersistido = this.libroRepositorio.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroForm(@RequestParam String isbn,
                                              @RequestParam String titulo,
                                              @RequestParam String autor){
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        this.libroRepositorio.save(libro);
        return ResponseEntity.created(null).body(libro);
    }


    //PUT --> UPDATE
    //falta actualizar ficheros
    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> updateLibro(@RequestBody Libro libro, @PathVariable String isbn) {
        Optional<Libro> libroExistenteOpt = libroRepositorio.findById(isbn); // Buscar el libro por ISBN
        if (libroExistenteOpt.isPresent()) {
            Libro libroExistente = libroExistenteOpt.get();

            // Actualizar los campos del libro existente
            libroExistente.setTitulo(libro.getTitulo());
            libroExistente.setAutor(libro.getAutor());
            // Aquí puedes agregar la actualización de otros campos según sea necesario

            // Guardar el libro actualizado
            Libro libroPersistido = libroRepositorio.save(libroExistente);
            return ResponseEntity.ok().body(libroPersistido);
        } else {
            // Si no se encuentra el libro, devolver un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    //DELETE
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn){
        libroRepositorio.deleteById(isbn);
        String mensaje = "libro con isbn: "+isbn+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }




}
