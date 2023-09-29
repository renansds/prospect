package com.siqueira.prospect.util;

import com.siqueira.prospect.exceptions.QueueEmptyException;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.model.ClienteFisico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FilaTest {
    private IFila<Cliente> fila;
    private ClienteFisico cliente1;
    private ClienteFisico cliente2;
    private ClienteFisico cliente3;
    private ClienteFisico cliente4;

    @BeforeEach
    public void setUp() {
        fila = new Fila<>();
        cliente1 = new ClienteFisico("Cliente 1", "082.805.240-96", "email@gmail.com.br", "0001");
        cliente2 = new ClienteFisico("Cliente 2", "082.805.240-96", "email2@gmail.com.br", "0002");
        cliente3 = new ClienteFisico("Cliente 3", "082.805.240-96", "email3@gmail.com.br", "0003");
        cliente4 = new ClienteFisico("Cliente 3", "082.805.240-96", "email3@gmail.com.br", "0003");

    }

    @Test
    public void testAdicionar() throws QueueEmptyException {
        fila.adicionar(cliente1);
        fila.adicionar(cliente2);
        fila.adicionar(cliente3);
        assertNotNull(fila);
        assertEquals(3, fila.tamanho());
    }

    @Test
    public void testAdicionarEVerificaPrimeiroElemento() throws QueueEmptyException {
        fila.adicionar(cliente2);
        fila.adicionar(cliente1);
        assertEquals(cliente2, fila.primeiro());
    }

    @Test
    public void testRemover() throws QueueEmptyException {
        fila.adicionar(cliente3);
        fila.adicionar(cliente2);
        Cliente elementoRemovido = fila.remover();
        assertEquals(cliente3, elementoRemovido);
        assertEquals(1, fila.tamanho());
    }

    @Test
    public void testRemoverQuandoExisteUnicoElemento() throws QueueEmptyException {
        fila.adicionar(cliente3);
        Cliente elementoRemovido = fila.remover();
        assertEquals(cliente3, elementoRemovido);
        assertEquals(0, fila.tamanho());
    }

    @Test
    public void testRemoverDeveRetornaExcecao() {
        assertThrows(QueueEmptyException.class, () -> fila.remover());
    }

    @Test
    public void testDeveRetornaExcecaoQuandoAListaEhVaziaObterPrimeiroFila() {
        assertThrows(QueueEmptyException.class, () -> fila.primeiro());
    }

    @Test
    public void testDeveEncontrarERemoverElemento() {
        Fila<Cliente> fila = new Fila<>();
        cliente1.setUuid(UUID.randomUUID());
        cliente2.setUuid(UUID.randomUUID());
        cliente3.setUuid(UUID.randomUUID());
        cliente3.setUuid(UUID.randomUUID());
        fila.adicionar(cliente1);
        fila.adicionar(cliente2);
        fila.adicionar(cliente3);
        fila.adicionar(cliente4);

        fila.remover(cliente3);
        assertFalse(fila.contem(cliente3));
        assertEquals(3, fila.tamanho());
    }

    @Test
    public void testRemoverPrimeiroEUnicoElementoDaFila() {
        Fila<Cliente> fila = new Fila<>();
        cliente1.setUuid(UUID.randomUUID());
        fila.adicionar(cliente1);
        fila.remover(cliente1);
        assertFalse(fila.contem(cliente1));
        assertEquals(0, fila.tamanho());
    }
    @Test
    public void testRemoverUmElementoQueNaoEstaNaFila() {
        Fila<Cliente> fila = new Fila<>();
        cliente1.setUuid(UUID.randomUUID());
        fila.adicionar(cliente1);
        fila.remover(cliente2);
        assertTrue(fila.contem(cliente1));
        assertEquals(1, fila.tamanho());
    }


    @Test
    public void testTamanho() {
        fila.adicionar(cliente1);
        fila.adicionar(cliente2);
        assertEquals(2, fila.tamanho());
    }

    @Test
    public void testEstaVazia() {
        assertTrue(fila.estaVazia());
        fila.adicionar(cliente1);
        assertFalse(fila.estaVazia());
    }

    @Test
    public void testDeveVerificarSeExisteOsItensNaFila() {
        Fila<String> fila = new Fila<>();
        fila.adicionar("Item1");
        fila.adicionar("Item2");
        fila.adicionar("Item3");

        assertTrue(fila.contem("Item1"));
        assertTrue(fila.contem("Item2"));
        assertTrue(fila.contem("Item3"));
        assertFalse(fila.contem("Item4"));
    }
}
