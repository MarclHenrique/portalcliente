package br.com.coderbank.portalCliente.services;

import br.com.coderbank.portalCliente.dtos.response.MovimentacaoResponseDTO;
import br.com.coderbank.portalCliente.entities.Conta;
import br.com.coderbank.portalCliente.entities.Enum.TipoMovimentacao;
import br.com.coderbank.portalCliente.entities.Movimentacao;
import br.com.coderbank.portalCliente.exceptions.ContaNaoEncontradaException;
import br.com.coderbank.portalCliente.repositories.ContaRepository;
import br.com.coderbank.portalCliente.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    // Registrar uma movimentação
    public void registrarMovimentacao(UUID contaId, TipoMovimentacao tipo, BigDecimal valor, UUID contaDestinoId) {

        Movimentacao movimentacao = new Movimentacao(contaId, tipo, valor, contaDestinoId);

        movimentacaoRepository.save(movimentacao);
    }

    // Consultar movimentações de um cliente
    public List<MovimentacaoResponseDTO> consultarMovimentacoes(UUID idConta) { //Usando collection List porque podem haver varias movimentações

        Conta conta = contaRepository.findByIdConta(idConta) //Só verificando existência do cliente
                .orElseThrow(() -> new ContaNaoEncontradaException(
                        "Conta não encontrada para o cliente ID: " + idConta));

        List<Movimentacao> movimentacoes = movimentacaoRepository.findByContaOrderByDataHoraDesc(conta.getIdConta()); //Usando metodo do Repository e buscando

        // Converter para DTO
        return movimentacoes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private MovimentacaoResponseDTO converterParaDTO(Movimentacao movimentacao) {
        return new MovimentacaoResponseDTO(
                movimentacao.getIdMovimentacao(),
                movimentacao.getConta(),
                movimentacao.getTipo().name(),
                movimentacao.getValor(),
                movimentacao.getContaDestinoId(),
                movimentacao.getDataHora()
        );
    }
}