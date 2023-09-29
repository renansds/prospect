package com.siqueira.prospect.dto.input;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.siqueira.prospect.model.Contato;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ClienteFila {
    private UUID uuid;
    @JsonSetter(nulls = Nulls.SKIP)
    private String nome;
    @JsonSetter(nulls = Nulls.SKIP)
    private String email;
    @JsonSetter(nulls = Nulls.SKIP)
    private String mcc;
    private String identificacao;
    private Contato contato;
    private String razaoSocial;
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
