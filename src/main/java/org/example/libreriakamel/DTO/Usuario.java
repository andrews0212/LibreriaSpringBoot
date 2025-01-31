package org.example.libreriakamel.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", nullable = false, length = 15, unique = true)
    @NotEmpty(message = "El DNI no puede estar vacío")
    private String dni; // Se validará en el servicio.

    @Column(name = "nombre", nullable = false, length = 100)
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9 ]{1,100}$", message = "El nombre solo puede contener caracteres alfanuméricos")
    private String nombre;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Solo se aceptan correos de Gmail")
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 4, max = 12, message = "La contraseña debe tener entre 4 y 12 caracteres")
    private String password;

    @Lob
    @Column(name = "tipo", nullable = false)
    @NotEmpty(message = "El tipo no puede estar vacío")
    @Pattern(regexp = "^(normal|administrador)$", message = "El tipo debe ser: normal o administrador")
    private String tipo;

    @Column(name = "penalizacionHasta")
    private LocalDate penalizacionHasta;

    @JsonManagedReference("prestamo-usuario")
    @OneToMany(mappedBy = "usuario")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();
}
