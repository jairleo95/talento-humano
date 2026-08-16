#!/bin/bash
set -e

# ============================================================
# Talento Humano — Script de instalación completa
# ============================================================
# Requisitos:
#   - Java 21 (OpenJDK)
#   - Node.js 18+
#   - Docker (para PostgreSQL)
#   - Git
# ============================================================

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

PROJECT_DIR="${HOME}/talento-humano"
REPO_URL="https://github.com/jairleo95/TALENTO_HUMANO.git"
PG_PORT=5434
PG_PASSWORD="${DB_PASSWORD:-$(openssl rand -base64 18)}"
JWT_SECRET="${JWT_SECRET:-$(openssl rand -hex 32)}"

echo -e "${BLUE}============================================${NC}"
echo -e "${BLUE}  Talento Humano — Instalación Automática  ${NC}"
echo -e "${BLUE}============================================${NC}"
echo ""

# --- Paso 1: Verificar requisitos ---
echo -e "${YELLOW}[1/7] Verificando requisitos...${NC}"

check_cmd() {
    if ! command -v "$1" &>/dev/null; then
        echo -e "${RED}✗ Falta: $1 — instálelo primero${NC}"
        exit 1
    fi
    echo -e "  ${GREEN}✓${NC} $1 ($($1 --version 2>&1 | head -1))"
}

check_cmd java
check_cmd node
check_cmd npm
check_cmd docker
check_cmd git

JAVA_VER=$(java -version 2>&1 | head -1 | grep -oP '\d+' | head -1)
if [ "$JAVA_VER" -lt 21 ]; then
    echo -e "${RED}✗ Se requiere Java 21+. Versión actual: $JAVA_VER${NC}"
    exit 1
fi
echo -e "  ${GREEN}✓${NC} Java $JAVA_VER (>= 21)"

echo ""

# --- Paso 2: Clonar / actualizar repositorio ---
echo -e "${YELLOW}[2/7] Preparando repositorio...${NC}"

if [ -d "$PROJECT_DIR" ]; then
    echo "  Actualizando repositorio existente..."
    git -C "$PROJECT_DIR" pull --rebase 2>/dev/null || true
else
    echo "  Clonando repositorio..."
    git clone "$REPO_URL" "$PROJECT_DIR" 2>/dev/null || {
        echo -e "${RED}✗ No se pudo clonar el repositorio.${NC}"
        echo "  Asegúrese de que el repo sea accesible o copie los archivos manualmente a:"
        echo "  $PROJECT_DIR"
        exit 1
    }
fi

cd "$PROJECT_DIR"
echo -e "  ${GREEN}✓${NC} Repositorio listo en $PROJECT_DIR"
echo ""

# --- Paso 3: PostgreSQL con Docker ---
echo -e "${YELLOW}[3/7] Iniciando PostgreSQL (Docker)...${NC}"

if docker ps --format '{{.Names}}' | grep -q 'talento-postgres'; then
    echo -e "  ${GREEN}✓${NC} PostgreSQL ya está corriendo"
else
    # Detener si hay otro contenedor en el puerto
    docker rm -f talento-postgres 2>/dev/null || true

    docker run -d \
        --name talento-postgres \
        -e POSTGRES_PASSWORD="$PG_PASSWORD" \
        -p $PG_PORT:5432 \
        postgres:16 2>/dev/null

    echo "  Esperando que PostgreSQL esté listo..."
    for i in $(seq 1 30); do
        if docker exec talento-postgres pg_isready -U postgres &>/dev/null; then
            break
        fi
        sleep 1
    done
    echo -e "  ${GREEN}✓${NC} PostgreSQL iniciado en puerto $PG_PORT"
fi

# Crear bases de datos y usuarios
echo "  Creando bases de datos y usuarios..."
docker exec talento-postgres psql -U postgres -c "SELECT 1" &>/dev/null || {
    echo -e "${RED}✗ No se pudo conectar a PostgreSQL${NC}"
    exit 1
}

for db in identity_db recruitment_db contract_db; do
    docker exec talento-postgres psql -U postgres -tc \
        "SELECT 1 FROM pg_database WHERE datname='$db'" | grep -q 1 || \
        docker exec talento-postgres psql -U postgres -c "CREATE DATABASE $db" &>/dev/null
done

for user in identity_user recruitment_user contract_user; do
    docker exec talento-postgres psql -U postgres -tc \
        "SELECT 1 FROM pg_roles WHERE rolname='$user'" | grep -q 1 || \
        docker exec talento-postgres psql -U postgres -c \
            "CREATE USER $user WITH ENCRYPTED PASSWORD '$PG_PASSWORD'" &>/dev/null
done

docker exec talento-postgres psql -U postgres -c \
    "GRANT ALL PRIVILEGES ON DATABASE identity_db TO identity_user" &>/dev/null
docker exec talento-postgres psql -U postgres -c \
    "GRANT ALL PRIVILEGES ON DATABASE recruitment_db TO recruitment_user" &>/dev/null
docker exec talento-postgres psql -U postgres -c \
    "GRANT ALL PRIVILEGES ON DATABASE contract_db TO contract_user" &>/dev/null

echo -e "  ${GREEN}✓${NC} Bases de datos y usuarios creados"
echo ""

