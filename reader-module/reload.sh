#!/bin/bash

# --- Configurações ---
PROJECT_PATH="/home/kinsa/desenvolvimento/debugger/reader-module/"
TARGET_PATH="${PROJECT_PATH}/target"
JAR_PATH="${TARGET_PATH}/reader-module-1.0-SNAPSHOT.jar"
MAIN_CLASS="knt.org.App"
APP_ARG="/home/kinsa/teste/"

# --- 1. Preparação do RAM Disk (tmpfs) ---
# Verifica se o diretório target já é um ponto de montagem tmpfs
if ! mountpoint -q "$TARGET_PATH"; then
    echo "Configurando RAM Disk de 1GB em $TARGET_PATH..."
    mkdir -p "$TARGET_PATH"
    # Monta 1GB de RAM no diretório target para eliminar I/O de disco
    sudo mount -t tmpfs -o size=1G tmpfs "$TARGET_PATH"
    # Ajusta permissões para o seu usuário
    sudo chown $(whoami):$(whoami) "$TARGET_PATH"
fi

# --- 2. Execução com Prioridade de Processo ---
# nice -n -20: Prioridade máxima de CPU (Scheduler do Kernel)
# ionice -c 1: Prioridade de I/O em tempo real (Realtime Class)
echo "Iniciando build de alta performance..."

cd "$PROJECT_PATH" || exit 1

if sudo nice -n -20 ionice -c 1 mvnd package \
    -T 1C \
    -Dmaven.test.skip=true \
    -Dmaven.compiler.useIncrementalCompilation=true \
    -nsu; then
    echo "Build concluído em RAM."
else
    echo "Erro no build."
    exit 1
fi

# --- 3. Execução da JVM Otimizada ---
# -XX:+TieredCompilation: Acelera o JIT para código de curta duração
# -Xms1G -Xmx1G: Fixa o Heap para evitar overhead de redimensionamento
# -XX:+UseG1GC: Coletor de lixo moderno e eficiente para dev
echo "Executando $MAIN_CLASS..."
java -XX:+TieredCompilation -Xms1G -Xmx1G -jar "$JAR_PATH" "$APP_ARG"