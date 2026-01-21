package br.com.coderbank.portalCliente.dtos.response;

public record ErrorResponseDTO(String message, long timeStamp) { //Poderíamos passar outros parâmetros para melhorar o entendimento do erro e corrigir posteriormente
}
