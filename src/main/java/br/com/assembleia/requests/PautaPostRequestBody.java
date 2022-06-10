package br.com.assembleia.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PautaPostRequestBody {
    @NotEmpty(message = "A Descrição da Pauta deve ser informada.")
    private String descricao;
}
