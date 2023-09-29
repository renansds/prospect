package com.siqueira.prospect.exceptions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ValidationErrorTest {

    @Test
    public void testConstrutorEGetters() {
        Integer status = 400;
        String msg = "Erro de validação";
        Long timeStamp = System.currentTimeMillis();
        ValidationError validationError = new ValidationError(status, msg, timeStamp);
        assertEquals(status, validationError.getStatus());
        assertEquals(msg, validationError.getMsg());
        assertEquals(timeStamp, validationError.getTimeStamp());
    }

    @Test
    public void testGetErros() {
        ValidationError validationError = new ValidationError(400, "Erro de validação", 123456789L);
        validationError.addErros("cpf", "(CPF) inválido");
        validationError.addErros("mcc", "MCC é inválido, deve conter no máximo 4 caracteres");
        List<FieldMessage> erros = validationError.getErros();
        assertNotNull(erros);
        assertEquals(2, erros.size());
        assertEquals("cpf", erros.get(0).getFieldName());
        assertEquals("(CPF) inválido", erros.get(0).getMessage());
        assertEquals("mcc", erros.get(1).getFieldName());
        assertEquals("MCC é inválido, deve conter no máximo 4 caracteres", erros.get(1).getMessage());
    }

    @Test
    public void testAddErros() {
        ValidationError validationError = new ValidationError(400, "Erro de validação", 123456789L);
        validationError.addErros("email", "Email é requerido.");
        List<FieldMessage> erros = validationError.getErros();
        assertNotNull(erros);
        assertEquals(1, erros.size());
        assertEquals("email", erros.get(0).getFieldName());
        assertEquals("Email é requerido.", erros.get(0).getMessage());
    }
}
