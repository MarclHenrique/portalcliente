package br.com.coderbank.portalCliente.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record SaldoResponseDTO(
    UUID contaId,
    String agencia,
    String numero,
    BigDecimal saldo,
    UUID idCliente
) {
}