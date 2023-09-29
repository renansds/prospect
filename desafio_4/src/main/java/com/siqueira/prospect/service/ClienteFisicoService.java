package com.siqueira.prospect.service;

import com.siqueira.prospect.dto.input.ClienteFisicoDTO;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.repository.ClienteFisicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteFisicoService {
    private final ClienteFisicoRepository repository;
    @Autowired
    public ClienteFisicoService(ClienteFisicoRepository repository) {
        this.repository = repository;
    }
    public ClienteFisico save(ClienteFisico cliente) {
        return repository.save(cliente);
    }
    public Optional<ClienteFisico> findById(UUID uuid) {
        return repository.findById(uuid);
    }
    public Page<ClienteFisico> findAll(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable);
    }
    public boolean existsById(UUID uuid) {
        return repository.existsById(uuid);
    }
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }
    public ClienteFisico fromDTO(ClienteFisicoDTO clienteFisicoDTO) {
        return new ClienteFisico(clienteFisicoDTO);
    }
    public ClienteFisico update(ClienteFisico obj) {
        Optional<ClienteFisico> optionalCliente = findById(obj.getUuid());
        updateData(optionalCliente.get(), obj);
        return repository.save(optionalCliente.get());
    }
    private void updateData(ClienteFisico newObj, ClienteFisico obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
        newObj.setMcc(obj.getMcc());
        newObj.setCpf(obj.getCpf());
    }

    public boolean delete(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    public Optional<ClienteFisico> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
