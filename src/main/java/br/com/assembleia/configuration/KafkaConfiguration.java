package br.com.assembleia.configuration;

import br.com.assembleia.dtos.ResultadoDTO;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstraopServer;
    @Value("${topic.name}")
    private String nameTopic;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(nameTopic, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, ResultadoDTO> resultadoProducerFactory() {
        Map<String, Object> configs = Maps.newHashMap();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstraopServer);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, ResultadoDTO> resultadoKafkaTemplate() {
        return new KafkaTemplate<>(resultadoProducerFactory());
    }
}
