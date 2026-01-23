package br.com.coderbank.portalCliente.repositories;

import br.com.coderbank.portalCliente.entities.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    // Buscar todas as movimentações de uma conta, ordenadas por data (mais recente primeiro)
    List<Movimentacao> findByContaOrderByDataHoraDesc(UUID conta);
}