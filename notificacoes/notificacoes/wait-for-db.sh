#!/bin/bash
set -e

until pg_isready -h db -p 5432 -U admin; do
  echo "Aguardando o banco de dados estar disponível..."
  sleep 2
done

echo "Banco de dados disponível, iniciando a aplicação..."
exec java -jar app.jar