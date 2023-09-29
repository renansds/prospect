package com.siqueira.prospect.listeners;

import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.util.Fila;
import com.siqueira.prospect.util.FilaAtendimento;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;


public class ClienteEntityListener {

    @PostPersist
    public void afterInsert(Cliente cliente) {
        adicionarClienteNaFila(cliente);
    }

    @PostUpdate
    public void afterUpdate(Cliente cliente) {
        adicionarClienteNaFila(cliente);
    }

    private void adicionarClienteNaFila(Cliente cliente) {
        FilaAtendimento fila = FilaAtendimento.getInstance();
        Fila<Cliente> filaAtual = fila.getFila();
        if (filaAtual.contem(cliente)) {
            filaAtual.remover(cliente);
        }
        filaAtual.adicionar(cliente);
    }
}