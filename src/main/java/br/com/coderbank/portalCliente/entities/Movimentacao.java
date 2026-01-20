package br.com.coderbank.portalCliente.entities;

import br.com.coderbank.portalCliente.entities.Enum.TipoMovimentacao;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_MOVIMENTACAO")
public class Movimentacao {

    @Id
    @GeneratedValue
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

    @Column(name = "data_hora", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataHora;

    public Movimentacao(UUID contaId, TipoMovimentacao tipo, BigDecimal valor, UUID contaDestinoId) {
        this.conta = contaId;
        this.tipo = tipo;
        this.valor = valor;
        this.contaDestinoId = contaDestinoId;
    }

    public Movimentacao() {

    }

    public UUID getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(UUID idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public UUID getConta() {
        return conta;
    }

    public void setConta(UUID conta) {
        this.conta = conta;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public UUID getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(UUID contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


}
