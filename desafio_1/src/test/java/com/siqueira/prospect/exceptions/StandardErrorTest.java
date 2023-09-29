package com.siqueira.prospect.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardErrorTest {
    @Test
    public void testConstrutorEGetters() {
        Integer status = 404;
        String msg = "Recurso não encontrado";
        Long timeStamp = System.currentTimeMillis();
        StandardError standardError = new StandardError(status, msg, timeStamp);
        assertEquals(status, standardError.getStatus());
        assertEquals(msg, standardError.getMsg());
        assertEquals(timeStamp, standardError.getTimeStamp());
    }

    @Test
    public void testSetters() {
        Integer status = 500;
        String msg = "Erro interno do servidor";
        Long timeStamp = System.currentTimeMillis();
        StandardError standardError = new StandardError(404, "Recurso não encontrado", 123456789L);
        standardError.setStatus(status);
        standardError.setMsg(msg);
        standardError.setTimeStamp(timeStamp);
        assertEquals(status, standardError.getStatus());
        assertEquals(msg, standardError.getMsg());
        assertEquals(timeStamp, standardError.getTimeStamp());
    }
}
