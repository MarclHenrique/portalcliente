package br.com.coderbank.portalCliente.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponseDTO(

        UUID contaOrigemId,
        UUID contaDestinoId,
        UUID clienteOrigemId,
        UUID clienteDestinoId,
        String tipoOperacao,
        BigDecimal valor,
        BigDecimal saldoAnteriorOrigem,
        BigDecimal saldoAtualOrigem,
        LocalDateTime dataHora

) {
}
