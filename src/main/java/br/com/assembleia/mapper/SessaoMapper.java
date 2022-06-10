package br.com.assembleia.mapper;

import br.com.assembleia.domain.Sessao;
import br.com.assembleia.requests.SessaoPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SessaoMapper {

    public static SessaoMapper getInstance() {
        return Mappers.getMapper(SessaoMapper.class);
    }

    public abstract Sessao toSessao(SessaoPostRequestBody sessaoPostRequestBody);
}
