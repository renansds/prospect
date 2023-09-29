package com.siqueira.prospect.controller;


import com.siqueira.prospect.dto.input.ClienteFisicoDTO;
import com.siqueira.prospect.dto.output.ClienteFisicoResponseDTO;
import com.siqueira.prospect.exceptions.CustomerAlreadyExistsException;
import com.siqueira.prospect.exceptions.CustomerNotFoundException;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.service.ClienteFisicoService;
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
@RequestMapping("/cliente/fisico")
@Tag(name = "Cadastro físico")
public class ClienteController {

    private static final String CUSTOMER_NOT_FOUND = "Não foi possível concluir a operação, pois o cliente não está registrado em nosso sistema.";
    private static final String CUSTOMER_EXISTS = "Cliente já cadastrado";

    @Autowired
    private ClienteFisicoService clienteFisicoService;

    @GetMapping(path = "{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteFisicoResponseDTO> find(@PathVariable String cpf) {
        Optional<ClienteFisico> optionalCliente = clienteFisicoService.findByCpf(cpf);
        if (optionalCliente.isPresent()) {
            return new ResponseEntity<>(new ClienteFisicoResponseDTO(optionalCliente.get()), HttpStatus.OK);
        }
        throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteFisicoResponseDTO> save(@Valid @RequestBody ClienteFisicoDTO clienteFisicoDTO,
                                                         HttpServletResponse response) {
        if (clienteFisicoService.existsByCpf(clienteFisicoDTO.getCpf())) {
            throw new CustomerAlreadyExistsException(CUSTOMER_EXISTS);
        }
        ClienteFisico output = (ClienteFisico) clienteFisicoService.save(new ClienteFisico(clienteFisicoDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(output.getCpf())
                .toUri();
        response.setHeader("Location", location.toString());
        ClienteFisicoResponseDTO responseDTO = new ClienteFisicoResponseDTO(output, location);
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteFisicoResponseDTO> update(@Valid @RequestBody ClienteFisicoDTO clienteFisicoDTO,
                                                           @PathVariable UUID uuid) {
        if (clienteFisicoService.existsById(uuid)) {
            ClienteFisico obj = clienteFisicoService.fromDTO(clienteFisicoDTO);
            obj.setUuid(uuid);
            clienteFisicoService.update(obj);
            return ResponseEntity.noContent().build();
        }
        throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        boolean excluirCliente = clienteFisicoService.delete(uuid);
        if (excluirCliente) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
