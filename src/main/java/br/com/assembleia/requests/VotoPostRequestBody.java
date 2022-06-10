package br.com.assembleia.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VotoPostRequestBody {
    @NotNull(message = "A Opção de voto deve ser informado.")
    private Boolean opcaoVoto;

    @NotEmpty(message = "O CPF deve ser informado.")
    private String cpf;

    @NotNull(message = "O Identificador da Pauta deve ser informada.")
    private Long pauta_id;

    @NotNull(message = "O Identificador da Sessão deve ser informado.")
    private Long sessao_id;
}
