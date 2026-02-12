package com.codeoscopic.spring.polizas.polizas_seguros_application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.PolizaSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.ClienteRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.PolizaRepository;

@Service
public class PolizaService {
    private final PolizaRepository repository;
    private final ClienteRepository clienteRepository;

    public PolizaService(PolizaRepository repository, ClienteRepository clienteRepository)
    {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public Poliza crearPoliza(PolizaSolicitudDto dto)
    {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                        .orElseThrow(() -> new RuntimeException("El cliente con ID " + dto.clienteId() + " no existe."));
        
        Poliza poliza = new Poliza();
        poliza.setCliente(cliente);
        poliza.setMatricula(dto.matricula());
        poliza.setMarcaModelo(dto.marcaModelo());
        poliza.setCaballos(dto.caballos());
        poliza.setFechaInicio(dto.fechaInicio());

        poliza.setNumeroPoliza(java.util.UUID.randomUUID().toString());
        poliza.setEstado(EstadoPoliza.VIGENTE);
        poliza.setFechaFin(dto.fechaInicio().plusYears(1));

        BigDecimal precioFinal = calcularPrecio(dto.caballos(), cliente.getFechaNacimiento());
        poliza.setPrecio(precioFinal);

        return repository.save(poliza);
    }

    private BigDecimal calcularPrecio(Integer caballos, LocalDate fechaNacimiento)
    {
        BigDecimal precioBase = new BigDecimal("300.00");

        int edad = java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if(edad < 25)
        {
            precioBase = precioBase.multiply(new BigDecimal("1.20"));
        }

        if(caballos > 150)
        {
            precioBase = precioBase.add(new BigDecimal("50.00"));
        }

        return precioBase;
    }

    public List<Poliza> getPolizasCliente(Long clienteId)
    {
        return repository.findByClienteId(clienteId);
    }

    public Optional<Poliza> getPolizaById(Long polizaId)
    {
        return repository.findById(polizaId);
    }

    public boolean cambiarEstadoPoliza(Long polizaId, EstadoPoliza estado)
    {
        var poliza = repository.findById(polizaId)
            .orElseThrow(() -> new RuntimeException("El ID de la poliza no se ha encontrado"));

        if(poliza.getEstado().equals(estado))
        {
            return false;
        }

        poliza.setEstado(estado);

        repository.save(poliza);
        return true;
    }
}
