package com.siqueira.prospect.listener;

import com.siqueira.prospect.listeners.ClienteEntityListener;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.service.FilaAtendimentoSQSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class ClienteEntityListenerTest {
    @InjectMocks
    private ClienteEntityListener clienteJuridicoEntityListener;
    @Mock
    private FilaAtendimentoSQSService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAfterInsert() {
        ClienteJuridico cliente = new ClienteJuridico();
        clienteJuridicoEntityListener.afterInsert(cliente);
        verify(service, times(1)).adicionarClienteNaFila(any());
    }

    @Test
    public void testAfterUpdate() {
        ClienteJuridico cliente1 = new ClienteJuridico();
        ClienteJuridico cliente2 = new ClienteJuridico();
        ClienteJuridico cliente3 = new ClienteJuridico();
        cliente1.setUuid(UUID.randomUUID());
        cliente2.setUuid(UUID.randomUUID());
        cliente3.setUuid(UUID.randomUUID());
        clienteJuridicoEntityListener.afterInsert(cliente1);
        clienteJuridicoEntityListener.afterInsert(cliente2);
        clienteJuridicoEntityListener.afterInsert(cliente3);
        clienteJuridicoEntityListener.afterUpdate(cliente2);
        verify(service, times(1)).atualizarClienteNaFila(any());
    }
}
