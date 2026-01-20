package br.com.coderbank.portalCliente.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovimentacaoResponseDTO(
    
    UUID id,
    UUID conta,
    String tipo,
    BigDecimal valor,
    UUID contaDestinoId,
    LocalDateTime dataHora
    
) {
}