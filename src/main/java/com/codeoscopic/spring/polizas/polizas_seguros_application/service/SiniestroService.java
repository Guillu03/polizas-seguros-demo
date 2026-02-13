package com.codeoscopic.spring.polizas.polizas_seguros_application.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.EvaluacionSiniestroDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.SiniestroSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoSiniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Siniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.PolizaRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.SiniestroRepository;

import jakarta.transaction.Transactional;

@Service
public class SiniestroService {
    private final SiniestroRepository siniestroRepository;
    private final PolizaRepository polizaRepository;

    public SiniestroService(SiniestroRepository siniestroRepository, PolizaRepository polizaRepository)
    {
        this.siniestroRepository = siniestroRepository;
        this.polizaRepository = polizaRepository;
    }

    @Transactional
    public Siniestro declararSiniestro(Long polizaId, SiniestroSolicitudDto siniestroDto)
    {
        var poliza = polizaRepository.findById(polizaId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Póliza no encontrada"));
        
        if(poliza.getEstado() != EstadoPoliza.VIGENTE)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pueden declarar siniestros en pólizas canceladas o anuladas.");
        }

        if(poliza.getFechaInicio().isAfter(siniestroDto.fechaSiniestro()) ||
            poliza.getFechaFin().isBefore(siniestroDto.fechaSiniestro()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha del siniestro está fuera de la cobertura de la póliza");
        }

        Siniestro siniestro = new Siniestro();
        siniestro.setPoliza(poliza);
        siniestro.setEstado(EstadoSiniestro.ABIERTO);
        siniestro.setDescripcion(siniestroDto.descripcion());
        siniestro.setFechaSiniestro(siniestroDto.fechaSiniestro());

        return siniestroRepository.save(siniestro);
    }

    public List<Siniestro> getSiniestrosPorPoliza(Long polizaId)
    {
        return siniestroRepository.findByPolizaId(polizaId);
    }

    @Transactional
    public Siniestro evaluarSiniestro(Long siniestroId, EvaluacionSiniestroDto estadoSiniestro)
    {
        Siniestro siniestro = siniestroRepository.findById(siniestroId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido encontrar un siniestro con ese ID."));
        
        if(siniestro.getEstado() == EstadoSiniestro.RECHAZADO)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede reabrir un siniestro rechazado");
        }
        
        siniestro.setEstado(estadoSiniestro.estado());
        siniestro.setDescripcion(estadoSiniestro.motivo());

        return siniestroRepository.save(siniestro);
    }
}
