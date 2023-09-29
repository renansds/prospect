package com.siqueira.prospect.controller;

import com.siqueira.prospect.exceptions.QueueEmptyException;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.util.Fila;
import com.siqueira.prospect.util.FilaAtendimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilaAtendimentoControllerTest {

    @InjectMocks
    private FilaAtendimentoController filaAtendimentoController;
    private FilaAtendimento filaAtendimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        filaAtendimento = FilaAtendimento.getInstance();
    }

    @Test
    void testProximoCliente() throws QueueEmptyException {
        Cliente cliente = new ClienteFisico("João Batista", "724.335.422-00", "augusto_nogueira@flex-erp.com");
        filaAtendimento.newFila(new Fila());
        filaAtendimento.getFila().adicionar(cliente);
        ResponseEntity<Object> response = filaAtendimentoController.proximoCliente();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Cliente clienteNaResposta = (Cliente) response.getBody();
        assertEquals(cliente, clienteNaResposta);
    }

    @Test
    void testProximoClienteFilaVazia() throws QueueEmptyException {
        filaAtendimento.newFila(new Fila<>());
        ResponseEntity<Object> response = filaAtendimentoController.proximoCliente();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
