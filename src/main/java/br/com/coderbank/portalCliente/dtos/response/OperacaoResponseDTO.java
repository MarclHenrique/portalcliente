package br.com.coderbank.portalCliente.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperacaoResponseDTO(
    
    UUID contaId,
    String tipoOperacao,
    BigDecimal valor,
    BigDecimal saldoAnterior,
    BigDecimal saldoAtual,
    LocalDateTime dataHora
    
) {
}