#!/bin/bash
set -e

# Aguarda até que o banco de dados esteja disponível
until pg_isready -h db -p 5432 -U admin; do
  echo "Aguardando o banco de dados estar disponível..."
  sleep 2
done

echo "Banco de dados disponível, iniciando a aplicação..."
exec java -jar app.jar