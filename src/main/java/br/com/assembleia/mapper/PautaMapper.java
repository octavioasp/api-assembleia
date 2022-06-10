package br.com.assembleia.mapper;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.requests.PautaPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class PautaMapper {

    public static PautaMapper getInstance() {
        return Mappers.getMapper(PautaMapper.class);
    }

    public abstract Pauta toPauta(PautaPostRequestBody pautaPostRequestBody);
}
