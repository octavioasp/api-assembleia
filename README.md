
# API Assembléia de Votação

Projeto que simula uma assembléia de votação. Uma Pauta, que pode ter uma ou mais Sessões.</br>
Cada Sessão tem uma validade em minutos definida (1 minuto por default). </br>
Durante a validade da sessão Votos são feitos vinculados a ela, 1 voto (Sim/Não) por CPF válido. </br>
Ao final da sessão uma mensagem é disparada através de mesageria notificando o resultado da sessão.

# Requisitos
Docker

# Passo a Passo 

### Docker Compose
Responsável por instalar o Mysql e o Kafka.
```
docker-compose up -d
```

### Container Kafka
Acessa o container do kafka, para criar/listar tópicos, e criar os produtores e consumidores.
```
docker exec -i -t zookeeper /bin/bash
```

### Listar Tópicos
```
/bin/kafka-topics --list --bootstrap-server kafka:29092
```

### Criar consumidor
```
/bin/kafka-console-consumer --topic resultado-votacao --bootstrap-server kafka:29092 --from-beginning
```

# Swagger
```
http://localhost:8080/api/documentation/swagger-ui/
```

# Tecnologias

## Desenvolvido com:

[Springboot](https://spring.io/projects/spring-boot) | [Docker](https://www.docker.com/) | [Apache Kafka](https://kafka.apache.org/) | [Lombok](https://projectlombok.org/) </br>
[Mapstruct](https://mapstruct.org/) | [Maven](https://maven.apache.org/) | [MySQL](https://www.mysql.com/) | [Swagger](https://swagger.io/docs/)

* Desenvolvido por **Octavio Pereira**
