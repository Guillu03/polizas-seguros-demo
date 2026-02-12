package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import java.time.LocalDate;

import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoSiniestro;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Data
@Table(name = "siniestros")
public class Siniestro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotNull
    public String descripcion;

    @Column(nullable = false)
    @NotNull
    @PastOrPresent(message = "El siniestro no puede ocurrir en el futuro")
    public LocalDate fechaSiniestro;

    @Enumerated(EnumType.STRING)
    public EstadoSiniestro estado;

    @ManyToOne
    @JoinColumn(name = "poliza_id", nullable = false)
    public Poliza poliza;
}
