package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaRequestDTO(

        @NotNull(message = "O ID do cliente remetente é obrigatório")
        UUID idContaOrigem,

        @NotNull(message = "O ID do cliente destinatário é obrigatório")
        UUID idContaDestino,

        @NotNull(message = "O valor da transferência é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor da transferência deve ser maior que zero")
        BigDecimal valor
) {
}
