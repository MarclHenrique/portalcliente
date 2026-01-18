package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record SaqueRequestDTO(

        @NotNull(message = "O id do cliente é obrigatório")
        UUID idCliente,

        @NotNull(message = "O valor do saque é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor

) {
}
