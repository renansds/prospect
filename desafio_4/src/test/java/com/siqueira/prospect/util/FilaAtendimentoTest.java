package com.siqueira.prospect.util;

import com.siqueira.prospect.exceptions.QueueEmptyException;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.model.ClienteJuridico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilaAtendimentoTest {

    private FilaAtendimento<Cliente> filaAtendimento;

    @BeforeEach
    public void setUp() {
        filaAtendimento = FilaAtendimento.getInstance();
        filaAtendimento.newFila(new Fila<>());
    }

    @Test
    public void testGetInstance() {
        FilaAtendimento<Cliente> outraInstancia = FilaAtendimento.getInstance();
        assertSame(filaAtendimento, outraInstancia);
    }

    @Test
    public void testDeveIniciarFilaVazia() {
        Fila<Cliente> fila = filaAtendimento.getFila();
        assertNotNull(fila);
        assertTrue(fila.estaVazia());
    }

    @Test
    public void testAdicionarCliente() {
        Cliente cliente1 = new ClienteJuridico();
        Cliente cliente2 = new ClienteFisico();
        Fila<Cliente> fila = filaAtendimento.getFila();
        fila.adicionar(cliente1);
        fila.adicionar(cliente2);
        assertFalse(fila.estaVazia());
        assertEquals(2, fila.tamanho());
    }

    @Test
    public void testRemoverCliente() throws QueueEmptyException {
        Cliente cliente1 = new ClienteJuridico();
        Fila<Cliente> fila = filaAtendimento.getFila();
        fila.adicionar(cliente1);
        Cliente clienteRemovido = fila.remover();
        assertSame(cliente1, clienteRemovido);
        assertTrue(fila.estaVazia());
    }
}
