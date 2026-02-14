package com.codeoscopic.spring.polizas.polizas_seguros_application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.PolizaSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cobertura;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.ClienteRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.CoberturaRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.PolizaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PolizaService {
    private final PolizaRepository repository;
    private final ClienteRepository clienteRepository;
    private final CoberturaRepository coberturaRepository;


    public PolizaService(PolizaRepository repository, ClienteRepository clienteRepository, 
                         CoberturaRepository coberturaRepository)
    {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.coberturaRepository = coberturaRepository;
    }

    public Poliza crearPoliza(PolizaSolicitudDto dto)
    {
        log.info("Iniciando creación de póliza para el cliente ID: {}", dto.tomadorId());
        Cliente tomador = clienteRepository.findById(dto.tomadorId())
                        .orElseThrow(() -> new RuntimeException("El tomador con ID " + dto.tomadorId() + " no existe."));

        Cliente conductor = clienteRepository.findById(dto.tomadorId())
                        .orElseThrow(() -> new RuntimeException("El conductor con ID " + dto.tomadorId() + " no existe."));
        
        Cliente propietario = clienteRepository.findById(dto.tomadorId())
                        .orElseThrow(() -> new RuntimeException("El propietario con ID " + dto.tomadorId() + " no existe."));

        
        if(conductor.getCarnetConducir() == null || conductor.getCarnetConducir().getFechaExpedicion() == null)
        {
            throw new RuntimeException("El conductor no tiene un carnet de conducir registrado");
        }
        
        Poliza poliza = new Poliza();
        poliza.setTomador(tomador);
        poliza.setConductor(conductor);
        poliza.setPropietario(propietario);
        poliza.setMatricula(dto.matricula());
        poliza.setMarcaModelo(dto.marcaModelo());
        poliza.setCaballos(dto.caballos());
        poliza.setFechaInicio(dto.fechaInicio());

        poliza.setNumeroPoliza(java.util.UUID.randomUUID().toString());
        poliza.setEstado(EstadoPoliza.VIGENTE);
        poliza.setFechaFin(dto.fechaInicio().plusYears(1));

        List<Cobertura> coberturasSeleccionadas = coberturaRepository.findAllById(dto.coberturaIds());
        poliza.setCoberturas(new HashSet<>(coberturasSeleccionadas));

        BigDecimal precioFinal = calcularPrecio(dto.caballos(), conductor.getFechaNacimiento(), coberturasSeleccionadas);
        poliza.setPrecio(precioFinal);

        Poliza guardada = repository.save(poliza);
        log.info("Póliza creada con éxito. Número: {}", guardada.getNumeroPoliza());
        return guardada;
    }

    private BigDecimal calcularPrecio(Integer caballos, LocalDate fechaNacimiento, List<Cobertura> coberturasSeleccionadas)
    {
        BigDecimal precioTotal = new BigDecimal("300.00");

        int edad = java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if(edad < 25)
        {
            precioTotal = precioTotal.multiply(new BigDecimal("1.20"));
        }

        if(caballos > 150)
        {
            precioTotal = precioTotal.add(new BigDecimal("50.00"));
        }

        if(coberturasSeleccionadas != null && !coberturasSeleccionadas.isEmpty())
        {
            for(Cobertura cob : coberturasSeleccionadas)
            {
                precioTotal = precioTotal.add(cob.getPrecioBase());

                if(cob.getFactorRiesgo().compareTo(BigDecimal.ZERO) > 0)
                {
                    BigDecimal valorEstimadoCoche = new BigDecimal(caballos).multiply(new BigDecimal("10"));
                    BigDecimal recargoVariable = valorEstimadoCoche.multiply(cob.getFactorRiesgo());

                    precioTotal = precioTotal.add(recargoVariable);
                }
                    
            }
        }

        return precioTotal;
    }

    public List<Poliza> getPolizasCliente(Long clienteId)
    {
        log.info("Obteniendo polizas del cliente ID: {}", clienteId);
        return repository.findByTomadorId(clienteId);
    }

    public Optional<Poliza> getPolizaById(Long polizaId)
    {
        log.info("Obteniendo poliza por el ID: {}", polizaId);
        return repository.findById(polizaId);
    }

    public boolean cambiarEstadoPoliza(Long polizaId, EstadoPoliza estado)
    {
        log.info("Cambiando el estado de la poliza {} a {}", polizaId, estado);

        var poliza = repository.findById(polizaId)
            .orElseThrow(() -> new RuntimeException("El ID de la poliza no se ha encontrado"));

        if(poliza.getEstado().equals(estado))
        {
            return false;
        }

        poliza.setEstado(estado);
        repository.save(poliza);
        log.info("Poliza ID {} actualizada al estado {}", polizaId, estado);
        return true;
    }
}
