package com.siqueira.prospect.model;

import com.siqueira.prospect.exceptions.EmailValidationException;
import com.siqueira.prospect.exceptions.MccValidationException;
import com.siqueira.prospect.exceptions.NameValidationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@MappedSuperclass
public abstract class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String REGEX = "^[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9_\\-\\.]+\\.[a-zA-Z]{2,5}$";

    private String mcc;
    private String email;
    private String nome;

    public Cliente(){}
    public Cliente(String mcc, String email, String nome) {
        this.setMcc(mcc);
        this.setEmail(email);
        this.setNome(nome);
    }

    public Cliente(String email, String nome) {
        this.setEmail(email);
        this.setNome(nome);
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) throw new EmailValidationException("Email inválido");
        this.email = email;
    }

    public void setMcc(String mcc) {
        if (mcc.trim().length() == 0) {
            throw new MccValidationException("O valor de MCC é obrigatório.");
        }
        if (mcc.length() > 4) {
            throw new MccValidationException("O valor de MCC não pode ser maior que 4 caracteres.");
        }
        this.mcc = mcc;
    }

    public void setNome(String nome) {
        if (!isValidNome(nome)) {
            throw new NameValidationException("Nome inválido");
        }
        this.nome = nome;
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidNome(String nome) {
        if (nome == null) return false;
        return nome.trim().length() > 0 && nome.length() <= 50;
    }

    public abstract String getIdentificacao();
}
