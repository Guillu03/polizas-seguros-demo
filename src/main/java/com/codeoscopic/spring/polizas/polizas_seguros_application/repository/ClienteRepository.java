package com.codeoscopic.spring.polizas.polizas_seguros_application.repository;

import com.codeoscopic.spring.polizas.polizas_seguros_application.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
