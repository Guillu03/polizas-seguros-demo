package com.codeoscopic.spring.polizas.polizas_seguros_application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(Long id)
    {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente)
    {
        Cliente nuevo = service.save(cliente);
        return ResponseEntity.status(201).body(nuevo);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if(service.findById(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
