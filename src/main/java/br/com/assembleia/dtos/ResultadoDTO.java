package br.com.assembleia.dtos;

import br.com.assembleia.domain.Pauta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoDTO {
    private Pauta pauta;
    private Integer sins;
    private Integer naos;
    private Integer total;
}
