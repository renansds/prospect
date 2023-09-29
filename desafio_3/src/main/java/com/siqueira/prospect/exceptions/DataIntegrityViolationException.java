package com.siqueira.prospect.exceptions;

class DataIntegrityViolationException extends RuntimeException {
	private static final long serialVersionUID = -9142474798884444584L;
	public DataIntegrityViolationException(String msg) {
		super(msg);
	}
	public DataIntegrityViolationException(String msg, Throwable cause){
		super(msg, cause);
	}
}
