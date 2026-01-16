package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.request.ContaRequestDTO;
import br.com.coderbank.portalCliente.dtos.response.ContaResponseDTO;
import br.com.coderbank.portalCliente.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contas")
public class ContaController {

    @Autowired
    private ContaService contaService; // Controller depende do Service, pois o Service que possui os métodos de criar conta e regras de negócio

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO contaRequestDTO) { // Usando um objeto DTO para transitar dados e não passar diretamente a entidade, um trata das requisições da entrada, o outro de saída

        ContaResponseDTO response = contaService.criarConta(contaRequestDTO);

      //  return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(contaRequestDTO)); // Retornando o status de criação htt e o request

        return ResponseEntity.status(HttpStatus.CREATED).body((response)); // Achei mais intuitivo, com contaRequestDto parece que envio oq recebo

    }
}