package com.siqueira.prospect.model;

import com.siqueira.prospect.exceptions.CPFInvalidException;
import com.siqueira.prospect.exceptions.EmailValidationException;
import com.siqueira.prospect.exceptions.MccValidationException;
import com.siqueira.prospect.exceptions.NameValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteFisicoTest {

    @Test
    public void testCriacaoClienteFisicoValido() {
        ClienteFisico output = new ClienteFisico("João Ricardo de Barros", "162.516.970-16", "seu@email.com.br",
                "5912");
        assertNotNull(output);
        assertTrue(output instanceof ClienteFisico);
        assertEquals("16251697016", output.getCpf());
        assertEquals("16251697016", output.getIdentificacao());
        assertEquals("5912", output.getMcc());
        assertEquals("João Ricardo de Barros", output.getNome());
        assertEquals("seu@email.com.br", output.getEmail());
    }

    @Test
    public void testDeveLancarExceptionMccInvalido() {
        assertThrows(MccValidationException.class, () -> {
            ClienteFisico output = new ClienteFisico("João Ricardo de Barros", "162.516.970-16", "inva0mail.com",
                    "59123");
        });
    }

    @Test
    public void testDeveLancarExceptionNomeInvalido() {
        assertNomeInvalido("João da Silva Santos Oliveira Pereira Rodrigues Souza Lima Almeida Castro Gomes Fernandes Dias Barbosa");
        assertNomeInvalido(" ");
        assertNomeInvalido(null);
    }

    private void assertNomeInvalido(String nome) {
        assertThrows(NameValidationException.class, () -> {
            new ClienteFisico(nome, "162.516.970-16", "seumail@gmail.com",
                    "5912");
        });
    }

    @Test
    public void testDeveLancarExceptionEmailInvalido() {
        assertEmailInvalido("emailinv.br");
        assertEmailInvalido("@emailinv.br");
        assertEmailInvalido(" ");
        assertEmailInvalido(null);
    }

    public void assertEmailInvalido(String email) {
        assertThrows(EmailValidationException.class, () -> {
            new ClienteFisico("nome", "162.516.970-16", email,
                    "5912");
        });
    }

    private void assertMccInvalido(String mcc) {
        assertThrows(MccValidationException.class, () -> {
            new ClienteFisico("João Ricardo de Barros", "162.516.970-16", "seumail@gmail.com",
                    mcc);
        });
    }

    @Test
    public void testDeveLancarExceptionCPFInvalido() {
        assertCPFInvalido("162.516.970-12");
        assertCPFInvalido(null);
        assertCPFInvalido("");
        assertCPFInvalido("7943856805");
        assertCPFInvalido("111.111.111-11");
        assertCPFInvalido("000.000.000-00");
        assertCPFInvalido("999.999.999-99");
    }

    private void assertCPFInvalido(String cpf) {
        assertThrows(CPFInvalidException.class, () -> {
            new ClienteFisico("João Ricardo de Barros", cpf, "jogao@gmail.com.br", "5912");
        });
    }
}
