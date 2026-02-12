package com.codeoscopic.spring.polizas.polizas_seguros_application.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SiniestroSolicitudDto(
    @NotNull
    LocalDate fechaSiniestro,

    @NotBlank
    String descripcion
) {
    
}
