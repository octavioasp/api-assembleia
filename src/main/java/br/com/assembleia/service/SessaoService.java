package br.com.assembleia.service;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.domain.Sessao;
import br.com.assembleia.exception.BadRequestException;
import br.com.assembleia.mapper.SessaoMapper;
import br.com.assembleia.repository.PautaRepository;
import br.com.assembleia.repository.SessaoRepository;
import br.com.assembleia.requests.SessaoPostRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final ResultadoService resultadoService;

    @Transactional
    public Optional<List<Sessao>> findAllSessoes() {
        return Optional.of(sessaoRepository.findAll());
    }

    public Optional<Sessao> findByIdOrThrowBadRequestException(Long idSessao) {
        return Optional.of(sessaoRepository.findById(idSessao).orElseThrow(this::sessaoNotFoundException));
    }

    public Optional<Sessao> saveSessao(SessaoPostRequestBody sessaoPostRequestBody) {
        Optional<Pauta> optionalPauta = pautaRepository.findById(sessaoPostRequestBody.getPauta_id());

        if (optionalPauta.isEmpty()) {
            throw new BadRequestException("Pauta nao encontrada.");
        }

        Sessao sessao = SessaoMapper.getInstance().toSessao(sessaoPostRequestBody);
        sessao.setPauta(optionalPauta.get());
        sessao.setAtiva(Boolean.TRUE);
        sessao.setInicio(LocalDateTime.now());
        sessao.setValidadeEmMinutos(sessao.getValidadeEmMinutos() == null ? 1L : sessao.getValidadeEmMinutos());
        producesMessage(sessao);

        return Optional.of(sessaoRepository.save(sessao));
    }

    public void deleteSessao(Long idSessao) {
        Optional<Sessao> sessao = findByIdOrThrowBadRequestException(idSessao);
        if (sessao.isEmpty()) {
            throw sessaoNotFoundException();
        }
        sessaoRepository.delete(sessao.get());
    }

    void deleteSessoesByPautaId(Long idPauta) {
        Optional<List<Sessao>> sessoes = sessaoRepository.findByPautaId(idPauta);
        sessoes.ifPresent(sessaoRepository::deleteAll);
    }

    private BadRequestException sessaoNotFoundException() {
        return new BadRequestException("Sessao nao encontrada.");
    }

    private void producesMessage(Sessao sessao) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        LocalDateTime inicio = sessao.getInicio();
        long delay = inicio.until(inicio.plusMinutes(sessao.getValidadeEmMinutos()), ChronoUnit.MINUTES);
        service.schedule(() -> resultadoService.producesMessage(sessao), delay, TimeUnit.MINUTES);
    }
}
