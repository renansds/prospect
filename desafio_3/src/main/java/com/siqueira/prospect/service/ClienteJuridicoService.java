package com.siqueira.prospect.service;

import com.siqueira.prospect.dto.input.ClienteJuridicoDTO;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.repository.ClienteJuridicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteJuridicoService {
    private final ClienteJuridicoRepository repository;
    @Autowired
    public ClienteJuridicoService(ClienteJuridicoRepository repository) {
        this.repository = repository;
    }
    public ClienteJuridico save(ClienteJuridico cliente) {
        return repository.save(cliente);
    }
    public Optional<ClienteJuridico> findById(UUID uuid) {
        return repository.findById(uuid);
    }
    public Page<ClienteJuridico> findAll(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable);
    }
    public boolean existsById(UUID uuid) {
        return repository.existsById(uuid);
    }
    public ClienteJuridico fromDTO(ClienteJuridicoDTO ClienteJuridicoDTO) {
        return new ClienteJuridico(ClienteJuridicoDTO);
    }
    public ClienteJuridico update(ClienteJuridico obj) {
        Optional<ClienteJuridico> optionalCliente = findById(obj.getUuid());
        updateData(optionalCliente.get(), obj);
        return repository.save(optionalCliente.get());
    }
    private void updateData(ClienteJuridico newObj, ClienteJuridico obj) {
        newObj.setUuid(obj.getUuid());
        newObj.setRazaoSocial(obj.getRazaoSocial());
        newObj.setCnpj(obj.getCnpj());
        newObj.setMcc(obj.getMcc());
        newObj.addContato(obj.getContato());
    }

    public boolean delete(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    public boolean existsByCnpj(String cnpj) {
        return repository.existsByCnpj(cnpj);
    }

    public Optional<ClienteJuridico> findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }
}
