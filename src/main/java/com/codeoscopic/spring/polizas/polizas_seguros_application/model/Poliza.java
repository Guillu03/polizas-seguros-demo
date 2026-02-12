package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
@Table(name="polizas")
public class Poliza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El número de poliza es obligatorio")
    private String numeroPoliza;

    @NotNull(message = "La fecha de inicio es obligatorio")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoPoliza estado;

    @NotBlank(message = "La matrícula no puede estar vacía")
    private String matricula;

    private String marcaModelo;

    @Min(value = 50, message = "La potencia mínima son 50cv")
    private Integer caballos;

    @NotNull
    @PositiveOrZero
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "Una póliza debe pertenecer a un cliente")
    private Cliente cliente;

}
