package br.com.coderbank.portalCliente.exceptions;

public class TransferenciaParaMesmaContaException extends RuntimeException {
    public TransferenciaParaMesmaContaException(String message) {
        super(message);
    }
}
