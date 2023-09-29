package com.siqueira.prospect.dto.input;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@NoArgsConstructor
public class ClienteJuridicoDTO {
    @NotNull(message = "(CNPJ) não pode ser nulo.")
    @CNPJ(message = "(CNPJ) é inválido.")
    private String cnpj;

    @NotNull(message = "Razão Social é requerida.")
    @NotEmpty(message = "Não é permitido Razão Social em branco.")
    @Size(max = 50, message = "A Razão Social deve ter no máximo 50 caracteres.")
    private String razaoSocial;
    @NotNull(message = "Contato é requerido.")
    private ContatoDTO contato;

    @NotNull(message = "Mcc é requerido.")
    @NotEmpty(message = "Não é permitido mcc em branco.")
    @Size(max = 4, message = "O mcc deve ter no máximo 4 caracteres.")
    private String mcc;

    public void setCnpj(String cnpj) {
        if (cnpj != null) {
            this.cnpj = cnpj.replaceAll("[^0-9]", "");
        }
    }
}
