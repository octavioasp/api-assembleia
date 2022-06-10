package br.com.assembleia.mapper;

import br.com.assembleia.domain.Voto;
import br.com.assembleia.requests.VotoPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class VotoMapper {

    public static VotoMapper getInstance() {
        return Mappers.getMapper(VotoMapper.class);
    }

    public abstract Voto toVoto(VotoPostRequestBody votoPostRequestBody);
}
