package com.siqueira.prospect.util;

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
