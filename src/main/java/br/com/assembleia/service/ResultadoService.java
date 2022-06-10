package br.com.assembleia.service;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.domain.Sessao;
import br.com.assembleia.domain.Voto;
import br.com.assembleia.dtos.ResultadoDTO;
import br.com.assembleia.exception.BadRequestException;
import br.com.assembleia.kafka.VotacaoProducer;
import br.com.assembleia.repository.SessaoRepository;
import br.com.assembleia.repository.VotoRepository;
import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResultadoService {
    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final VotacaoProducer votacaoProducer;

    public void producesMessage(Sessao sessao) {
        Optional<ResultadoDTO> resultadoPauta = createResultadoPauta(sessao.getPauta().getId());
        if (resultadoPauta.isEmpty()) {
            throw new BadRequestException("Resultado da pauta nao encontrado.");
        }
        sessao.setAtiva(Boolean.FALSE);
        sessaoRepository.save(sessao);
        votacaoProducer.produceMessage(resultadoPauta.get());
    }

    public Optional<ResultadoDTO> createResultadoPauta(Long idPauta) {
        Optional<List<Voto>> optionalVotos = votoRepository.findAllVotosByPautaId(idPauta);

        if (optionalVotos.isEmpty()) {
            throw new BadRequestException("Nenhum voto encontrado.");
        }

        List<Voto> votos = optionalVotos.get();
        Pauta pauta = Iterables.getFirst(votos, new Voto()).getPauta();
        //Optional<Long> totalSessoes = sessaoRepository.countAllByPautaId(pauta.getId());

        int sins = Math.toIntExact(votos.stream().filter(voto -> Boolean.TRUE.equals(voto.getOpcaoVoto())).count());

        return Optional.of(ResultadoDTO
                .builder()
                .pauta(pauta)
                .sins(sins)
                .naos(votos.size() - sins)
                .total(votos.size())
                .build());
    }
}
