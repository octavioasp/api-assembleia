package br.com.assembleia.domain;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A Opção de voto deve ser informada.")
    private Boolean opcaoVoto;
    @NotEmpty(message = "O CPF deve ser informado.")
    private String cpf;

    @ManyToOne
    @NotNull(message = "O Identificador da Pauta deve ser informada.")
    private Pauta pauta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equal(id, voto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
