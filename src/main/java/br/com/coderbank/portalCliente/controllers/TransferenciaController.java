package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.request.TransferenciaRequestDTO;
import br.com.coderbank.portalCliente.dtos.response.TransferenciaResponseDTO;
import br.com.coderbank.portalCliente.services.ContaService;
import br.com.coderbank.portalCliente.services.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transferencias")
public class TransferenciaController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<TransferenciaResponseDTO> realizarTransferencia(@Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {

        TransferenciaResponseDTO response = contaService.realizarTransferencia(transferenciaRequestDTO.idContaOrigem(), transferenciaRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body((response));

    }

}
