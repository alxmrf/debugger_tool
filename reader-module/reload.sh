#!/bin/bash

# --- Configurações ---
# Define o caminho absoluto ou relativo para o projeto Maven
PROJECT_PATH="$HOME/desenvolvimento/debbuger-tool/reader-module"
# Define o caminho do JAR gerado (geralmente dentro de target/)
JAR_PATH="${PROJECT_PATH}/target/reader-module-1.0-SNAPSHOT.jar"
# Classe principal e argumentos
MAIN_CLASS="knt.org.App"
APP_ARG="$HOME/teste/"

# 1. Navegar até o diretório do projeto
cd "$PROJECT_PATH" || { echo "Erro: Não foi possível acessar $PROJECT_PATH"; exit 1; }

# 2. Executar o Build
echo "Iniciando build: mvn clean install..."
if mvn clean install; then
    echo "Build concluído com sucesso."
else
    echo "Erro: Falha no build do Maven."
    exit 1
fi

# 3. Execução da aplicação
# O switch -cp (classpath) define onde a JVM buscará as classes e dependências.
echo "Executando $MAIN_CLASS..."
java -jar "$JAR_PATH" "$APP_ARG"
