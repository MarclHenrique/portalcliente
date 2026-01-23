package br.com.coderbank.portalCliente.controllers;

import br.com.coderbank.portalCliente.dtos.request.ContaRequestDTO;
import br.com.coderbank.portalCliente.dtos.request.DepositoRequestDTO;
import br.com.coderbank.portalCliente.dtos.request.SaqueRequestDTO;
import br.com.coderbank.portalCliente.dtos.response.ContaResponseDTO;
import br.com.coderbank.portalCliente.dtos.response.OperacaoResponseDTO;
import br.com.coderbank.portalCliente.dtos.response.SaldoResponseDTO;
import br.com.coderbank.portalCliente.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/contas")
public class ContaController {

    @Autowired
    private ContaService contaService; // Controller depende do Service, pois o Service que possui os métodos de criar conta e regras de negócio

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@Valid @RequestBody ContaRequestDTO contaRequestDTO) { // Usando um objeto DTO para transitar dados e não passar diretamente a entidade, um trata das requisições da entrada, o outro de saída

        ContaResponseDTO response = contaService.criarConta(contaRequestDTO);

        //  return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(contaRequestDTO)); // Retornando o status de criação htt e o request

        return ResponseEntity.status(HttpStatus.CREATED).body((response)); // Achei mais intuitivo, com contaRequestDto parece que envio oq recebo

    }

    @GetMapping("/{idCliente}") // Mapeando Rota
    public ResponseEntity<SaldoResponseDTO> obterSaldo(@PathVariable UUID idCliente) { // Usando a biblioteca ResponseEntity para controlar status http, body e passando clienteId porq a requisição só vai saber quem buscar através dele
//PathVariable pega o id do cliente, sem ele idcliente sempre vai dar null
        SaldoResponseDTO response = contaService.obterSaldo(idCliente);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/deposito")
    public ResponseEntity<OperacaoResponseDTO> realizarDeposito(@Valid @RequestBody DepositoRequestDTO depositoRequestDTO) { //Não passamos o id aqui, porq ele já vem dentro do corpo da requisição e no metodo anterior passamos porq o spring pega no path

        OperacaoResponseDTO response = contaService.realizarDeposito(depositoRequestDTO.idCliente(), depositoRequestDTO); //primeiro parametro passa o id informando pelo cliente e o segundo envia o valor do deposito

        /**
         * Inicia no Controller, recebe os dados do usuário e envia ao Service
         * Service recebe idCliente e valor do deposito, busca a conta, faz deposito e envia para o Repository salvar
         * Após isso o Service monta a resposta com o OperacaoResponseDto
         * Finaliza no controller, retornando status e o responseDto
         */

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/saque")
    public ResponseEntity<OperacaoResponseDTO> realizarSaque(@Valid @RequestBody SaqueRequestDTO saqueRequestDTO) {

        OperacaoResponseDTO response = contaService.realizarSaque(saqueRequestDTO.idCliente(), saqueRequestDTO );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}