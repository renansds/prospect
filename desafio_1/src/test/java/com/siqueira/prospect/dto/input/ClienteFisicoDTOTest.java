package com.siqueira.prospect.dto.input;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteFisicoDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClienteFisicoDTO() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO("João Silva", "123.456.789-09", "1234", "joao@example.com");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertTrue(violations.isEmpty(), "ClienteFisicoDTO deve ser válido");
        assertEquals("João Silva", clienteFisicoDTO.getNome(), "Nome deve ser 'João Silva'");
        assertEquals("123.456.789-09", clienteFisicoDTO.getCpf(), "CPF deve ser '123.456.789-09'");
        assertEquals("1234", clienteFisicoDTO.getMcc(), "MCC deve ser '1234'");
        assertEquals("joao@example.com", clienteFisicoDTO.getEmail(), "Email deve ser 'joao@example.com'");
    }

    @Test
    public void testInvalidNomeNulo() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO(null, "123.456.789-09", "1234", "joao@example.com");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "Nome deve ser inválido");
        for (ConstraintViolation<ClienteFisicoDTO> violation : violations) {
            if ("Nome é requerido.".equals(violation.getMessage())) {
                return;
            }
        }
        fail("Mensagem de erro esperada não encontrada.");
    }

    @Test
    public void testInvalidNomeEmBranco() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO(null, "123.456.789-09", "1234", "joao@example.com");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "Nome deve ser inválido");
        for (ConstraintViolation<ClienteFisicoDTO> violation : violations) {
            if ("Não é permitido nome em branco.".equals(violation.getMessage())) {
                return;
            }
        }
        fail("Mensagem de erro esperada não encontrada.");
    }

    @Test
    public void testInvalidCPF() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO("João Silva", "123.456.789-0X", "1234", "joao@example.com");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "CPF deve ser inválido");
    }
    @Test
    public void testInvalidCPFNulo() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO("João Silva", null, "1234", "joao@example.com");
        clienteFisicoDTO.setCpf(null);
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "CPF deve ser inválido");
        for (ConstraintViolation<ClienteFisicoDTO> violation : violations) {
            if ("(CPF) é requerido.".equals(violation.getMessage())) {
                return;
            }
        }
        fail("Mensagem de erro esperada não encontrada.");
    }

    @Test
    public void testInvalidMcc() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO("João Silva", "123.456.789-09", "12345", "joao@example.com");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "Mcc deve ser inválido");
    }

    @Test
    public void testInvalidEmail() {
        ClienteFisicoDTO clienteFisicoDTO = new ClienteFisicoDTO("João Silva", "123.456.789-09", "1234", "joao@example");
        Set<ConstraintViolation<ClienteFisicoDTO>> violations = validator.validate(clienteFisicoDTO);
        assertFalse(violations.isEmpty(), "Email deve ser inválido");
    }
}
