import java.io.*;
import java.util.*;

import reserva.*;
import ordenacao.*;

public class Main {

    // Pastas do projeto: entrada com os arquivos dados, saida com tudo que o programa gera
    private static final String ENTRADA = "entrada/";
    private static final String SAIDA = "saida/";

    // Método principal que inicia o processo de ordenação e medição do tempo de execução
    public static void main(String[] args) {  
        // Lista dos arquivos para serem processados
        // Arquivos de entrada (nomes exatamente como no diretório)
        String[] arquivos = {
            "Reserva1000alea.txt", "Reserva1000inv.txt", "Reserva1000ord.txt",
            "Reserva5000alea.txt", "Reserva5000inv.txt", "Reserva5000ord.txt",
            "Reserva10000alea.txt", "Reserva10000inv.txt", "Reserva10000ord.txt",
            "Reserva50000alea.txt", "Reserva50000inv.txt", "Reserva50000ord.txt"
        };

        // Criar os diretórios para salvar os resultados
        criarDiretorio(SAIDA + "Heap");
        criarDiretorio(SAIDA + "Quick");
        criarDiretorio(SAIDA + "QuickInsercao");
        criarDiretorio(SAIDA + "ABB");
        criarDiretorio(SAIDA + "AVL");
        criarDiretorio(SAIDA + "Hash");

        // Processamento e ordenação com Heapsort — retorna média por arquivo (ms)
        Map<String, Double> temposHeap = processarHeap(arquivos);

        // Processamento e ordenação com Quicksort
        Map<String, Double> temposQuick = processarQuick(arquivos);

        // Processamento e ordenação com Quicksort + Inserção Direta
        Map<String, Double> temposQuickInsercao = processarQuickInsercao(arquivos);

        // Processamento com ABB (pesquisa por nome)
        Map<String, Double> temposABB = processarABB(arquivos);

        // Processamento com AVL (pesquisa por nome)
        Map<String, Double> temposAVL = processarAVL(arquivos);

        // Processamento com Hash
        Map<String, Double> temposHash = processarHash(arquivos);

        // Imprimir resumo dos tempos médios por arquivo para cada algoritmo
        System.out.println("Resumo de tempos médios (ms) por arquivo e algoritmo:");
        System.out.println("Veja saida/tempos.txt para tabela detalhada.");

        // Mostrar resumo no console e gravar arquivo de resumo com os tempos por arquivo
        imprimirResumoConsole("Heapsort", temposHeap);
        imprimirResumoConsole("Quicksort", temposQuick);
        imprimirResumoConsole("Quicksort + Inserção Direta", temposQuickInsercao);
        imprimirResumoConsole("ABB", temposABB);
        imprimirResumoConsole("AVL", temposAVL);
        imprimirResumoConsole("Hash", temposHash);

        gravarResumoTempos(temposHeap, temposQuick, temposQuickInsercao, temposABB, temposAVL, temposHash);
    }

    private static void imprimirResumoConsole(String nome, Map<String, Double> mapa) {
        System.out.println("--- " + nome + " ---");
        for (var e : mapa.entrySet()) {
            System.out.println(String.format("%s -> %.3f ms", e.getKey(), e.getValue()));
        }
    }

