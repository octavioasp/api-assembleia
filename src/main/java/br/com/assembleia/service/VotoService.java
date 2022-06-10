package br.com.assembleia.service;

import br.com.assembleia.domain.Sessao;
import br.com.assembleia.domain.Voto;
import br.com.assembleia.exception.BadRequestException;
import br.com.assembleia.mapper.VotoMapper;
import br.com.assembleia.repository.SessaoRepository;
import br.com.assembleia.repository.VotoRepository;
import br.com.assembleia.requests.VotoPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoService {
    private static final Logger logger = LoggerFactory.getLogger(VotoService.class);

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final RestTemplate restTemplate;

    @Value("${url.validate.cpf}")
    private String urlValidateCpf = "";

    public Optional<List<Voto>> findAllVotos() {
        return Optional.of(votoRepository.findAll());
    }

    public Optional<Voto> findByIdOrThrowBadRequestException(Long idVoto) {
        return Optional.of(votoRepository.findById(idVoto).orElseThrow(this::votoNotFoundException));
    }

    public Optional<Voto> saveVoto(VotoPostRequestBody votoPostRequestBody) {
        Optional<Sessao> sessao = sessaoRepository.findById(
                votoPostRequestBody.getSessao_id());

        if (sessao.isEmpty()) {
            throw new BadRequestException("Sessao nao encontrada.");
        }

        Voto voto = VotoMapper.getInstance().toVoto(votoPostRequestBody);
        voto.setPauta(sessao.get().getPauta());

        voto = validateVoto(sessao.get(), voto, votoPostRequestBody.getPauta_id());
        return Optional.of(votoRepository.save(voto));
    }

    public Voto validateVoto(Sessao sessao, Voto voto, Long idPauta) {
        if (!sessao.getPauta().getId().equals(idPauta)) {
            throw new BadRequestException("Sessao associada a outra pauta.");
        }
        if (!sessao.getAtiva()) {
            throw new BadRequestException("Sessao nao ativa.");
        }

        validateCpf(voto.getCpf());

        Optional<Voto> optionalVoto = votoRepository.findByCpfAndPautaId(voto.getCpf(), idPauta);
        if (optionalVoto.isPresent()) {
            throw new BadRequestException("Voto ja registrado pra esse CPF e Pauta.");
        }
        return voto;
    }

    private void validateCpf(String cpf) {
        String url = urlValidateCpf + "/" + cpf;
        try {
            restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException ex) {
            logger.error("Cpf invalido. ", ex.getCause());
            throw new BadRequestException("CPF Invalido para voto.");
        } catch (Exception e) {
            logger.error("Erro ao validar cpf. ", e.getCause());
            throw new BadRequestException("Erro ao validar CPF.");
        }
    }

    public void deleteVoto(Long idVoto) {
        Optional<Voto> voto = findByIdOrThrowBadRequestException(idVoto);
        if (voto.isEmpty()) {
            throw votoNotFoundException();
        }
        votoRepository.delete(voto.get());
    }

    void deleteVotosByPautaId(Long idPauta) {
        Optional<List<Voto>> votos = votoRepository.findAllVotosByPautaId(idPauta);
        votos.ifPresent(votoRepository::deleteAll);
    }

    private BadRequestException votoNotFoundException() {
        return new BadRequestException("Voto nao encontrado");
    }
}
