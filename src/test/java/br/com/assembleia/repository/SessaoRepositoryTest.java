package br.com.assembleia.repository;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.domain.Sessao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testes SessaoRepository")
class SessaoRepositoryTest {
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Save sessao when sucessful")
    void saveSessao_WhenSucessful() {
        Sessao sessaoBeforeSaved = createSessao();
        Sessao sessaoAfterSaved = this.sessaoRepository.save(sessaoBeforeSaved);

        Assertions.assertThat(sessaoAfterSaved).isNotNull();

        Assertions.assertThat(sessaoAfterSaved.getId()).isNotNull();

        Assertions.assertThat(sessaoAfterSaved.getValidadeEmMinutos())
                .isEqualTo(sessaoAfterSaved.getValidadeEmMinutos());

        Assertions.assertThat(sessaoAfterSaved.getPauta())
                .isEqualTo(sessaoAfterSaved.getPauta());
    }

    @Test
    @DisplayName("Delete sessao when succesful")
    void deleteSessao_RemoveSessao_WhenSucessful() {
        Sessao sessaoBeforeSaved = createSessao();
        Sessao sessaoAfterSaved = this.sessaoRepository.save(sessaoBeforeSaved);

        this.sessaoRepository.delete(sessaoAfterSaved);
        Optional<Sessao> optionalSessao = this.sessaoRepository.findById(sessaoAfterSaved.getId());

        Assertions.assertThat(optionalSessao).isEmpty();
    }

    @Test
    @DisplayName("Save sessao throw ConstraintViolationException when validadeEmMinutos is null")
    void saveSessao_Throws_ConstraintViolationException_WhenValidaEmMinutosIsNull() {
        Sessao sessao = new Sessao();
        sessao.setPauta(savedPauta());

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.sessaoRepository.save(sessao))
                .withMessageContaining("A Validade em Minutos deve ser informada.");
    }

    @Test
    @DisplayName("Save sessao throw ConstraintViolationException when validadeEmMinutos less than zero")
    void saveSessao_Throws_ConstraintViolationException_WhenValidaEmMinutosLessThanZero() {
        Sessao sessao = new Sessao();
        sessao.setValidadeEmMinutos(-1L);
        sessao.setPauta(savedPauta());

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.sessaoRepository.save(sessao))
                .withMessageContaining("A Validade em Minutos deve ser maior que 0.");
    }

    @Test
    @DisplayName("Save sessao throw ConstraintViolationException when pauta is null")
    void saveSessao_Throws_ConstraintViolationException_WhenPautaIsNull() {
        Sessao sessao = new Sessao();
        sessao.setValidadeEmMinutos(1L);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.sessaoRepository.save(sessao))
                .withMessageContaining("O Identificador da Pauta deve ser informado.");
    }

    private Sessao createSessao() {
        Pauta pautaAfterSaved = savedPauta();
        return Sessao.builder()
                .validadeEmMinutos(1L)
                .pauta(pautaAfterSaved)
                .build();
    }

    private Pauta savedPauta() {
        Pauta pautaBeforeSaved = Pauta.builder().descricao("Pauta 01").build();
        return this.pautaRepository.save(pautaBeforeSaved);
    }
}
