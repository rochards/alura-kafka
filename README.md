# Apache Kafka

Uma breve introdução ao Apache Kafka. Na pasta desse projeto há vários serviços todos construídos em Java. Esse arquivos estão divididos em módulos.
- O serviço NewOrderMain produz eventos/mensages para o Kafka nos tópicos ```ECOMMERCE_NEW_ORDER``` e  ```ECOMMERCE_SEND_EMAIL```;
- O serviço EmailService consome os eventos/mensages publicados no tópico ```ECOMMERCE_SEND_EMAIL```;
- O serviço FraudDetectorService consome os eventos/mensages publicados no tópico ```ECOMMERCE_NEW_ORDER```;
- O serviço LogService consome os eventos/mensagens de ambos os tópicos.

Nesse projeto é mostrado como construir serializadores e desserializadores customizados para enviar e consumir mensagens no Kafka.

### Necessário para executar esse projeto
- Docker  20.10.5 ou superior;
- Java 11 ou superior;
- Maven 3.6.3 ou superior;

Para subir os serviços do docker-compose.yaml basta executar na pasta raiz da aplicação: ```$ docker-compose up -d```
