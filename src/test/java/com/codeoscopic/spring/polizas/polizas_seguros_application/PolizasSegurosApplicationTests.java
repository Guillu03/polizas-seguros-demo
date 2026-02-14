package com.codeoscopic.spring.polizas.polizas_seguros_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.codeoscopic.spring.polizas.polizas_seguros_application.dtos.PolizaSolicitudDto;
import com.codeoscopic.spring.polizas.polizas_seguros_application.enums.EstadoPoliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.ClienteRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.repository.PolizaRepository;
import com.codeoscopic.spring.polizas.polizas_seguros_application.service.PolizaService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PolizasSegurosApplicationTests {

	@Mock
    private PolizaRepository polizaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PolizaService polizaService;

    @Test
    void crearPoliza_DeberiaCalcularPrecioCorrecto_ParaJovenYCochePotente() {
        Long clienteId = 1L;
        // TODO: Adaptar la prueba de calcular precio correcto
        Cliente clienteJoven = new Cliente();
        clienteJoven.setId(clienteId);
        clienteJoven.setFechaNacimiento(LocalDate.now().minusYears(20));

        PolizaSolicitudDto dto = new PolizaSolicitudDto(
            clienteId, 
            2L,
            3L,
            "1234-XYZ", 
            "Ferrari", 
            200,
            LocalDate.now(),
            new ArrayList<>()
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteJoven));
        
        when(polizaRepository.save(any(Poliza.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Poliza resultado = polizaService.crearPoliza(dto);

        assertNotNull(resultado);
        
        BigDecimal precioEsperado = new BigDecimal("410.00");
        assertEquals(0, precioEsperado.compareTo(resultado.getPrecio()), "El precio debería ser 410");

        assertEquals(EstadoPoliza.VIGENTE, resultado.getEstado());
        assertEquals(resultado.getFechaInicio().plusYears(1), resultado.getFechaFin());

        verify(polizaRepository, times(1)).save(any(Poliza.class));
    }

}
