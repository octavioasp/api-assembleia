package br.com.assembleia.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SessaoPostRequestBody {
    @Min(value = 1L, message = "A Validade em Minutos deve ser maior que 0.")
    private Long validadeEmMinutos;
    @NotNull(message = "O Identificador da Pauta deve ser informado.")
    private Long pauta_id;
}
