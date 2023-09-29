package com.siqueira.prospect.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siqueira.prospect.dto.input.ClienteFila;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilaAtendimentoSQSServiceTest {

    @InjectMocks
    private FilaAtendimentoSQSService filaAtendimentoSQSService;

    @Mock
    private SqsTemplate sqsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdicionarClienteNaFila() throws JsonProcessingException {
        ClienteFila cliente = new ClienteFila();
        cliente.setUuid(UUID.randomUUID());
        when(objectMapper.writeValueAsString(any(ClienteFila.class))).thenReturn("{}");
        filaAtendimentoSQSService.adicionarClienteNaFila(cliente);
        verify(sqsTemplate).send(any());
    }
}
