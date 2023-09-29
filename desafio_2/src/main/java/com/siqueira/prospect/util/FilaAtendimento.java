package com.siqueira.prospect.util;

import com.siqueira.prospect.model.Cliente;

import java.util.List;
import java.util.stream.Collectors;

public class FilaAtendimento<T>{
    private static FilaAtendimento instancia = null;

    private Fila<T> fila;

    private FilaAtendimento() {
        fila = new Fila<>();
    }

    public static synchronized FilaAtendimento getInstance() {
        if (instancia == null) {
            instancia = new FilaAtendimento();
        }
        return instancia;
    }

    public Fila<T> getFila() {
        return fila;
    }
    public void newFila(Fila<T> fila) {
        this.fila = fila;
    }
}
