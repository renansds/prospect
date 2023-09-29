package com.siqueira.prospect.model;

import com.siqueira.prospect.exceptions.CPFInvalidException;
import com.siqueira.prospect.exceptions.EmailValidationException;
import com.siqueira.prospect.exceptions.MccValidationException;
import com.siqueira.prospect.exceptions.NameValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContatoTest {

    @Test
    public void testCriacaoClienteFisicoValido() {
        Contato output = new Contato("João Ricardo de Barros", "162.516.970-16", "seu@email.com.br");
        assertNotNull(output);
        assertTrue(output instanceof Contato);
        assertEquals("16251697016", output.getCpf());
        assertNull(output.getId());
        assertEquals("João Ricardo de Barros", output.getNome());
        assertEquals("seu@email.com.br", output.getEmail());
    }

    @Test
    public void testDeveLancarExceptionNomeInvalido() {
        assertNomeInvalido("João da Silva Santos Oliveira Pereira Rodrigues Souza Lima Almeida Castro Gomes Fernandes Dias Barbosa");
        assertNomeInvalido(" ");
        assertNomeInvalido(null);
    }

    private void assertNomeInvalido(String nome) {
        assertThrows(NameValidationException.class, () -> {
            new Contato(nome, "162.516.970-16", "seumail@gmail.com");
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
            new Contato("nome", "162.516.970-16", email);
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
            new Contato("João Ricardo de Barros", cpf, "jogao@gmail.com.br");
        });
    }
}
