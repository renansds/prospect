package com.siqueira.prospect.repository;

import com.siqueira.prospect.model.ClienteJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ClienteJuridicoRepository extends JpaRepository<ClienteJuridico, UUID> {
    boolean existsByCnpj(String cnpj);
    Optional<ClienteJuridico> findByCnpj(String cnpj);
}