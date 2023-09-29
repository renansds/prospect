package com.siqueira.prospect.controller;

import com.siqueira.prospect.dto.input.ClienteJuridicoDTO;
import com.siqueira.prospect.dto.output.ClienteJuridicoResponseDTO;
import com.siqueira.prospect.exceptions.CompanyAlreadyExistsException;
import com.siqueira.prospect.exceptions.CompanyNotFoundException;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.service.ClienteJuridicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cliente/juridico")
@Tag(name = "Cadastro jurídico")
public class ClienteJuridicoController {
    private static final String COMPANY_EXISTS = "Empresa já cadastrada.";
    private static final String COMPANY_NOT_FOUND = "Não foi possível concluir a operação, pois a empresa não está " +
            "registrado em nosso sistema.";

    @Autowired
    private ClienteJuridicoService service;

    @GetMapping(path = "{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteJuridicoResponseDTO> find(@PathVariable String cnpj) {
        Optional<ClienteJuridico> optionalCliente = service.findByCnpj(cnpj);
        if (optionalCliente.isPresent()) {
            return new ResponseEntity<>(new ClienteJuridicoResponseDTO(optionalCliente.get()), HttpStatus.OK);
        }
        throw new CompanyNotFoundException(COMPANY_NOT_FOUND);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteJuridicoResponseDTO> save(@Valid @RequestBody ClienteJuridicoDTO dto,
                                                           HttpServletResponse response) {
        if (service.existsByCnpj(dto.getCnpj())) {
            throw new CompanyAlreadyExistsException(COMPANY_EXISTS);
        }
        ClienteJuridico output = service.save(new ClienteJuridico(dto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(output.getCnpj())
                .toUri();
        response.setHeader("Location", location.toString());
        ClienteJuridicoResponseDTO responseDTO = new ClienteJuridicoResponseDTO(output, location);
        return ResponseEntity.created(location).body(responseDTO);
    }
    @PutMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteJuridicoResponseDTO> update(@Valid @RequestBody ClienteJuridicoDTO dto,
                                                           @PathVariable UUID uuid) {
        if (service.existsById(uuid)) {
            ClienteJuridico obj = service.fromDTO(dto);
            obj.setUuid(uuid);
            service.update(obj);
            return ResponseEntity.noContent().build();
        }
        throw new CompanyNotFoundException(COMPANY_NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        boolean excluirCliente = service.delete(uuid);
        if (excluirCliente) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
