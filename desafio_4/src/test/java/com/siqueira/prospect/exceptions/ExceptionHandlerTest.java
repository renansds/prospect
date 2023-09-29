package com.siqueira.prospect.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionHandlerTest {

    private ExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new ExceptionHandler();
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("clienteDTO", "cpf", "CPF inválido"));
        fieldErrors.add(new FieldError("clienteDTO", "email", "Email inválido"));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        StandardError standardError = response.getBody();
        assertNotNull(standardError);
        assertEquals(HttpStatus.BAD_REQUEST.value(), standardError.getStatus());
        assertEquals("Erro de validação", standardError.getMsg());
        assertNotNull(standardError.getTimeStamp());
    }

    @Test
    public void testHandleInvalidFormatException() {
        InvalidFormatException exception = new InvalidFormatException(null, "Valor inválido", null, null);
        ResponseEntity<StandardError> response = exceptionHandler.invalidFormatException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        StandardError standardError = response.getBody();
        assertNotNull(standardError);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), standardError.getStatus());
        assertTrue(standardError.getMsg().contains("Erro ao conveter valor exception: [Valor inválido]"));
        assertNotNull(standardError.getTimeStamp());
    }

    @Test
    public void testHandleDataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler.invalidFormatException2();
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        StandardError standardError = response.getBody();
        assertNotNull(standardError);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), standardError.getStatus());
        assertEquals("Erro ao inserir registro", standardError.getMsg());
        assertNotNull(standardError.getTimeStamp());
    }

    @Test
    public void testHandleCustomerNotFoundException() {
        CustomerNotFoundException exception = new CustomerNotFoundException("Cliente não encontrado");
        ResponseEntity<ValidationError> response = exceptionHandler.handleClienteNotFoundException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ValidationError validationError = response.getBody();
        assertNotNull(validationError);
        assertEquals(HttpStatus.NOT_FOUND.value(), validationError.getStatus());
        assertEquals("Cliente não encontrado", validationError.getMsg());
        assertNotNull(validationError.getTimeStamp());
    }

    @Test
    public void testHandleCustomerAlreadyExistsException() {
        CustomerAlreadyExistsException exception = new CustomerAlreadyExistsException("Cliente já existe");
        ResponseEntity<ValidationError> response = exceptionHandler.handleClienteAlreadyExistsException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationError validationError = response.getBody();
        assertNotNull(validationError);
        assertEquals(HttpStatus.BAD_REQUEST.value(), validationError.getStatus());
        assertEquals("Cliente já existe", validationError.getMsg());
        assertNotNull(validationError.getTimeStamp());
    }

    @Test
    public void testHandleCompanyAlreadyExistsException() {
        CompanyAlreadyExistsException exception = new CompanyAlreadyExistsException("Empresa já cadastrada.");
        ResponseEntity<ValidationError> response = exceptionHandler.handleCompanyAlreadyExistsException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationError validationError = response.getBody();
        assertNotNull(validationError);
        assertEquals(HttpStatus.BAD_REQUEST.value(), validationError.getStatus());
        assertEquals("Empresa já cadastrada.", validationError.getMsg());
        assertNotNull(validationError.getTimeStamp());
    }

    @Test
    public void testHandleCompanyNotFoundException() {
        CompanyNotFoundException exception = new CompanyNotFoundException("Registro não encontrado.");
        ResponseEntity<ValidationError> response = exceptionHandler.handleCompanyNotFoundException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ValidationError validationError = response.getBody();
        assertNotNull(validationError);
        assertEquals(HttpStatus.NOT_FOUND.value(), validationError.getStatus());
        assertEquals("Registro não encontrado.", validationError.getMsg());
        assertNotNull(validationError.getTimeStamp());
    }

    @Test
    public void testHandleQueueEmptyException() {
        String msg = "Fila esta vazia.";
        QueueEmptyException exception = new QueueEmptyException(msg);
        ResponseEntity<ValidationError> response = exceptionHandler.handleQueueEmptyException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        ValidationError validationError = response.getBody();
        assertNotNull(validationError);
        assertEquals(HttpStatus.NO_CONTENT.value(), validationError.getStatus());
        assertEquals(msg, validationError.getMsg());
        assertNotNull(validationError.getTimeStamp());
    }

    @Test
    public void testHandleGlobalException() {
        String msg = "Argumento informado é invalido";
        IllegalArgumentException exception = new IllegalArgumentException(msg);
        ResponseEntity<StandardError> response = exceptionHandler.handleGlobalException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        StandardError standardError = response.getBody();
        assertNotNull(standardError);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), standardError.getStatus());
        assertEquals("Erro interno no servidor", standardError.getMsg());
        assertNotNull(standardError.getTimeStamp());
    }
}