    // Processa os arquivos usando o algoritmo Heapsort e grava os resultados
    private static Map<String, Double> processarHeap(String[] arquivos) {
        Map<String, Double> medias = new LinkedHashMap<>();

        for (String arquivo : arquivos) {
            // Carregar o arquivo UMA vez, fora das repetições (evita I/O ser contabilizado)
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' está vazio ou não foi encontrado — saltando medição (HeapSort)");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("Heapsort — arquivo '" + arquivo + "' carregado com " + original.size() + " registros");

            long soma = 0;
            for (int i = 0; i < 5; i++) { // Repetir o processo 5 vezes por arquivo
                ArrayList<Reserva> reservas = new ArrayList<>(original); // copia para garantir estado limpo
                long inicio = System.nanoTime();

                // Ordenar as reservas com Heapsort (medimos apenas o algoritmo)
                Heapsort.sort(reservas);

                long fim = System.nanoTime();
                long iterNanos = fim - inicio;
                soma += iterNanos;

                String arquivoResultadoHeap = SAIDA + "Heap/Heap" + arquivo;
                // gravar resultado final (sobrepõe nas repetições)
                if (i == 0) gravarResultado(arquivoResultadoHeap, reservas);
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }
            double mediaMs = (soma / 5) / 1_000_000.0; // converte para ms (double)
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("Heapsort — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Processa os arquivos usando o algoritmo Quicksort e grava os resultados
    private static Map<String, Double> processarQuick(String[] arquivos) {
        Map<String, Double> medias = new LinkedHashMap<>();

        for (String arquivo : arquivos) {
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' está vazio ou não foi encontrado — saltando medição (QuickSort)");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("Quicksort — arquivo '" + arquivo + "' carregado com " + original.size() + " registros");

            long soma = 0;
            for (int i = 0; i < 5; i++) {
                ArrayList<Reserva> reservas = new ArrayList<>(original);
                long inicio = System.nanoTime();

                // Ordenar as reservas com Quicksort
                Quicksort.sort(reservas);

                long fim = System.nanoTime();
                long iterNanos = fim - inicio;
                soma += iterNanos;

                String arquivoResultadoQuick = SAIDA + "Quick/Quick" + arquivo;
                if (i == 0) gravarResultado(arquivoResultadoQuick, reservas);
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }
            double mediaMs = (soma / 5) / 1_000_000.0;
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("Quicksort — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Processa os arquivos usando o algoritmo Quicksort com Inserção Direta e grava
    // os resultados
    private static Map<String, Double> processarQuickInsercao(String[] arquivos) {
        Map<String, Double> medias = new LinkedHashMap<>();

        for (String arquivo : arquivos) {
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' está vazio ou não foi encontrado — saltando medição (Quick+Insercao)");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("Quicksort+Insercao — arquivo '" + arquivo + "' carregado com " + original.size() + " registros");

            long soma = 0;
            for (int i = 0; i < 5; i++) {
                ArrayList<Reserva> reservas = new ArrayList<>(original);
                long inicio = System.nanoTime();

                // Ordenar as reservas com Quicksort + Inserção Direta
                QuicksortInsercao.sort(reservas);

                long fim = System.nanoTime();
                long iterNanos = fim - inicio;
                soma += iterNanos;

                String arquivoResultadoQuickInsercao = SAIDA + "QuickInsercao/QuickInsercao" + arquivo;
                if (i == 0) gravarResultado(arquivoResultadoQuickInsercao, reservas);
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }
            double mediaMs = (soma / 5) / 1_000_000.0;
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("Quicksort+Insercao — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Processa os arquivos usando ABB (Árvore Binária de Busca) e grava os
    // resultados
    private static Map<String, Double> processarABB(String[] arquivos) {
        List<String> nomes = lerNomes("nome.txt");
        Map<String, Double> medias = new LinkedHashMap<>();

        if (nomes == null || nomes.isEmpty()) {
            System.err.println("AVISO: lista de nomes vazia — verificando se 'nome.txt' foi localizado corretamente.");
        }

        for (String arquivo : arquivos) {
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' vazio/nao encontrado — saltando ABB.");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("ABB — arquivo '" + arquivo + "' carregado com " + original.size() + " registros; nomes: " + nomes.size());

            long soma = 0;
            for (int i = 0; i < 5; i++) { // Repetir o processo 5 vezes
                long inicio = System.nanoTime();

                // cria a ABB e insere
                ArvoreABB abb = new ArvoreABB();
                for (Reserva reserva : original) abb.inserir(reserva);

                // Pesquisa e grava
                String arquivoResultadoABB = SAIDA + "ABB/ABB" + arquivo;
                if (i == 0) {
                    File f = new File(arquivoResultadoABB);
                    if (f.exists()) f.delete();
                }
                for (String nome : nomes) {
                    List<Reserva> resultadoPesquisa = abb.pesquisar(nome);
                    gravarResultadoPesquisa(arquivoResultadoABB, nome, resultadoPesquisa);
                }

                long fim = System.nanoTime();
                long iterNanos = fim - inicio;
                soma += iterNanos;
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }

            double mediaMs = (soma / 5) / 1_000_000.0;
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("ABB — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Processa os arquivos usando AVL (Árvore Balanceada) e grava os resultados
    private static Map<String, Double> processarAVL(String[] arquivos) {
        List<String> nomes = lerNomes("nome.txt");
        Map<String, Double> medias = new LinkedHashMap<>();

        if (nomes == null || nomes.isEmpty()) {
            System.err.println("AVISO: lista de nomes vazia — verificando se 'nome.txt' foi localizado corretamente.");
        }

        for (String arquivo : arquivos) {
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' vazio/nao encontrado — saltando AVL.");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("AVL — arquivo '" + arquivo + "' carregado com " + original.size() + " registros; nomes: " + nomes.size());

            long soma = 0;
            for (int i = 0; i < 5; i++) { // Repetir o processo 5 vezes
                long inicio = System.nanoTime(); // Começa a contagem do tempo

                // criar e preencher a Árvore AVL
                ArvoreAVL arvoreAVL = new ArvoreAVL();
                for (Reserva reserva : original) arvoreAVL.inserir(reserva);

                // Pesquisa e grava
                String arquivoResultadoAVL = SAIDA + "AVL/AVL" + arquivo;
                if (i == 0) {
                    File f = new File(arquivoResultadoAVL);
                    if (f.exists()) f.delete();
                }
                for (String nome : nomes) {
                    List<Reserva> resultadoPesquisa = arvoreAVL.pesquisar(nome);
                    gravarResultadoPesquisa(arquivoResultadoAVL, nome, resultadoPesquisa);
                }

                long fim = System.nanoTime(); // Termina a contagem do tempo
                long iterNanos = fim - inicio;
                soma += iterNanos;
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }
            double mediaMs = (soma / 5) / 1_000_000.0;
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("AVL — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Processa os arquivos usando Hash e grava os resultados
    private static Map<String, Double> processarHash(String[] arquivos) {
        List<String> nomes = lerNomes("nome.txt");
        Map<String, Double> medias = new LinkedHashMap<>();

        if (nomes == null || nomes.isEmpty()) {
            System.err.println("AVISO: lista de nomes vazia — verificando se 'nome.txt' foi localizado corretamente.");
        }

        for (String arquivo : arquivos) {
            ArrayList<Reserva> original = LerArquivo.lerArquivo(resolvePath(arquivo));
            if (original == null || original.isEmpty()) {
                System.err.println("AVISO: arquivo '" + arquivo + "' vazio/nao encontrado — saltando Hash.");
                medias.put(arquivo, 0.0);
                continue;
            }

            System.out.println("Hash — arquivo '" + arquivo + "' carregado com " + original.size() + " registros; nomes: " + nomes.size());

            long soma = 0;
            for (int i = 0; i < 5; i++) { // Repetir o processo 5 vezes
                long inicio = System.nanoTime(); // Começa a contagem do tempo

                // construir hash e inserir reservas
                Hash tabelaHash = new Hash(Math.max(original.size(), 100));
                for (Reserva reserva : original) tabelaHash.inserir(reserva);

                // Realizar a pesquisa de nomes na tabela Hash e gravar os resultados
                String arquivoResultadoHash = SAIDA + "Hash/Hash" + arquivo;
                if (i == 0) {
                    File f = new File(arquivoResultadoHash);
                    if (f.exists()) f.delete();
                }
                for (String nome : nomes) {
                    List<Reserva> resultadoPesquisa = tabelaHash.get(nome);
                    gravarResultadoPesquisa(arquivoResultadoHash, nome, resultadoPesquisa);
                }

                long fim = System.nanoTime(); // Termina a contagem do tempo
                long iterNanos = fim - inicio;
                soma += iterNanos;
                System.out.println(String.format("  run %d: %.3f ms", i+1, iterNanos/1_000_000.0));
            }
            double mediaMs = (soma / 5) / 1_000_000.0;
            medias.put(arquivo, mediaMs);
            System.out.println(String.format("Hash — média %s: %.3f ms", arquivo, mediaMs));
        }

        return medias;
    }

    // Método para criar diretórios onde os arquivos de resultados serão salvos
    private static void criarDiretorio(String nomeDiretorio) {
        File pasta = new File(nomeDiretorio);
        if (!pasta.exists()) {
            boolean success = pasta.mkdirs();
            if (success) {
                System.out.println("Pasta '" + nomeDiretorio + "' criada com sucesso.");
            } else {
                System.out.println("Erro ao criar a pasta '" + nomeDiretorio + "'.");
            }
        } else {
            System.out.println("Pasta '" + nomeDiretorio + "' já existe.");
        }
    }

    // Os arquivos de entrada ficam sempre na pasta entrada/
    private static String resolvePath(String nomeArquivo) {
        return ENTRADA + nomeArquivo;
    }

    // Método para gravar o resultado da ordenação em um arquivo
    private static void gravarResultado(String nomeArquivo, ArrayList<Reserva> reservas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Reserva reserva : reservas) {
                // escreve a reserva completa no formato padrão (codigo;nome;voo;data;assento)
                writer.write(reserva.toString() + "\n");
            }
            System.out.println("Resultado gravado em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo: " + nomeArquivo);
        }
    }

    // Método para gravar o resultado da pesquisa no arquivo
    private static void gravarResultadoPesquisa(String nomeArquivo, String nome, List<Reserva> resultadoPesquisa) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write("NOME " + nome + ":\n");

            if (resultadoPesquisa.isEmpty()) {
                writer.write("NÃO TEM RESERVA\n");
            } else {
                for (Reserva reserva : resultadoPesquisa) {
                    // detalha codigo e nome para que a reserva possa ser identificada
                    writer.write("Reserva: " + reserva.getCodigo() + " | Nome: " + reserva.getNome()
                            + " | voo: " + reserva.getVoo() + " | Data: " + reserva.getData()
                            + " | assento: " + reserva.getInfo() + "\n");
                }
                writer.write("TOTAL: " + resultadoPesquisa.size() + " reservas\n");
            }

            System.out.println("Resultado da pesquisa para o NOME '" + nome + "' gravado no arquivo " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    // Lê os nomes a serem pesquisados (um por linha) da pasta entrada/
    private static List<String> lerNomes(String nomeArquivo) {
        List<String> nomes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENTRADA + nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) nomes.add(linha.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler " + ENTRADA + nomeArquivo + ": " + e.getMessage());
        }
        return nomes;
    }

    // Grava um resumo tabulado com os tempos médios por arquivo para cada algoritmo
    private static void gravarResumoTempos(Map<String, Double> heap,
                                          Map<String, Double> quick,
                                          Map<String, Double> quickInsercao,
                                          Map<String, Double> abb,
                                          Map<String, Double> avl,
                                          Map<String, Double> hash) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAIDA + "tempos.txt"))) {
            writer.write("Heapsort:\n");
            for (var e : heap.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }
            writer.write("\nQuicksort:\n");
            for (var e : quick.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }
            writer.write("\nQuicksort + Inserção Direta:\n");
            for (var e : quickInsercao.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }
            writer.write("\nABB:\n");
            for (var e : abb.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }
            writer.write("\nAVL:\n");
            for (var e : avl.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }
            writer.write("\nHash:\n");
            for (var e : hash.entrySet()) {
                writer.write(e.getKey() + " -> " + String.format("%.3f", e.getValue()) + " ms\n");
            }

            System.out.println("Arquivo de resumo 'saida/tempos.txt' gravado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao gravar tempos.txt: " + e.getMessage());
        }
    }
}
