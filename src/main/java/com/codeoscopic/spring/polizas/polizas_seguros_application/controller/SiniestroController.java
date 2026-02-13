package com.codeoscopic.spring.polizas.polizas_seguros_application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.EvaluacionSiniestroDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.SiniestroSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Siniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.SiniestroService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/poliza/{polizaId}")
    public ResponseEntity<List<Siniestro>> getSiniestrosPorPoliza(@PathVariable Long polizaId) {
        return ResponseEntity.ok(service.getSiniestrosPorPoliza(polizaId));
    }

    @PutMapping("/{siniestroId}/estado")
    public ResponseEntity<Siniestro> evaluarSiniestro(@PathVariable Long siniestroId, @RequestBody EvaluacionSiniestroDto estadoSiniestro)
    {
        return ResponseEntity.ok(service.evaluarSiniestro(siniestroId, estadoSiniestro));
    }

}
