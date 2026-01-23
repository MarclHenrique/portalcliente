package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record SaqueRequestDTO(

        @NotNull(message = "O id da conta é obrigatório") //Validação
        UUID idConta,

        @NotNull(message = "O valor do saque é obrigatório") //Validação
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor

) {
}
