package br.com.coderbank.portalCliente.exceptions;

public class ClienteJaExistenteException extends RuntimeException {

    public ClienteJaExistenteException(String message) {
        super(message);
    }
}
