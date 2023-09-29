package com.siqueira.prospect.repository;

import com.siqueira.prospect.model.ClienteFisico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ClienteFisicoRepository extends JpaRepository<ClienteFisico, UUID> {
    boolean existsByCpf(String cpf);
    Optional<ClienteFisico> findByCpf(String cpf);
}