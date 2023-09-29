package com.siqueira.prospect.util;

import com.siqueira.prospect.exceptions.QueueEmptyException;

public class Fila<T> implements IFila<T> {
    private Node<T> primeiro;
    private Node<T> ultimo;
    private int numeroElementos;

    public Fila() {
        this.primeiro = null;
        this.ultimo = null;
        this.numeroElementos = 0;
    }

    @Override
    public void adicionar(T elemento) {
        Node<T> novoNode = new Node<>(elemento);
        if (estaVazia()) {
            primeiro = novoNode;
            ultimo = novoNode;
        } else {
            novoNode.anterior = ultimo;
            ultimo.proximo = novoNode;
            ultimo = novoNode;
        }
        numeroElementos++;
    }


    @Override
    public T remover() throws QueueEmptyException {
        if (estaVazia()) {
            throw new QueueEmptyException("A fila está vazia");
        }
        T valorRemovido = primeiro.valor;
        primeiro = primeiro.proximo;
        numeroElementos--;
        if (estaVazia()) {
            ultimo = null;
        }
        return valorRemovido;
    }

    @Override
    public T primeiro() throws QueueEmptyException {
        if (estaVazia()) {
            throw new QueueEmptyException("A fila está vazia");
        }
        return primeiro.valor;
    }

    @Override
    public int tamanho() {
        return numeroElementos;
    }

    @Override
    public boolean estaVazia() {
        return numeroElementos == 0;
    }

    @Override
    public boolean contem(T elemento) {
        return buscaElemento(elemento) != null;
    }

    @Override
    public void remover(T elemento) {
        Node<T> encontrado = buscaElemento(elemento);
        if (encontrado != null) {
            Node<T> anterior = encontrado.anterior;
            Node<T> proximo = encontrado.proximo;
            if (anterior == null) {
                primeiro = proximo;
            } else {
                anterior.proximo = proximo;
            }
            if (proximo == null) {
                ultimo = anterior;
            } else {
                proximo.anterior = anterior;
            }
            numeroElementos--;
        }
    }

    private Node<T> buscaElemento(T elemento) {
        Node<T> atual = primeiro;
        while (atual != null) {
            if (atual.valor.equals(elemento)) {
                return atual;
            }
            atual = atual.proximo;
        }
        return null;
    }


    private static class Node<T> {
        T valor;
        Node<T> anterior;
        Node<T> proximo;

        Node(T valor) {
            this.valor = valor;
            this.anterior = null;
            this.proximo = null;
        }
    }
}
