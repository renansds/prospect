package com.siqueira.prospect.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> objectNotFound(MethodArgumentNotValidException e) {
		ValidationError erro = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				Instant.now().toEpochMilli());
		for(FieldError x :e.getBindingResult().getFieldErrors()){
			erro.addErros(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<StandardError> invalidFormatException(InvalidFormatException e){
		StandardError standardError = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro ao conveter valor exception: [".concat(e.getMessage()).concat("]"), Instant.now().toEpochMilli());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standardError);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> invalidFormatException2(){
		StandardError standardError = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro ao inserir registro", Instant.now().toEpochMilli());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(standardError);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ValidationError> handleClienteNotFoundException(CustomerNotFoundException ex) {
		ValidationError error = new ValidationError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now().toEpochMilli());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ValidationError> handleClienteAlreadyExistsException(CustomerAlreadyExistsException ex) {
		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now().toEpochMilli());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(CompanyAlreadyExistsException.class)
	public ResponseEntity<ValidationError> handleCompanyAlreadyExistsException(CompanyAlreadyExistsException ex) {
		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now().toEpochMilli());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@org.springframework.web.bind.annotation.ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<ValidationError> handleCompanyNotFoundException(CompanyNotFoundException ex) {
		ValidationError error = new ValidationError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now().toEpochMilli());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
