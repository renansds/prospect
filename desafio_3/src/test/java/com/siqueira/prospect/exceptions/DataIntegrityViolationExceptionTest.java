package com.siqueira.prospect.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataIntegrityViolationExceptionTest {

    @Test
    public void testConstrutorSemCausa() {
        String mensagem = "Mensagem de exceção";
        DataIntegrityViolationException exception = new DataIntegrityViolationException(mensagem);
        assertEquals(mensagem, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstrutorComCausa() {
        String mensagem = "Mensagem de exceção";
        Throwable causa = new RuntimeException("Exception thrown when an attempt to insert or update data results in violation of an primary key or unique constraint.");
        DataIntegrityViolationException exception = new DataIntegrityViolationException(mensagem, causa);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}