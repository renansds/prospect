package com.siqueira.prospect.model;

import br.com.caelum.stella.validation.CPFValidator;
import com.siqueira.prospect.exceptions.CPFInvalidException;
import com.siqueira.prospect.exceptions.EmailValidationException;
import com.siqueira.prospect.exceptions.NameValidationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor
public class Contato {

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String REGEX = "^[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9_\\-\\.]+\\.[a-zA-Z]{2,5}$";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cpf;
    private String email;

    public Contato(String nome, String cpf, String email) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public void setNome(String nome) {
        if (!isValidNome(nome)) {
            throw new NameValidationException("Nome inválido");
        }
        this.nome = nome;
    }

    private boolean isValidNome(String nome) {
        if (nome == null) return false;
        return nome.trim().length() > 0 && nome.length() <= 50;
    }

    public void setCpf(String cpf) {
        if (cpf == null) cpf = "";
        String aux = cpf.replaceAll("[^0-9]", "");
        if (!isValidCpf(aux)) {
            throw new CPFInvalidException("CPF inválido");
        }
        this.cpf = aux;
    }

    private boolean isValidCpf(String cpf) {
        return new CPFValidator().invalidMessagesFor(cpf).isEmpty();
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) throw new EmailValidationException("Email inválido");
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}