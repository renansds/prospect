package com.siqueira.prospect.controller;

import com.siqueira.prospect.controller.FilaAtendimentoController;
import com.siqueira.prospect.dto.input.ClienteFila;
import com.siqueira.prospect.service.FilaAtendimentoSQSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FilaAtendimentoControllerTest {

    @InjectMocks
    private FilaAtendimentoController filaAtendimentoController;

    @Mock
    private FilaAtendimentoSQSService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProximoCliente() {
        ClienteFila clienteFila = new ClienteFila();
        clienteFila.setNome("João Batista");
        Message<ClienteFila> mensagem = new GenericMessage<>(clienteFila);
        when(service.proximo()).thenReturn(Optional.of(mensagem));
        ResponseEntity<Object> response = filaAtendimentoController.proximoCliente();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ClienteFila clienteNaResposta = (ClienteFila) response.getBody();
        assertEquals("João Batista", clienteNaResposta.getNome());
    }

    @Test
    void testProximoClienteFilaVazia() {
        when(service.proximo()).thenReturn(Optional.empty());
        ResponseEntity<Object> response = filaAtendimentoController.proximoCliente();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
