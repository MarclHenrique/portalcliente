package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.response.MovimentacaoResponseDTO;
import br.com.coderbank.portalCliente.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> consultarMovimentacoes(@PathVariable UUID clienteId) {

        List<MovimentacaoResponseDTO> movimentacoes = movimentacaoService.consultarMovimentacoes(clienteId);

        return ResponseEntity.ok(movimentacoes);
    }
}