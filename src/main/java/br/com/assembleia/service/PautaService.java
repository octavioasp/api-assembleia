package br.com.assembleia.service;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.exception.BadRequestException;
import br.com.assembleia.mapper.PautaMapper;
import br.com.assembleia.repository.PautaRepository;
import br.com.assembleia.requests.PautaPostRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PautaService {
    private final PautaRepository pautaRepository;
    private final SessaoService sessaoService;
    private final VotoService votoService;

    public Optional<List<Pauta>> findAllPautas() {
        return Optional.of(pautaRepository.findAll());
    }

    public Optional<Pauta> findByIdOrThrowBadRequestException(Long idPauta) {
        return Optional.of(pautaRepository.findById(idPauta).orElseThrow(this::pautaNotFoundException));
    }

    public Optional<Pauta> savePauta(PautaPostRequestBody pautaPostRequestBody) {
        return Optional.of(pautaRepository.save(PautaMapper.getInstance().toPauta(pautaPostRequestBody)));
    }

    public void deletePauta(Long idPauta) {
        Optional<Pauta> pauta = findByIdOrThrowBadRequestException(idPauta);
        pautaRepository.deleteById(pauta.get().getId());
        sessaoService.deleteSessoesByPautaId(pauta.get().getId());
        votoService.deleteVotosByPautaId(pauta.get().getId());
    }

    private BadRequestException pautaNotFoundException() {
        return new BadRequestException("Pauta nao encontrada");
    }
}
