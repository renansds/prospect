package com.siqueira.prospect.model;

import br.com.caelum.stella.validation.CPFValidator;
import com.siqueira.prospect.dto.input.ClienteFisicoDTO;
import com.siqueira.prospect.exceptions.CPFInvalidException;
import com.siqueira.prospect.listeners.ClienteEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Getter;

@Getter
@Entity
@EntityListeners(ClienteEntityListener.class)
public class ClienteFisico extends Cliente {
    private String cpf;

    public ClienteFisico(){
        super();
    }
    public ClienteFisico(String nome, String cpf, String email, String mcc) {
        super(mcc, email, nome);
        this.setCpf(cpf);
    }

    public ClienteFisico(String nome, String cpf, String email) {
        super(email, nome);
        this.setCpf(cpf);
    }

    public ClienteFisico(ClienteFisicoDTO clienteFisicoDTO) {
        super(clienteFisicoDTO.getEmail(), clienteFisicoDTO.getNome());
        this.setCpf(clienteFisicoDTO.getCpf());
        this.setMcc(clienteFisicoDTO.getMcc());
    }

    public void setCpf(String cpf) {
        if (cpf == null) cpf = "";
        String aux = cpf.replaceAll("[^0-9]", "");
        if (!isValidCpf(aux)) {
            throw new CPFInvalidException("CPF inválido");
        }
        this.cpf = aux;
    }

    @Override
    public String getIdentificacao() {
        return getCpf();
    }

    @Override
    public String toString() {
        return "ClienteFisico{" +
                "nome='" + getNome() + '\'' +
                "cpf='" + cpf + '\'' +
                '}';
    }

    private boolean isValidCpf(String cpf) {
        return new CPFValidator().invalidMessagesFor(cpf).isEmpty();
    }
}
