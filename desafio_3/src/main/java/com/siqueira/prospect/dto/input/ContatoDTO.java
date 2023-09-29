package com.siqueira.prospect.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
public class ContatoDTO {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String REGEX = "^[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9_\\-\\.]+\\.[a-zA-Z]{2,5}$";

    @NotNull(message = "Nome é requerido.")
    @NotEmpty(message = "Não é permitido nome em branco.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    private String nome;
    @NotNull(message = "(CPF) é requerido.")
    @CPF(message = "(CPF) inválido.")
    private String cpf;
    @NotNull(message = "Email é requerido.")
    @Email(message = "Informe um EMAIL válido.", regexp = REGEX)
    private String email;

    public ContatoDTO(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }
}
