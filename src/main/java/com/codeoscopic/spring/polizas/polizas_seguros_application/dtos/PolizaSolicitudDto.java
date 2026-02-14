package com.codeoscopic.spring.polizas.polizas_seguros_application.dtos;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.*;

public record PolizaSolicitudDto(
    @NotNull(message = "El ID del tomador es obligatorio")
    Long tomadorId,

    @NotNull(message = "El ID del conductor es obligatorio")
    Long conductorId,

    @NotNull(message = "El ID del propietario es obligatorio")
    Long propietarioId,

    @NotBlank(message = "La matrícula es obligatoria")
    String matricula,

    String marcaModelo,

    @Min(value = 50, message = "La potencia mínima son 50cv")
    @NotNull
    Integer caballos,

    @NotNull(message = "La fecha de inicio es obligatoria")
    LocalDate fechaInicio,

    List<Long> coberturaIds
)
{    
}
