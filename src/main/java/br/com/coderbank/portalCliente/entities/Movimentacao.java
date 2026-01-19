package br.com.coderbank.portalCliente.entities;

import br.com.coderbank.portalCliente.entities.Enum.TipoMovimentacao;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Movimentacao {

    @Id
    @Column
    private UUID idMovimentacao;

    @Column
    private UUID conta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private UUID contaDestinoId;

    @Column(nullable = false)
    private LocalDateTime dataHora;

}
