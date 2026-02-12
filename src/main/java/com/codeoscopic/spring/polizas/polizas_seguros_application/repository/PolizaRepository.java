package com.codeoscopic.spring.polizas.polizas_seguros_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Poliza;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {
    List<Poliza> findByClienteId(Long clienteId);
}
