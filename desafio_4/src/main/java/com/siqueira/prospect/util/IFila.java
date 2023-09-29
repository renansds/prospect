package com.siqueira.prospect.util;

import com.siqueira.prospect.exceptions.QueueEmptyException;

public interface IFila<T> {

    void adicionar(T elemento);

    T remover() throws QueueEmptyException;

    T primeiro() throws QueueEmptyException;

    int tamanho();

    boolean estaVazia();

    boolean contem(T cliente);

    void remover(T cliente);
}
