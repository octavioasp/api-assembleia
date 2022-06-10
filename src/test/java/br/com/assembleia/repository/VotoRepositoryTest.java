package br.com.assembleia.repository;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.domain.Sessao;
import br.com.assembleia.domain.Voto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testes VotoRepositoryTest")
class VotoRepositoryTest {
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Save voto when sucessful")
    void saveVoto_WhenSucessful() {
        Voto votoBeforeSaved = createVoto();
        Voto votoAfterSaved = this.votoRepository.save(votoBeforeSaved);

        Assertions.assertThat(votoAfterSaved).isNotNull();

        Assertions.assertThat(votoAfterSaved.getId()).isNotNull();

        Assertions.assertThat(votoAfterSaved.getCpf())
                .isEqualTo(votoBeforeSaved.getCpf());

        Assertions.assertThat(votoAfterSaved.getOpcaoVoto())
                .isEqualTo(votoBeforeSaved.getOpcaoVoto());

        Assertions.assertThat(votoAfterSaved.getPauta())
                .isEqualTo(votoBeforeSaved.getPauta());
    }

    @Test
    @DisplayName("Delete voto when succesful")
    void deleteVoto_WhenSucessful() {
        Voto votoBeforeSaved = createVoto();
        Voto votoAfterSaved = this.votoRepository.save(votoBeforeSaved);

        this.votoRepository.delete(votoAfterSaved);
        Optional<Voto> optionalVoto = this.votoRepository.findById(votoAfterSaved.getId());

        Assertions.assertThat(optionalVoto).isEmpty();
    }

    @Test
    @DisplayName("Save voto throw ConstraintViolationException when cpf is empty")
    void saveVoto_Throws_ConstraintViolationException_WhenCpfIsEmpty() {
        Voto voto = new Voto();
        voto.setOpcaoVoto(true);
        voto.setPauta(savedPauta());

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.votoRepository.save(voto))
                .withMessageContaining("O CPF deve ser informado.");
    }

    @Test
    @DisplayName("Save voto throw ConstraintViolationException when opcaoVoto is null")
    void save_Throws_ConstraintViolationException_WhenOpcaoVotoIsNull() {
        Voto voto = new Voto();
        voto.setCpf("1234");
        voto.setPauta(savedPauta());

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.votoRepository.save(voto))
                .withMessageContaining("A Opção de voto deve ser informada");
    }

    @Test
    @DisplayName("Save voto throw ConstraintViolationException when pauta is null")
    void save_Throws_ConstraintViolationException_WhenPautaIsNull() {
        Voto voto = new Voto();
        voto.setCpf("1234");
        voto.setOpcaoVoto(false);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.votoRepository.save(voto))
                .withMessageContaining("O Identificador da Pauta deve ser informada.");
    }

    private Voto createVoto() {
        Pauta pautaAfterSaved = savedPauta();

        return Voto.builder()
                .cpf("1234")
                .opcaoVoto(true)
                .pauta(pautaAfterSaved)
                .build();
    }

    private Pauta savedPauta() {
        Pauta pautaBeforeSaved = Pauta.builder().descricao("Pauta 01").build();
        return this.pautaRepository.save(pautaBeforeSaved);
    }

}
