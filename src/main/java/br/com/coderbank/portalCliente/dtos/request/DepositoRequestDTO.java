package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositoRequestDTO(

        @NotNull(message = "O id do cliente é obrigatório")
        UUID idCliente,

        @NotNull(message = "O valor do depósito é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor do depósito deve ser maior que zero")
        BigDecimal valor

) {
}