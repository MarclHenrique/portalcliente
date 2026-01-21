package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.response.MovimentacaoResponseDTO;
import br.com.coderbank.portalCliente.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping("/{idConta}/transactions")
    public ResponseEntity<List<MovimentacaoResponseDTO>> consultarMovimentacoes(@PathVariable UUID idConta) {

        List<MovimentacaoResponseDTO> movimentacoes = movimentacaoService.consultarMovimentacoes(idConta);

        return ResponseEntity.ok(movimentacoes);
    }
}