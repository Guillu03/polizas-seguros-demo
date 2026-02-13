package com.codeoscopic.spring.polizas.polizas_seguros_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.SiniestroSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoSiniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Siniestro;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.PolizaRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.SiniestroRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.SiniestroService;

@ExtendWith(MockitoExtension.class)
public class SiniestroServiceTest {
    @Mock 
    private SiniestroRepository siniestroRepository;

    @Mock
    private PolizaRepository polizaRepository;

    @InjectMocks
    private SiniestroService siniestroService;

    @Test
    void declararSiniestro_DeberiaGuardar_CuandoDatosSonValidos() {
        Long polizaId = 1L;
        LocalDate hoy = LocalDate.now();

        Poliza polizaValida = new Poliza();
        polizaValida.setId(polizaId);
        polizaValida.setEstado(EstadoPoliza.VIGENTE);
        polizaValida.setFechaInicio(hoy.minusMonths(6));
        polizaValida.setFechaFin(hoy.plusMonths(6));

        SiniestroSolicitudDto dto = new SiniestroSolicitudDto(hoy, "Golpe en garaje");

        when(polizaRepository.findById(polizaId)).thenReturn(Optional.of(polizaValida));
        when(siniestroRepository.save(any(Siniestro.class))).thenAnswer(i -> i.getArgument(0));

        Siniestro resultado = siniestroService.declararSiniestro(polizaId, dto);

        assertNotNull(resultado);
        assertEquals(EstadoSiniestro.ABIERTO, resultado.getEstado(), "El estado inicial debe ser ABIERTO");
        assertEquals("Golpe en garaje", resultado.getDescripcion());
        
        verify(siniestroRepository, times(1)).save(any(Siniestro.class));
    }

    @Test
    void declararSiniestro_DeberiaLanzarExcepcion_CuandoFechaFueraDeCobertura() {
        Long polizaId = 1L;
        LocalDate hoy = LocalDate.now();

        Poliza polizaAntigua = new Poliza();
        polizaAntigua.setId(polizaId);
        polizaAntigua.setEstado(EstadoPoliza.VIGENTE);
        polizaAntigua.setFechaInicio(hoy.minusYears(2)); 
        polizaAntigua.setFechaFin(hoy.minusYears(1));

        SiniestroSolicitudDto dto = new SiniestroSolicitudDto(hoy, "Intento de fraude");

        when(polizaRepository.findById(polizaId)).thenReturn(Optional.of(polizaAntigua));

        assertThrows(ResponseStatusException.class, () -> {
            siniestroService.declararSiniestro(polizaId, dto);
        });

        verify(siniestroRepository, never()).save(any(Siniestro.class));
    }

    @Test
    void declararSiniestro_DeberiaLanzarExcepcion_CuandoPolizaCancelada() {
        Long polizaId = 1L;
        Poliza polizaCancelada = new Poliza();
        polizaCancelada.setEstado(EstadoPoliza.CANCELADA);

        SiniestroSolicitudDto dto = new SiniestroSolicitudDto(LocalDate.now(), "Golpe");

        when(polizaRepository.findById(polizaId)).thenReturn(Optional.of(polizaCancelada));

        assertThrows(ResponseStatusException.class, () -> {
            siniestroService.declararSiniestro(polizaId, dto);
        });
    }
}
