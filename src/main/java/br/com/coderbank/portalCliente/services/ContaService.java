package br.com.coderbank.portalCliente.services;

import br.com.coderbank.portalCliente.dtos.request.ContaRequestDTO;
import br.com.coderbank.portalCliente.dtos.request.DepositoRequestDTO;
import br.com.coderbank.portalCliente.dtos.request.SaqueRequestDTO;
import br.com.coderbank.portalCliente.dtos.request.TransferenciaRequestDTO;
import br.com.coderbank.portalCliente.dtos.response.ContaResponseDTO;
import br.com.coderbank.portalCliente.dtos.response.OperacaoResponseDTO;
import br.com.coderbank.portalCliente.dtos.response.SaldoResponseDTO;
import br.com.coderbank.portalCliente.dtos.response.TransferenciaResponseDTO;
import br.com.coderbank.portalCliente.entities.Conta;
import br.com.coderbank.portalCliente.repositories.ContaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository; // Como estamos salvando, a Service depende da repository pois ela que possui o método de salvar
    private final Random random = new Random();

    public ContaResponseDTO criarConta(ContaRequestDTO contaRequestDTO) {

        // Verificando se o cliente já possui conta
        contaRepository.findByIdCliente(contaRequestDTO.idCliente())
                .ifPresent(conta -> {
                    throw new IllegalStateException("Cliente já possui uma conta cadastrada");
                });

        Conta contaEntity = new Conta(); //Instancia da classe entidades

        BeanUtils.copyProperties(contaRequestDTO, contaEntity); //Recebemos os dados no requestDto e passamos ao contaEntity porq ele que vai possuir objetos pro banco

        // Ao criar o objeto estamos preenchendo as informações orientadas pela regra de negócio
        contaEntity.setAgencia("0001");
        contaEntity.setNumero(gerarNumeroConta());
        contaEntity.setSaldo(BigDecimal.ZERO);
        contaEntity.setIdCliente(contaRequestDTO.idCliente());

        contaRepository.save(contaEntity); //Feito isso, salvamos no banco de dados

        return new ContaResponseDTO( //Retornando um Objeto do tipo record que é um construtor com argumentos, então passamos os mesmos atributos do ContaResponseDTO
                contaEntity.getId(),
                contaEntity.getAgencia(),
                contaEntity.getNumero(),
                contaEntity.getSaldo(),
                contaEntity.getIdCliente(),
                contaEntity.getCriadoEm(),
                contaEntity.getAtualizadoEm()
        );

    }

    private String gerarNumeroConta() {
        String numero;
        do {
            numero = String.format("%06d", random.nextInt(900000) + 100000);
        } while (contaRepository.existsByNumero(numero));

        return numero;
    }

    public SaldoResponseDTO obterSaldo(UUID idCliente) {
        // Buscar conta pelo ID do cliente
        Conta conta = contaRepository.findByIdCliente(idCliente)
                .orElseThrow(() -> new IllegalStateException(
                        "Conta não encontrada para o cliente ID: " + idCliente));

        // Retornar DTO com informações do saldo
        return new SaldoResponseDTO(
                conta.getId(),
                conta.getAgencia(),
                conta.getNumero(),
                conta.getSaldo(),
                conta.getIdCliente()
        );
    }

    public OperacaoResponseDTO realizarDeposito(UUID idCliente, DepositoRequestDTO depositoRequestDTO) {

        Conta conta = contaRepository.findByIdCliente(idCliente)
                .orElseThrow(() -> new IllegalStateException("Conta não encontrada para o cliente ID: " + idCliente));

        BigDecimal saldoAnterior = conta.getSaldo();

        BigDecimal novoSaldo = saldoAnterior.add(depositoRequestDTO.valor());

        conta.setSaldo(novoSaldo);

        contaRepository.save(conta);

        return new OperacaoResponseDTO(
                conta.getId(),
                "DEPOSITO",
                depositoRequestDTO.valor(),
                saldoAnterior,
                novoSaldo,
                LocalDateTime.now()
        );
    }

    public OperacaoResponseDTO realizarSaque(UUID idCliente, SaqueRequestDTO saqueRequestDTO) {

        Conta conta = contaRepository.findByIdCliente(idCliente) // Usando repository e buscando o id cliente
                .orElseThrow(() -> new IllegalStateException("Conta não encontrada para o cliente ID: " + idCliente)); //Lançando erro caso nao exista

        BigDecimal saldoAnterior = conta.getSaldo(); // Armazenando valor atual antes do saque

        if (saldoAnterior.compareTo(saqueRequestDTO.valor()) < 0) { //Verificando se o valor do saque excede o total na conta
            throw new IllegalStateException(String.format("Saldo insuficiente. Saldo autal: %.2f, Valor solicitado: R$ %.2f", saldoAnterior, saqueRequestDTO.valor()));
        }

        BigDecimal novoSaldo = saldoAnterior.subtract(saqueRequestDTO.valor()); // Subtraindo o valor atual pelo valor do saque

        conta.setSaldo(novoSaldo); //Setando novo valor

        contaRepository.save(conta); // Persistindo no banco

        return new OperacaoResponseDTO( // Informação para o USer
                conta.getId(),
                "SAQUE",
                saqueRequestDTO.valor(),
                saldoAnterior,
                novoSaldo,
                LocalDateTime.now()
        );
    }

    public TransferenciaResponseDTO realizarTransferencia(UUID clienteOrigemId, TransferenciaRequestDTO transferenciaRequestDTO) { //cliente sendo passado aqui é meio que um desperdicio, seria usado futuramente quando tivesse jwt/autenticação
                                                                                                                                    //Tudo necessário já pegamos pelo dto, mas se o cliente tá aí, usamos ao menos pra buscar no banco sua existencia
        Conta contaOrigem = contaRepository.findByIdCliente(clienteOrigemId)
                .orElseThrow(() -> new IllegalStateException("Conta de origem não encontrada para o cliente ID: " + clienteOrigemId));

        Conta contaDestino = contaRepository.findByIdCliente(transferenciaRequestDTO.idContaDestino())
                .orElseThrow(() -> new IllegalStateException("Conta de destino não encontrada para o cliente ID: " + transferenciaRequestDTO.idContaDestino()));

        if (contaOrigem.getIdCliente().equals(contaDestino.getIdCliente())) { //Verificando se transferência é para mesma conta
            throw new IllegalStateException("Não é permitido transferencia para a própria conta");
        }

        BigDecimal saldoAnteriorOrigem = contaOrigem.getSaldo();  // Armazenando valor da conta que vai fazer a transferencia

        if (saldoAnteriorOrigem.compareTo(transferenciaRequestDTO.valor()) < 0) { //Verificando se o valor transferido é compativel com o valor da conta
            throw new IllegalStateException(String.format("Saldo insuficiente. Saldo atual: R$ %.2f, Valor solicitado: R$ %.2f", saldoAnteriorOrigem, transferenciaRequestDTO.valor()));
        }

        // 6. Calcular novos saldos
        BigDecimal novoSaldoOrigem = saldoAnteriorOrigem.subtract(transferenciaRequestDTO.valor());
        BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(transferenciaRequestDTO.valor());

        contaOrigem.setSaldo(novoSaldoOrigem); // valor atualizado nas contas
        contaDestino.setSaldo(novoSaldoDestino);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        return new TransferenciaResponseDTO(
                contaOrigem.getId(),
                contaDestino.getId(),
                contaOrigem.getIdCliente(),
                contaDestino.getIdCliente(),
                transferenciaRequestDTO.valor(),
                saldoAnteriorOrigem,
                novoSaldoOrigem,
                LocalDateTime.now()
        );
    }

}