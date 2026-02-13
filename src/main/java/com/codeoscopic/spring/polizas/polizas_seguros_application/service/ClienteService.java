package com.codeoscopic.spring.polizas.polizas_seguros_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository)
    {
        this.repository = repository;
    }

    public List<Cliente> findAll()
    {
        log.info("Obteniendo todos los clientes");
        return repository.findAll();
    }

    public Cliente save(Cliente cliente)
    {
        log.info("Guardando cliente {}", cliente.getNombre());
        return repository.save(cliente);
    }

    public Optional<Cliente> findById(Long id)
    {
        log.info("Obteniendo cliente por ID {}", id);
        return repository.findById(id);
    }

    public void deleteById(Long id)
    {
        log.info("Eliminando cliente por ID {}", id);
        repository.deleteById(id);
    }
}
