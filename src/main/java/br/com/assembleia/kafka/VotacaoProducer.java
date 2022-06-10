package br.com.assembleia.kafka;

import br.com.assembleia.dtos.ResultadoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Getter
@AllArgsConstructor
public class VotacaoProducer {
    private static final Logger logger = LoggerFactory.getLogger(VotacaoProducer.class);

    private final KafkaTemplate<String, ResultadoDTO> kafkaTemplate;

    public void produceMessage(ResultadoDTO resultadoDTO) {
        kafkaTemplate.send("resultado-votacao", resultadoDTO).addCallback(
                sucess -> logger.info("Message send" + sucess.getProducerRecord().value()),
                failure -> logger.info("Message failure" + failure.getMessage())
        );
    }
}
