package com.siqueira.prospect.dto.output;

import com.siqueira.prospect.model.ClienteJuridico;
import com.siqueira.prospect.model.Contato;
import lombok.Getter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Getter
public class ClienteJuridicoResponseDTO {
    private UUID uuid;
    private String cnpj;
    private String razaoSocial;
    private String mcc;
    private Contato contato;
    private Map<String, String> links = new HashMap<>();
    public ClienteJuridicoResponseDTO(ClienteJuridico cliente) {
        this.cnpj =  cliente.getCnpj();
        this.razaoSocial = cliente.getRazaoSocial();
        this.mcc = cliente.getMcc();
        this.contato = cliente.getContato();
        this.uuid = cliente.getUuid();
    }

    public ClienteJuridicoResponseDTO(ClienteJuridico cliente, URI location) {
        this.cnpj =  cliente.getCnpj();
        this.razaoSocial = cliente.getRazaoSocial();
        this.mcc = cliente.getMcc();
        this.contato = cliente.getContato();
        this.uuid = cliente.getUuid();
        links.put("self", location.toString());
    }
}
