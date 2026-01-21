package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaRequestDTO(

        @NotNull(message = "O ID da conta remetente é obrigatório") //Validação
        UUID idContaOrigem,

        @NotNull(message = "O ID da conta é obrigatório") //Validação
        UUID idContaDestino,

        @NotNull(message = "O valor da transferência é obrigatório") //Validação
        @DecimalMin(value = "0.01", message = "O valor da transferência deve ser maior que zero") //Validação
        BigDecimal valor
) {
}
