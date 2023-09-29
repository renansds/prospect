package com.siqueira.prospect.dto.output;

import com.siqueira.prospect.model.ClienteFisico;
import lombok.Getter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ClienteFisicoResponseDTO {
    private UUID uuid;
    private String cpf;
    private String mcc;
    private String nome;
    private String email;
    private Map<String, String> links = new HashMap<>();
    public ClienteFisicoResponseDTO(ClienteFisico output, URI location) {
        this.cpf = output.getCpf();
        this.mcc = output.getMcc();
        this.nome = output.getNome();
        this.email = output.getEmail();
        this.uuid = output.getUuid();
        links.put("self", location.toString());
    }

    public ClienteFisicoResponseDTO(ClienteFisico output) {
        this.cpf = output.getCpf();
        this.mcc = output.getMcc();
        this.nome = output.getNome();
        this.email = output.getEmail();
        this.uuid = output.getUuid();
    }
}
