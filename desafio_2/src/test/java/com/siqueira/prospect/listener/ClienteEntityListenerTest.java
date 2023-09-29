package com.siqueira.prospect.listener;

import com.siqueira.prospect.listeners.ClienteEntityListener;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.util.Fila;
import com.siqueira.prospect.util.FilaAtendimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteEntityListenerTest {
    private ClienteEntityListener clienteEntityListener;
    private FilaAtendimento filaAtendimento;

    @BeforeEach
    public void setUp() {
        clienteEntityListener = new ClienteEntityListener();
        filaAtendimento = FilaAtendimento.getInstance();
        filaAtendimento.newFila(new Fila());
    }

    @Test
    public void testAfterInsert() {
        Cliente cliente = new ClienteJuridico();
        clienteEntityListener.afterInsert(cliente);
        assertNotNull(filaAtendimento.getFila());
        assertEquals(1, filaAtendimento.getFila().tamanho());
        assertFalse(filaAtendimento.getFila().estaVazia());
    }

    @Test
    public void testAfterUpdate() {
        Cliente cliente1 = new ClienteJuridico();
        Cliente cliente2 = new ClienteJuridico();
        Cliente cliente3 = new ClienteJuridico();
        cliente1.setUuid(UUID.randomUUID());
        cliente2.setUuid(UUID.randomUUID());
        cliente3.setUuid(UUID.randomUUID());
        clienteEntityListener.afterInsert(cliente1);
        clienteEntityListener.afterInsert(cliente2);
        clienteEntityListener.afterInsert(cliente3);
        clienteEntityListener.afterUpdate(cliente2);
        assertNotNull(filaAtendimento.getFila());
        assertEquals(3, filaAtendimento.getFila().tamanho());
        assertFalse(filaAtendimento.getFila().estaVazia());
    }
}
