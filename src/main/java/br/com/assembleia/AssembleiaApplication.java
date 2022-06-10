package br.com.assembleia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AssembleiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssembleiaApplication.class, args);
    }
}
