package com.codeoscopic.spring.polizas.polizas_seguros_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository)
    {
        this.repository = repository;
    }

    public List<Cliente> findAll()
    {
        return repository.findAll();
    }

    public Cliente save(Cliente cliente)
    {
        return repository.save(cliente);
    }

    public Optional<Cliente> findById(Long id)
    {
        return repository.findById(id);
    }

    public void deleteById(Long id)
    {
        repository.deleteById(id);
    }
}
