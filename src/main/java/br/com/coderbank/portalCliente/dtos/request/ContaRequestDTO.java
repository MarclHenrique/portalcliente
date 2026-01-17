package br.com.coderbank.portalCliente.dtos.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ContaRequestDTO( //Dados que chegam na aplicação
    
    @NotNull(message = "O ID do cliente é obrigatório")
    UUID idCliente

) {
}