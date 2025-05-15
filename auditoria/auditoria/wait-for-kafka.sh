#!/bin/sh
MAX_ATTEMPTS=60
ATTEMPT=0

wait_for_service() {
  local host=$1
  local port=$2
  local name=$3
  ATTEMPT=0

  until nc -z $host $port || [ $ATTEMPT -eq $MAX_ATTEMPTS ]; do
    echo "Aguardando $name ($host:$port) (tentativa $((ATTEMPT+1))/$MAX_ATTEMPTS)..."
    sleep 2
    ATTEMPT=$((ATTEMPT+1))
  done

  if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
    echo "Erro: $name não disponível após $MAX_ATTEMPTS tentativas."
    exit 1
  fi

  echo "$name está disponível em $host:$port"
}

echo "🔎 Verificando dependências da aplicação..."

# Espera Kafka estar disponível na porta
wait_for_service "kafka" 9092 "Kafka"

# Verifica se Kafka responde comandos administrativos
ATTEMPT=0
until docker run --rm --network app-network confluentinc/cp-kafka:latest \
  kafka-topics --list --bootstrap-server kafka:9092 > /dev/null 2>&1 || [ $ATTEMPT -eq $MAX_ATTEMPTS ]; do
  echo "Aguardando Kafka estar operacional (tentativa $((ATTEMPT+1))/$MAX_ATTEMPTS)..."
  sleep 2
  ATTEMPT=$((ATTEMPT+1))
done

if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
  echo "Erro: Kafka não respondeu a comandos após $MAX_ATTEMPTS tentativas."
  exit 1
fi

echo "✅ Kafka operacional."

# Espera MongoDB (usado pelo auditoria)
wait_for_service "mongodb" 27017 "MongoDB"

# (opcional) espera PostgreSQL (caso queira incluir API ou notificações)
# wait_for_service "db" 5432 "PostgreSQL"

echo "🚀 Iniciando aplicação..."
exec java -jar app.jar --spring.profiles.active=dev
