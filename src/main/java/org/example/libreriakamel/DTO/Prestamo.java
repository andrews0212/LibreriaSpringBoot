package org.example.libreriakamel.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("prestamo-usuario")
    private org.example.libreriakamel.DTO.Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ejemplar_id", nullable = false)
    @JsonBackReference
    private Ejemplar ejemplar;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;

}