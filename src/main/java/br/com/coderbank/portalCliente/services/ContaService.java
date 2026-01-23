package br.com.coderbank.portalCliente.services;

import br.com.coderbank.portalCliente.dtos.request.ContaRequestDTO;
import br.com.coderbank.portalCliente.dtos.response.ContaResponseDTO;
import br.com.coderbank.portalCliente.entities.Conta;
import br.com.coderbank.portalCliente.repositories.ContaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository; // Como estamos salvando, a Service depende da repository pois ela que possui o método de salvar
    private final Random random = new Random();

    public ContaResponseDTO criarConta(ContaRequestDTO contaRequestDTO) {

        // Verificando se o cliente já possui conta
        contaRepository.findByClienteId(contaRequestDTO.clienteId())
                .ifPresent(conta -> {
                    throw new IllegalStateException("Cliente já possui uma conta cadastrada");
                });

        Conta contaEntity = new Conta(); //Instancia da classe entidades

        BeanUtils.copyProperties(contaRequestDTO, contaEntity); //Recebemos os dados no requestDto e passamos ao contaEntity porq ele que vai possuir objetos pro banco

        // Ao criar o objeto estamos preenchendo as informações orientadas pela regra de negócio
        contaEntity.setAgencia("0001");
        contaEntity.setNumero(gerarNumeroConta());
        contaEntity.setSaldo(BigDecimal.ZERO);
        contaEntity.setClienteId(contaRequestDTO.clienteId());

        contaRepository.save(contaEntity); //Feito isso, salvamos no banco de dados

        return new ContaResponseDTO( //Retornando um Objeto do tipo record que é um construtor com argumentos, então passamos os mesmos atributos do ContaResponseDTO
                contaEntity.getId(),
                contaEntity.getAgencia(),
                contaEntity.getNumero(),
                contaEntity.getSaldo(),
                contaEntity.getClienteId(),
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
}