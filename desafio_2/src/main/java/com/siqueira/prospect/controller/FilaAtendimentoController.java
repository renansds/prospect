package com.siqueira.prospect.controller;


import com.siqueira.prospect.exceptions.QueueEmptyException;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.util.Fila;
import com.siqueira.prospect.util.FilaAtendimento;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Fila de Atendimento")
public class FilaAtendimentoController {

    private final FilaAtendimento filaAtendimento = FilaAtendimento.getInstance();

    @PostMapping(path = "/fila-atendimento/proximo-cliente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> proximoCliente() throws QueueEmptyException {
        Fila<Cliente> filaAtual = filaAtendimento.getFila();
        if (filaAtual.estaVazia()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        Cliente proximoCliente = filaAtual.remover();
        return ResponseEntity.ok(proximoCliente);
    }
}
