package com.siqueira.prospect.model;

import br.com.caelum.stella.validation.CNPJValidator;
import com.siqueira.prospect.dto.input.ClienteJuridicoDTO;
import com.siqueira.prospect.exceptions.CNPJInvalidException;
import com.siqueira.prospect.exceptions.RazaoSocialValidationException;
import com.siqueira.prospect.listeners.ClienteEntityListener;
import jakarta.persistence.*;

@Entity
@EntityListeners(ClienteEntityListener.class)
public class ClienteJuridico extends Cliente {
    private String cnpj;
    private String razaoSocial;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    public ClienteJuridico(String cnpj, String razaoSocial, String mcc, Contato contato) {
        super(contato.getEmail(), contato.getNome());
        this.setCnpj(cnpj);
        this.setRazaoSocial(razaoSocial);
        this.setMcc(mcc);
        this.contato = contato;
    }

    public ClienteJuridico() {
    }

    public ClienteJuridico(ClienteJuridicoDTO dto) {
        this.setCnpj(dto.getCnpj());
        this.setMcc(dto.getMcc());
        this.setRazaoSocial(dto.getRazaoSocial());
        this.contato = new Contato(dto.getContato().getNome(), dto.getContato().getCpf(), dto.getContato().getEmail());
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (cnpj == null) cnpj = "";
        String aux = cnpj.replaceAll("[^0-9]", "");
        if (!isValidCnpj(aux)) {
            throw new CNPJInvalidException("CNPJ inválido");
        }
        this.cnpj = aux;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        if (!isValidRazaoSocial(razaoSocial)) {
            throw new RazaoSocialValidationException("Razão Social deve ter no máximo 50 caracteres");
        }
        this.razaoSocial = razaoSocial;
    }

    private boolean isValidRazaoSocial(String razaoSocial) {
        return razaoSocial != null && razaoSocial.trim().length() > 0 && razaoSocial.length() < 50;
    }

    public Contato getContato() {
        return contato;
    }

    public void addContato(Contato contato) {
        this.contato = contato;
    }

    @Override
    public String getIdentificacao() {
        return getCnpj();
    }

    @Override
    public String toString() {
        return "ClienteJuridico{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                '}';
    }

    private boolean isValidCnpj(String cnpj) {
        return new CNPJValidator().invalidMessagesFor(cnpj).isEmpty();
    }
}