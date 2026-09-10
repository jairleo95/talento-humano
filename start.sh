#!/usr/bin/env bash
set -e

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

# Cargar variables de entorno desde .env
if [ -f .env ]; then
  set -a
  source .env
  set +a
fi

echo "Iniciando PostgreSQL en puerto ${POSTGRES_PORT:-5435}..."
docker compose up -d postgres

echo "Esperando disponibilidad de PostgreSQL..."
for i in {1..30}; do
  if docker exec talento-postgres pg_isready -U postgres -d postgres >/dev/null 2>&1; then
    echo "PostgreSQL activo."
    break
  fi
  sleep 1
done

# Detener procesos previos en los puertos
for port in 8081 8082 8083 8085 4200; do
  lsof -ti :$port 2>/dev/null | xargs -r kill -9 2>/dev/null || true
done
sleep 1

mkdir -p /tmp/talento-humano-logs

echo "Iniciando identity-service (:8081)..."
nohup java -jar identity-service/build/libs/identity-service-0.0.1-SNAPSHOT.jar \
  > /tmp/talento-humano-logs/identity-service.log 2>&1 &

echo "Iniciando recruitment-service (:8082)..."
nohup java -jar recruitment-service/build/libs/recruitment-service-0.0.1-SNAPSHOT.jar \
  > /tmp/talento-humano-logs/recruitment-service.log 2>&1 &

echo "Iniciando contract-service (:8083)..."
nohup java -jar contract-service/build/libs/contract-service-0.0.1-SNAPSHOT.jar \
  > /tmp/talento-humano-logs/contract-service.log 2>&1 &

echo "Iniciando gth-gtw (:8085)..."
nohup java -jar gth-gtw/build/libs/gth-gtw-0.0.1-SNAPSHOT.jar \
  > /tmp/talento-humano-logs/gateway.log 2>&1 &

echo "Iniciando gth-webapp (:4200)..."
(cd gth-webapp && nohup npx vite --host 0.0.0.0 --port 4200 > /tmp/talento-humano-logs/vite.log 2>&1 &)

echo "Esperando que los servicios respondan..."
for i in {1..30}; do
  if curl -s http://localhost:8081/actuator/health | grep -q "UP" && \
     curl -s http://localhost:8082/actuator/health | grep -q "UP" && \
     curl -s http://localhost:8083/actuator/health | grep -q "UP" && \
     curl -s http://localhost:8085/actuator/health | grep -q "UP" && \
     curl -s http://localhost:4200/ > /dev/null 2>&1; then
    echo "============================================="
    echo "Todos los servicios están arriba y operando:"
    echo "  - PostgreSQL:       puerto ${POSTGRES_PORT:-5435}"
    echo "  - identity-service:  http://localhost:8081"
    echo "  - recruitment-service: http://localhost:8082"
    echo "  - contract-service: http://localhost:8083"
    echo "  - Gateway:          http://localhost:8085"
    echo "  - Frontend:         http://localhost:4200"
    echo "============================================="
    exit 0
  fi
  sleep 1
done

echo "Advertencia: Algunos servicios pueden tardar unos segundos adicionales en completar su inicio."
