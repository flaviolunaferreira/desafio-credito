import os
from pathlib import Path
import fnmatch

# Configurações personalizáveis
DIRETORIO_RAIZ = "C:/Users/The Coyote/Desktop/Projetos/desafio-credito"  # Substitua pelo caminho real da sua pasta
ARQUIVO_SAIDA = "todos_os_arquivos.txt"
EXTENSOES_PERMITIDAS = [".java", ".xml", ".yml", ".sh", ".html", ".ts", ".json"]  # Extensões a serem incluídas
INCLUIR_CAMINHO_RELATIVO = True  # Define se o caminho relativo será incluído no nome do arquivo
CODIFICACOES_POSSIVEIS = ["utf-8", "latin-1"]  # Lista de codificações a tentar, em ordem de preferência
GITIGNORE_PATH = os.path.join(DIRETORIO_RAIZ, ".gitignore")  # Caminho para o arquivo .gitignore

def ler_gitignore():
    """Lê o arquivo .gitignore e retorna uma lista de padrões a serem ignorados."""
    ignore_patterns = []
    if os.path.exists(GITIGNORE_PATH):
        with open(GITIGNORE_PATH, "r", encoding="utf-8") as f:
            for line in f:
                line = line.strip()
                # Ignora linhas vazias, comentários e linhas inválidas
                if line and not line.startswith("#"):
                    ignore_patterns.append(line)
    return ignore_patterns

def deve_ignorar(caminho, ignore_patterns):
    """Verifica se um arquivo ou diretório deve ser ignorado com base nos padrões do .gitignore."""
    caminho_relativo = os.path.relpath(caminho, DIRETORIO_RAIZ).replace(os.sep, "/")
    
    for pattern in ignore_patterns:
        # Normaliza o padrão para usar barras consistentes
        pattern = pattern.replace(os.sep, "/").rstrip("/")
        # Verifica se o padrão é um diretório (termina com /) ou arquivo
        if pattern.endswith("/"):
            # Para diretórios, verifica se o caminho começa com o padrão
            if caminho_relativo.startswith(pattern) or f"{caminho_relativo}/".startswith(pattern):
                return True
        else:
            # Para arquivos, usa fnmatch para correspondência de padrões
            if fnmatch.fnmatch(caminho_relativo, pattern) or fnmatch.fnmatch(os.path.basename(caminho), pattern):
                return True
            # Verifica se o padrão corresponde a um diretório no caminho
            for part in caminho_relativo.split("/"):
                if fnmatch.fnmatch(part, pattern):
                    return True
    return False

def escrever_cabecalho(saida, caminho_arquivo):
    """Escreve o cabeçalho com o nome ou caminho do arquivo no arquivo de saída."""
    saida.write(f"\n{'=' * 50}\n")
    saida.write(f"Arquivo: {caminho_arquivo}\n")
    saida.write(f"{'=' * 50}\n\n")

def tentar_ler_arquivo(caminho_completo):
    """Tenta ler o conteúdo do arquivo com diferentes codificações."""
    for codificacao in CODIFICACOES_POSSIVEIS:
        try:
            with open(caminho_completo, "r", encoding=codificacao) as f:
                return f.read(), None
        except UnicodeDecodeError:
            continue
        except Exception as e:
            return None, f"Erro ao ler o arquivo: {e}"
    return None, "Erro: Não foi possível decodificar o arquivo com as codificações fornecidas"

def processar_arquivos(diretorio, arquivo_saida):
    """Processa todos os arquivos no diretório e subdiretórios, salvando no arquivo de saída."""
    # Carregar padrões do .gitignore
    ignore_patterns = ler_gitignore()
    
    # Contadores para estatísticas
    total_arquivos = 0
    erros = 0

    # Abrir o arquivo de saída no modo de escrita
    with open(arquivo_saida, "w", encoding="utf-8") as saida:
        # Escrever um cabeçalho inicial
        saida.write(f"Relatório de arquivos do diretório: {diretorio}\n")
        saida.write(f"Data de geração: {os.path.getctime(diretorio)}\n")
        saida.write(f"Extensões permitidas: {', '.join(EXTENSOES_PERMITIDAS)}\n\n")

        # Percorrer o diretório raiz e subpastas
        for root, dirs, files in os.walk(diretorio):
            # Filtrar diretórios a serem ignorados
            dirs[:] = [d for d in dirs if not deve_ignorar(os.path.join(root, d), ignore_patterns)]
            
            for arquivo in sorted(files):  # Ordenar arquivos para consistência
                # Verificar se o arquivo tem uma extensão permitida
                if any(arquivo.endswith(ext) for ext in EXTENSOES_PERMITIDAS):
                    caminho_completo = os.path.join(root, arquivo)
                    
                    # Verificar se o arquivo deve ser ignorado pelo .gitignore
                    if deve_ignorar(caminho_completo, ignore_patterns):
                        continue
                    
                    total_arquivos += 1
                    
                    # Determinar o nome a ser exibido (nome simples ou caminho relativo)
                    if INCLUIR_CAMINHO_RELATIVO:
                        caminho_relativo = os.path.relpath(caminho_completo, DIRETORIO_RAIZ)
                    else:
                        caminho_relativo = arquivo

                    # Escrever o cabeçalho do arquivo
                    escrever_cabecalho(saida, caminho_relativo)

                    # Tentar ler e escrever o conteúdo
                    conteudo, erro = tentar_ler_arquivo(caminho_completo)
                    if conteudo is not None:
                        saida.write(conteudo)
                        saida.write("\n\n")
                    else:
                        saida.write(f"{erro}\n\n")
                        erros += 1

        # Escrever resumo no final
        saida.write(f"{'=' * 50}\n")
        saida.write(f"Resumo:\n")
        saida.write(f"Total de arquivos processados: {total_arquivos}\n")
        saida.write(f"Arquivos com erro: {erros}\n")
        saida.write(f"Arquivos processados com sucesso: {total_arquivos - erros}\n")
        saida.write(f"{'=' * 50}\n")

    return total_arquivos, erros

def main():
    """Função principal para executar o script."""
    # Verificar se o diretório existe
    if not os.path.isdir(DIRETORIO_RAIZ):
        print(f"Erro: O diretório '{DIRETORIO_RAIZ}' não existe.")
        return

    print(f"Iniciando processamento do diretório: {DIRETORIO_RAIZ}")
    total, erros = processar_arquivos(DIRETORIO_RAIZ, ARQUIVO_SAIDA)

    if total == 0:
        print("Nenhum arquivo encontrado com as extensões especificadas.")
    else:
        print(f"Processamento concluído!")
        print(f"Total de arquivos encontrados: {total}")
        print(f"Arquivos com erro: {erros}")
        print(f"Todos os arquivos foram salvos em: {ARQUIVO_SAIDA}")

if __name__ == "__main__":
    main()