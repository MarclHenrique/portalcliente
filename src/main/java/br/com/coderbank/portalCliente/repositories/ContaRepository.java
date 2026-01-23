package br.com.coderbank.portalCliente.repositories;

import br.com.coderbank.portalCliente.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository // O jpaRepository já possui essa anotação, então não é necessário anotar
public interface ContaRepository extends JpaRepository<Conta, UUID> { //Serve para integrar a aplicação com o banco de dados(inserir dados, consultar e etc)
    // É uma interface pois estende o jpaRepository que é uma ponte entre o bd e api possuindo operações básicas de crud com métodos prontos
    //Criamos um repository para cada entidade, especificamos a classe e o tipo do id

    Optional<Conta> findByClienteId(UUID clienteId); //Verificar se o cliente já tem conta

    boolean existsByNumero(String numero); //Usado pra verificar se já existe um número igual ao gerado lá no service para o número da conta

}