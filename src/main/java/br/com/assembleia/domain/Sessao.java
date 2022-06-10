package br.com.assembleia.domain;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A Validade em Minutos deve ser informada.")
    @Min(value = 1L, message = "A Validade em Minutos deve ser maior que 0.")
    private Long validadeEmMinutos;
    private LocalDateTime inicio;
    private Boolean ativa;

    @ManyToOne
    @NotNull(message = "O Identificador da Pauta deve ser informado.")
    private Pauta pauta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessao sessao = (Sessao) o;
        return Objects.equal(id, sessao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
