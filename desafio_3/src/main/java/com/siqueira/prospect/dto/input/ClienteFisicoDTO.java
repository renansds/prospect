package com.siqueira.prospect.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteFisicoDTO {
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

    @NotNull(message = "Mcc é requerido.")
    @NotEmpty(message = "Não é permitido mcc em branco.")
    @Size(max = 4, message = "O mcc deve ter no máximo 4 caracteres.")
    private String mcc;
    @NotNull(message = "Email é requerido.")
    @Email(message = "Informe um EMAIL válido.", regexp = REGEX)
    private String email;

    public void setCpf(String cpf) {
        if (cpf != null){
            this.cpf = cpf.replaceAll("[^0-9]", "");
        }
    }
}
