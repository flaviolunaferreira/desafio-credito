#!/bin/sh
MAX_ATTEMPTS=60
ATTEMPT=0

# Aguarda até que a porta do Kafka esteja acessível
until nc -z kafka 9092 || [ $ATTEMPT -eq $MAX_ATTEMPTS ]; do
    echo "Aguardando Kafka na porta 9092 (tentativa $((ATTEMPT+1))/$MAX_ATTEMPTS)..."
    sleep 2
    ATTEMPT=$((ATTEMPT+1))
done

if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
    echo "Erro: Kafka não está disponível na porta 9092 após $MAX_ATTEMPTS tentativas."
    exit 1
fi

# Aguarda até que o broker esteja operacional (verifica tópicos)
ATTEMPT=0
until docker run --rm --network app-network confluentinc/cp-kafka:latest kafka-topics --list --bootstrap-server kafka:9092 || [ $ATTEMPT -eq $MAX_ATTEMPTS ]; do
    echo "Aguardando Kafka estar totalmente operacional (tentativa $((ATTEMPT+1))/$MAX_ATTEMPTS)..."
    sleep 2
    ATTEMPT=$((ATTEMPT+1))
done

if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
    echo "Erro: Kafka não está operacional após $MAX_ATTEMPTS tentativas."
    exit 1
fi

echo "Kafka está pronto!"
java -jar app.jar --spring.profiles.active=dev