package com.codeoscopic.spring.polizas.polizas_seguros_application.model;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Con esto digo que CarnetConducir forma parte de otra tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarnetConducir {

    private String numeroCarnet;

    private String tipo; // B, A2, C...

    private LocalDate fechaExpedicion;

    private LocalDate fechaVencimiento;

    private Integer puntos;
}
