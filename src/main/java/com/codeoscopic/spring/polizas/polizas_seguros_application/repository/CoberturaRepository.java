package com.codeoscopic.spring.polizas.polizas_seguros_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cobertura;

@Repository
public interface CoberturaRepository extends JpaRepository<Cobertura, Long> {
    
}
