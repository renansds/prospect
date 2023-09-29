package com.siqueira.prospect.listeners;

import com.siqueira.prospect.dto.input.ClienteFila;
import com.siqueira.prospect.model.Cliente;
import com.siqueira.prospect.model.ClienteFisico;
import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.service.FilaAtendimentoSQSService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;


public class ClienteEntityListener {
    @Autowired
    private FilaAtendimentoSQSService service;

    @PostPersist
    public void afterInsert(Object obj){
        service.adicionarClienteNaFila(toFrom(obj));
    }

    @PostUpdate
    public void afterUpdate(Cliente obj) {
        service.atualizarClienteNaFila(toFrom(obj));
    }

    private ClienteFila toFrom(Object obj){
        ClienteFila clienteFila = new ClienteFila();
        if (obj.getClass() == ClienteFisico.class) {
            ClienteFisico fisico = (ClienteFisico) obj;
            clienteFila.setUuid(fisico.getUuid());
            clienteFila.setNome(fisico.getNome());
            clienteFila.setEmail(fisico.getEmail());
            clienteFila.setMcc(fisico.getMcc());
            clienteFila.setIdentificacao(fisico.getIdentificacao());
        } else {
            ClienteJuridico juridico = (ClienteJuridico) obj;
            clienteFila.setUuid(juridico.getUuid());
            clienteFila.setRazaoSocial(juridico.getRazaoSocial());
            clienteFila.setMcc(juridico.getMcc());
            clienteFila.setIdentificacao(juridico.getIdentificacao());
            clienteFila.setContato(juridico.getContato());
        }
        return clienteFila;
    }
}