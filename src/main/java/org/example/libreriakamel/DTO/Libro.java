package org.example.libreriakamel.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @Pattern(regexp = "\\d{13}", message = "El ISBN debe ser válido (ISBN-13: 13 dígitos)")
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    @NotEmpty(message = "El título no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,200}$", message = "El título solo puede contener caracteres alfanuméricos")
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    @NotEmpty(message = "El autor no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,100}$", message = "El autor solo puede contener caracteres alfanuméricos")
    private String autor;

    @JsonManagedReference
    @OneToMany(mappedBy = "isbn")
    private Set<Ejemplar> ejemplars = new LinkedHashSet<>();
}
