package br.com.assembleia.repository;

import br.com.assembleia.domain.Pauta;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testes PautaRepository")
class PautaRepositoryTest {
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Save pauta when sucessful")
    void savePauta_WhenSucessful() {
        Pauta pautaBeforeSaved = createPauta();
        Pauta pautaAfterSaved = this.pautaRepository.save(pautaBeforeSaved);

        Assertions.assertThat(pautaAfterSaved).isNotNull();

        Assertions.assertThat(pautaAfterSaved.getId()).isNotNull();

        Assertions.assertThat(pautaAfterSaved.getDescricao()).isEqualTo(pautaBeforeSaved.getDescricao());
    }

    @Test
    @DisplayName("Delete pauta when succesful")
    void deletePauta_WhenSucessful() {
        Pauta pautaBeforeSaved = createPauta();
        Pauta pautaAfterSaved = this.pautaRepository.save(pautaBeforeSaved);

        this.pautaRepository.delete(pautaAfterSaved);
        Optional<Pauta> optionalPauta = this.pautaRepository.findById(pautaAfterSaved.getId());

        Assertions.assertThat(optionalPauta).isEmpty();
    }

    @Test
    @DisplayName("Save pauta throw ConstraintViolationException when descricao is empty")
    void savePauta_Throws_ConstraintViolationException_WhenDescricaoIsEmpty() {
        Pauta pauta = new Pauta();

        Assertions.assertThatThrownBy(() -> this.pautaRepository.save(pauta))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save pauta throw ConstraintViolationException when descricao is empty and check content message")
    void savePauta_Throws_ConstraintViolationException_WhenDescricaoIsEmpty_CheckingContentMessage() {
        Pauta pauta = new Pauta();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.pautaRepository.save(pauta))
                .withMessageContaining("A Descrição da Pauta deve ser informada.");
    }

    private Pauta createPauta() {
        return Pauta.builder()
                .descricao("Pauta 01")
                .build();
    }
}
