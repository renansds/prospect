package com.siqueira.prospect.controller;


import com.siqueira.prospect.dto.input.ClienteFila;
import com.siqueira.prospect.service.FilaAtendimentoSQSService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Fila de Atendimento")
public class FilaAtendimentoController {
    @Autowired
    private FilaAtendimentoSQSService service;

    @PostMapping(path = "/fila-atendimento/proximo-cliente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> proximoCliente() {
        Optional<Message<ClienteFila>> proximo = service.proximo();
        if (!proximo.isPresent()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(proximo.get().getPayload());
    }
}
