package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "coberturas")
public class Cobertura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precioBase;

    private BigDecimal factorRiesgo;
}
