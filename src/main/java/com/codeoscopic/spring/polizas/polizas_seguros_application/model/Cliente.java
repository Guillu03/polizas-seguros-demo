package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity // Inidcamos a Spring que es una tabla SQL
@Data  // Para generar getters, setters, toString etc (se encarga Lombok)
@Table(name = "clientes") // Indicamos el nombre de la tabla
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Como en .NET indicar que es autoincremental con EF Core
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;

    private String nombre;
    private String apellido;
    private String direccion;

    private LocalDate fechaNacimiento;
}
