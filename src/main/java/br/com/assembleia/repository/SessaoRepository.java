package br.com.assembleia.repository;

import br.com.assembleia.domain.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<List<Sessao>> findByPautaId(Long pautaId);
}
