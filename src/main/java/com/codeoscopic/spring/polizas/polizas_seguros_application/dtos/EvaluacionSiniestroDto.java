package com.codeoscopic.spring.polizas.polizas_seguros_application.dtos;

import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoSiniestro;

public record EvaluacionSiniestroDto(
    EstadoSiniestro estado,
    String motivo
) {
    
}