# --- Paso 4: Compilar servicios backend ---
echo -e "${YELLOW}[4/7] Compilando microservicios...${NC}"

for svc in identity-service recruitment-service contract-service; do
    echo "  Compilando $svc..."
    (cd "$svc" && ./gradlew clean build -x test -q 2>/dev/null) || {
        echo -e "${RED}✗ Falló la compilación de $svc${NC}"
        exit 1
    }
    echo -e "    ${GREEN}✓${NC} $svc compilado"
done

echo "  Compilando gateway..."
(cd gth-gtw && ./gradlew clean build -x test -q 2>/dev/null) || {
    echo -e "${RED}✗ Falló la compilación del gateway${NC}"
    exit 1
}
echo -e "    ${GREEN}✓${NC} gth-gtw compilado"
echo ""

# --- Paso 5: Instalar frontend ---
echo -e "${YELLOW}[5/7] Instalando dependencias del frontend...${NC}"

cd "$PROJECT_DIR/gth-webapp"
npm install --silent 2>/dev/null || npm install
echo -e "  ${GREEN}✓${NC} Dependencias frontend instaladas"

# Build de producción
npm run build 2>/dev/null
echo -e "  ${GREEN}✓${NC} Frontend compilado (dist/)"
cd "$PROJECT_DIR"
echo ""

# --- Paso 6: Configurar variables de entorno ---
echo -e "${YELLOW}[6/7] Configurando variables de entorno...${NC}"

export JWT_SECRET="${JWT_SECRET:-$JWT_SECRET}"
export JWT_EXPIRATION="${JWT_EXPIRATION:-1h}"
export DB_PASSWORD="${DB_PASSWORD:-$PG_PASSWORD}"
export GTH_CRYPTO_SECRET="${GTH_CRYPTO_SECRET:-$(openssl rand -hex 32)}"
export CONFIG_SERVER_PASSWORD="${CONFIG_SERVER_PASSWORD:-$(openssl rand -base64 18)}"

echo "  JWT_SECRET: ${JWT_SECRET:0:15}..."
echo "  JWT_EXPIRATION: ${JWT_EXPIRATION}"
echo "  DB_PASSWORD: ${DB_PASSWORD:0:4}..."
echo "  GTH_CRYPTO_SECRET: ${GTH_CRYPTO_SECRET:0:4}..."
echo "  PG_PORT: $PG_PORT"
echo ""

# --- Paso 7: Iniciar servicios ---
echo -e "${YELLOW}[7/7] Iniciando servicios...${NC}"

# Matar procesos previos
for port in 8081 8082 8083 8085 4200; do
    lsof -ti :$port 2>/dev/null | xargs -r kill
done
sleep 2

# Iniciar servicios en orden
java -jar identity-service/build/libs/identity-service-0.0.1-SNAPSHOT.jar \
    > /tmp/identity-service.log 2>&1 &
echo "  identity-service (8081) PID: $!"

java -jar recruitment-service/build/libs/recruitment-service-0.0.1-SNAPSHOT.jar \
    > /tmp/recruitment-service.log 2>&1 &
echo "  recruitment-service (8082) PID: $!"

java -jar contract-service/build/libs/contract-service-0.0.1-SNAPSHOT.jar \
    > /tmp/contract-service.log 2>&1 &
echo "  contract-service (8083) PID: $!"

java -jar gth-gtw/build/libs/gth-gtw-0.0.1-SNAPSHOT.jar \
    > /tmp/gateway.log 2>&1 &
echo "  gateway (8085) PID: $!"

# Frontend (dev mode)
cd "$PROJECT_DIR/gth-webapp"
nohup npx vite --host --port 4200 > /tmp/vite.log 2>&1 &
echo "  frontend (4200) PID: $!"

echo ""
echo "  Esperando que los servicios inicien..."

# Health check
check_health() {
    local url=$1
    local name=$2
    for i in $(seq 1 30); do
        if curl -s -m 2 "$url" | grep -q "UP"; then
            echo -e "    ${GREEN}✓${NC} $name"
            return 0
        fi
        sleep 1
    done
    echo -e "    ${RED}✗${NC} $name no respondió"
    return 1
}

check_health "http://localhost:8081/actuator/health" "identity-service"
check_health "http://localhost:8082/actuator/health" "recruitment-service"
check_health "http://localhost:8083/actuator/health" "contract-service"
check_health "http://localhost:8085/actuator/health" "gateway"

echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}  Instalación completada exitosamente!      ${NC}"
echo -e "${GREEN}============================================${NC}"
echo ""
echo -e "  ${BLUE}Frontend:${NC}  http://localhost:4200"
echo -e "  ${BLUE}Gateway:${NC}   http://localhost:8085"
echo -e "  ${BLUE}Login:${NC}     admin / admin123"
echo ""
echo -e "  ${YELLOW}Logs:${NC}"
echo "    /tmp/identity-service.log"
echo "    /tmp/recruitment-service.log"
echo "    /tmp/contract-service.log"
echo "    /tmp/gateway.log"
echo "    /tmp/vite.log"
echo ""
echo -e "  ${YELLOW}Para detener:${NC}"
echo "    lsof -ti :8081 :8082 :8083 :8085 :4200 | xargs kill"
echo ""
echo -e "  ${YELLOW}Para reiniciar:${NC}"
echo "    bash $0"
