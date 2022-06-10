package br.com.assembleia.repository;

import br.com.assembleia.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByCpfAndPautaId(String cpf, Long idPauta);

    Optional<List<Voto>> findAllVotosByPautaId(Long idPauta);
}
