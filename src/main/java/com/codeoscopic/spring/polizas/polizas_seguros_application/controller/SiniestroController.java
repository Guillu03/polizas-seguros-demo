package com.codeoscopic.spring.polizas.polizas_seguros_application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.SiniestroSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Siniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.SiniestroService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/siniestros")
public class SiniestroController {

    private final SiniestroService service;

    public SiniestroController(SiniestroService service)
    {
        this.service = service;
    }
    
    @PostMapping("/{polizaId}")
    public ResponseEntity<Siniestro> declararSiniestroEnPoliza(@PathVariable Long polizaId, @Valid @RequestBody SiniestroSolicitudDto siniestroDto) {
        return ResponseEntity.status(201).body(service.declararSiniestro(polizaId, siniestroDto));
    }
    
}
