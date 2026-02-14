package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoRecibo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "recibos")
public class Recibo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroRecibo;

    private String descripcion;

    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private LocalDate fechaEfecto;

    @Column(precision = 10, scale = 2)
    private BigDecimal importeNeto;

    @Column(precision = 10, scale = 2)
    private BigDecimal importeIVA;

    @Column(precision = 10, scale = 2)
    private BigDecimal importeTotal;

    @Enumerated(EnumType.STRING)
    private EstadoRecibo estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poliza_id", nullable = false)
    private Poliza poliza;
}
