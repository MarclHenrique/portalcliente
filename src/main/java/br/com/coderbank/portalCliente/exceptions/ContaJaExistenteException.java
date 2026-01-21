package br.com.coderbank.portalCliente.exceptions;

public class ContaJaExistenteException extends RuntimeException {

    public ContaJaExistenteException(String message) {
        super(message);
    }
}
