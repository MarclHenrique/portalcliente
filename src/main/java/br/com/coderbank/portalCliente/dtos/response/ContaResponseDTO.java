package br.com.coderbank.portalCliente.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContaResponseDTO( // Definimos como Record e não precisamos ficar criando getters e setters
    //Dados enviados
     UUID id,
     String agencia,
     String numero,
     BigDecimal saldo,
     UUID clienteId,
     LocalDateTime criadoEm,
     LocalDateTime atualizadoEm

){

}