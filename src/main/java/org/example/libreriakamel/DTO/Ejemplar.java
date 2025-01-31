package org.example.libreriakamel.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "ejemplar")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    @JsonBackReference
    private Libro isbn;

    @Lob
    @Column(name = "estado", nullable = false)
    @NotEmpty(message = "El estado no puede estar vacío")
    @Pattern(regexp = "^(disponible|prestado|dañado)$", message = "El estado debe ser: disponible, prestado o dañado")
    private String estado;
}
