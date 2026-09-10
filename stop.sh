#!/usr/bin/env bash
echo "Deteniendo servicios de talento-humano..."
for port in 8081 8082 8083 8085 4200; do
  lsof -ti :$port 2>/dev/null | xargs -r kill -9 2>/dev/null || true
done
echo "Servicios en puertos 8081, 8082, 8083, 8085, 4200 detenidos."
