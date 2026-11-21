import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import emprestimo.Emprestimo;
import emprestimo.LerArquivo;
import ordenacao.ArvoreABB;
import ordenacao.ArvoreAVL;
import ordenacao.Hash;
import ordenacao.Heapsort;
import ordenacao.Quicksort;
import ordenacao.QuicksortInsercao;

/**
 * Executa em sequência (sem menu) os 6 processos do trabalho para os 12
 * arquivos de empréstimos:
 *   1) Heapsort  2) Quicksort  3) Quicksort + Inserção Direta
 *   4) ABB       5) AVL        6) Hashing Encadeado
 *
 * Para cada método e cada arquivo: começa a contar o tempo, repete 5 vezes
 * (carregar o arquivo, ordenar/pesquisar, gravar o resultado), termina de
 * contar e divide por 5. O arquivo de saída é sobrescrito a cada repetição.
 */
public class Main {

    private static final String ENTRADA = "data/";
    private static final String SAIDA = "saida/";
    private static final String ARQUIVO_NOMES = "nomes.txt";
    private static final int REPETICOES = 5;

    // Sufixos dos 12 arquivos: emprestimo<sufixo>.txt
    private static final String[] SUFIXOS = {
        "1000alea", "1000inv", "1000ord",
        "5000alea", "5000inv", "5000ord",
        "10000alea", "10000inv", "10000ord",
        "50000alea", "50000inv", "50000ord"
    };

    // Métodos, na ordem do enunciado, com a pasta e o prefixo do arquivo de saída
    private static final String[] METODOS = {
        "Heapsort", "Quicksort", "Quicksort + Inserção", "ABB", "AVL", "Hashing"
    };
    private static final String[] PASTAS = { "Heap", "Quick", "QuickIns", "ABB", "AVL", "Hash" };
    private static final String[] PREFIXOS = { "heap", "quick", "QuickIns", "ABB", "AVL", "Hash" };

    public static void main(String[] args) {
        // Mostra as pastas que o programa está acessando
        mostrarCaminhos();
        // Encerra o programa se a pasta de entrada não existir
        verificarPastaEntrada();
        // Cria as pastas de saída de cada método
        criarPastasSaida();
        // Lê os nomes que serão pesquisados na ABB, AVL e Hash
        ArrayList<String> nomes = carregarNomes();
        // Roda os 6 métodos nos 12 arquivos e guarda o tempo médio de cada um
        double[][] tempos = executarTodos(nomes);
        // Grava a tabela de tempos em saida/tempos.txt
        gravarTempos(tempos);
    }

    /** Cria a pasta de saída de cada método (Heap, Quick, QuickIns, ABB, AVL, Hash). */
    private static void criarPastasSaida() {
        for (String pasta : PASTAS) {
            new File(SAIDA + pasta).mkdirs();
        }
    }

    /** Lê o arquivo com os nomes a pesquisar. */
    private static ArrayList<String> carregarNomes() {
        ArrayList<String> nomes = LerArquivo.lerNomes(ENTRADA + ARQUIVO_NOMES);
        System.out.println("Nomes a pesquisar: " + nomes.size());
        return nomes;
    }

    /**
     * Roda cada um dos 6 métodos em cada um dos 12 arquivos.
     *
     * @return tempos[metodo][arquivo] = tempo médio em ms
     */
    private static double[][] executarTodos(ArrayList<String> nomes) {
        double[][] tempos = new double[METODOS.length][SUFIXOS.length];

        for (int m = 0; m < METODOS.length; m++) {
            System.out.println("\n=== " + METODOS[m] + " ===");
            for (int a = 0; a < SUFIXOS.length; a++) {
                tempos[m][a] = medirTempoMedio(m, a, nomes);
            }
        }

        return tempos;
    }

    /**
     * Procedimento do enunciado para um método e um arquivo: começa a contar o
     * tempo, executa 5 vezes (carregar, processar, gravar), termina de contar e
     * divide por 5.
     *
     * @return tempo médio de uma execução, em ms
     */
    private static double medirTempoMedio(int metodo, int arquivo, ArrayList<String> nomes) {
        String entrada = ENTRADA + "emprestimo" + SUFIXOS[arquivo] + ".txt";
        String saida = SAIDA + PASTAS[metodo] + "/" + PREFIXOS[metodo] + SUFIXOS[arquivo] + ".txt";

        long inicio = System.nanoTime();
        for (int r = 0; r < REPETICOES; r++) {
            executar(metodo, entrada, saida, nomes);
        }
        long fim = System.nanoTime();

        double mediaMs = (fim - inicio) / (double) REPETICOES / 1_000_000.0;
        System.out.printf("%-12s %10.3f ms  -> %s%n", SUFIXOS[arquivo], mediaMs, saida);
        return mediaMs;
    }

    /**
     * Mostra as pastas que o programa está acessando
     */
    private static void mostrarCaminhos() {
        File pastaEntrada = new File(ENTRADA);
        System.out.println("Diretório de trabalho: " + new File("").getAbsolutePath());
        System.out.println("Pasta de entrada:      " + pastaEntrada.getAbsolutePath()
                + (pastaEntrada.isDirectory() ? "  (encontrada)" : "  (NÃO ENCONTRADA)"));
        System.out.println("Pasta de saída:        " + new File(SAIDA).getAbsolutePath());
    }

