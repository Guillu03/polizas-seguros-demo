package com.codeoscopic.spring.polizas.polizas_seguros_application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.PolizaSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.PolizaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/polizas")
public class PolizaController {
    private final PolizaService service;

    public PolizaController(PolizaService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Poliza> crearPoliza(@Valid @RequestBody PolizaSolicitudDto dto)
    {
        return ResponseEntity.status(201).body(service.crearPoliza(dto));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Poliza>> getAllPolizasByClienteId(@PathVariable Long clienteId){
        return ResponseEntity.status(200).body(service.getPolizasCliente(clienteId));
    }

    @GetMapping("/{polizaId}")
    public ResponseEntity<Poliza> getPolizaById(@PathVariable Long polizaId) {
        return service.getPolizaById(polizaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{polizaId}/estado")
    public ResponseEntity<Void> actualizarEstadoPoliza(@PathVariable Long polizaId, @RequestBody EstadoPoliza estado) {
        service.cambiarEstadoPoliza(polizaId, estado);
        return ResponseEntity.ok().build();
    }
    
}