    /**
     * Encerra o programa se a pasta de entrada não existir: sem ela não há o que
     * processar
     */
    private static void verificarPastaEntrada() {
        File pastaEntrada = new File(ENTRADA);
        if (!pastaEntrada.isDirectory()) {
            System.err.println("ERRO: pasta de entrada não encontrada em " + pastaEntrada.getAbsolutePath());
            System.err.println("Confira se a pasta '" + ENTRADA + "' existe no projeto.");
            System.exit(1);
        }
    }

    /** Uma repetição do procedimento: carrega o arquivo, processa e grava a saída. */
    private static void executar(int metodo, String entrada, String saida, ArrayList<String> nomes) {
        ArrayList<Emprestimo> lista = LerArquivo.lerArquivo(entrada);

        switch (metodo) {
            case 0:
                Heapsort.sort(lista);
                gravarOrdenacao(saida, lista);
                break;
            case 1:
                Quicksort.sort(lista);
                gravarOrdenacao(saida, lista);
                break;
            case 2:
                QuicksortInsercao.sort(lista);
                gravarOrdenacao(saida, lista);
                break;
            case 3: {
                ArvoreABB abb = new ArvoreABB();
                for (Emprestimo e : lista) abb.inserir(e);
                abb.balancear();
                StringBuilder sb = new StringBuilder();
                for (String nome : nomes) formatarPesquisa(sb, nome, abb.pesquisar(nome));
                gravarTexto(saida, sb.toString());
                break;
            }
            case 4: {
                ArvoreAVL avl = new ArvoreAVL();
                for (Emprestimo e : lista) avl.inserir(e);
                StringBuilder sb = new StringBuilder();
                for (String nome : nomes) formatarPesquisa(sb, nome, avl.pesquisar(nome));
                gravarTexto(saida, sb.toString());
                break;
            }
            case 5: {
                Hash hash = new Hash(lista.size());
                for (Emprestimo e : lista) hash.inserir(e);
                StringBuilder sb = new StringBuilder();
                for (String nome : nomes) formatarPesquisa(sb, nome, hash.pesquisar(nome));
                gravarTexto(saida, sb.toString());
                break;
            }
            default:
                throw new IllegalArgumentException("Método inválido: " + metodo);
        }
    }

    /** Grava a lista ordenada no mesmo formato do arquivo de entrada. */
    private static void gravarOrdenacao(String arquivo, ArrayList<Emprestimo> lista) {
        StringBuilder sb = new StringBuilder();
        for (Emprestimo e : lista) {
            sb.append(e.toString()).append('\n');
        }
        gravarTexto(arquivo, sb.toString());
    }

    /** Formata o resultado da pesquisa de um nome conforme o modelo do enunciado. */
    private static void formatarPesquisa(StringBuilder sb, String nome, List<Emprestimo> encontrados) {
        sb.append("NOME: ").append(nome).append('\n');

        if (encontrados.isEmpty()) {
            sb.append("NÃO POSSUI EMPRÉSTIMOS\n\n");
            return;
        }

        for (Emprestimo e : encontrados) {
            sb.append("Empréstimo: ").append(e.getCodigo())
              .append(" - Livro: ").append(e.getTitulo())
              .append(" - Autor: ").append(e.getAutor()).append('\n');
            sb.append("Data do empréstimo: ").append(e.getDataEmprestimo())
              .append(" - Data de devolução: ").append(e.getDataDevolucao()).append("\n\n");
        }
        int total = encontrados.size();
        sb.append("TOTAL: ").append(total).append(total == 1 ? " empréstimo" : " empréstimos").append("\n\n");
    }

    /** Grava o texto no arquivo, sobrescrevendo o conteúdo anterior. */
    private static void gravarTexto(String arquivo, String texto) {
        try (BufferedWriter w = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo, false), StandardCharsets.UTF_8))) {
            w.write(texto);
        } catch (IOException e) {
            System.err.println("Erro ao gravar " + arquivo + ": " + e.getMessage());
        }
    }

    /** Grava saida/tempos.txt: uma tabela com o tempo médio (ms) de cada método para cada arquivo. */
    private static void gravarTempos(double[][] tempos) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tempo médio (ms) de 5 execuções — cada execução: carregar + ordenar/pesquisar + gravar\n\n");

        sb.append(String.format("%-22s", "Método"));
        for (String s : SUFIXOS) sb.append(String.format("%12s", s));
        sb.append('\n');

        for (int m = 0; m < METODOS.length; m++) {
            sb.append(String.format("%-22s", METODOS[m]));
            for (int a = 0; a < SUFIXOS.length; a++) {
                sb.append(String.format("%12.3f", tempos[m][a]));
            }
            sb.append('\n');
        }
        System.out.println("\n" + sb);
        gravarTexto(SAIDA + "tempos.txt", sb.toString());
        System.out.println("\nTabela de tempos gravada em " + SAIDA + "tempos.txt");
    }
}
