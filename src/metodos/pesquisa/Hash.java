package metodos.pesquisa;

import java.util.ArrayList;
import java.util.LinkedList;

import emprestimo.Emprestimo;

/**
 * Hashing Encadeado cuja chave é o nome do usuário (chave secundária).
 *
 * Como funciona:
 *   - A tabela é um vetor de posições. A função de espalhamento (hash)
 *     transforma o nome num número, que é a posição onde ele fica guardado.
 *   - Nomes diferentes podem cair na mesma posição (colisão). Para tratar
 *     isso, cada posição do vetor é uma lista encadeada (LinkedList) de
 *     entradas: os nomes que colidem ficam um depois do outro nessa lista.
 *   - Cada entrada guarda um nome e TODOS os empréstimos desse nome. Assim,
 *     ao pesquisar um nome, todos os registros associados a ele são
 *     encontrados (item 6.2 do enunciado).
 *
 * Com a tabela do tamanho da quantidade de registros, as listas ficam curtas,
 * e inserção e pesquisa custam, em média, O(1).
 */
public class Hash {

    /** Entrada da tabela: um nome e a lista de empréstimos desse nome. */
    private static class Entrada {
        String nome;                                           // chave (nome do usuário)
        ArrayList<Emprestimo> emprestimos = new ArrayList<>(); // todos os empréstimos desse nome

        Entrada(String nome) {
            this.nome = nome;
        }
    }

    // final: são definidos uma única vez no construtor e nunca mais trocados
    private final LinkedList<Entrada>[] tabela; // vetor de listas encadeadas
    private final int tamanho;                  // quantidade de posições do vetor

    /**
     * Cria a tabela com todas as posições vazias.
     *
     * @param quantRegistros quantidade de registros que serão inseridos; o
     *                       tamanho da tabela é o primeiro primo maior ou igual
     *                       a esse valor (primo espalha melhor no resto da divisão).
     */
    @SuppressWarnings({"unchecked", "rawtypes"}) // o Java não permite criar vetor de tipo genérico sem esses avisos
    public Hash(int quantRegistros) {
        // Tamanho mínimo de 11, para a tabela nunca ficar pequena demais
        this.tamanho = proximoPrimo(Math.max(quantRegistros, 11));
        this.tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>(); // cada posição começa com uma lista vazia
        }
    }

    /**
     * Função de espalhamento feita sobre o nome do usuário (método polinomial):
     * h = (h * 31 + caractere) mod tamanho, para cada caractere do nome.
     * Calcular o mod a cada passo mantém o valor sempre positivo e sem estouro.
     *
     * @param nome Nome do usuário.
     * @return Posição da tabela, entre 0 e tamanho - 1.
     */
    private int hash(String nome) {
        long h = 0;
        for (int i = 0; i < nome.length(); i++) {
            h = (h * 31 + nome.charAt(i)) % tamanho;
        }
        return (int) h;
    }

    /**
     * Insere um empréstimo. Se o nome já existe, adiciona na lista desse nome;
     * senão, cria uma entrada nova na posição calculada pelo hash.
     *
     * @param e Empréstimo a ser inserido.
     */
    public void inserir(Emprestimo e) {
        String nome = e.getNome();
        LinkedList<Entrada> lista = tabela[hash(nome)]; // lista da posição desse nome

        // Procura o nome entre as entradas dessa posição
        for (Entrada entrada : lista) {
            if (entrada.nome.equals(nome)) {
                entrada.emprestimos.add(e); // nome repetido: mesmo grupo
                return;
            }
        }

        // Nome ainda não existe: cria a entrada com este primeiro empréstimo
        Entrada nova = new Entrada(nome);
        nova.emprestimos.add(e);
        lista.add(nova); // se já houver outros nomes aqui (colisão), encadeia na mesma posição
    }

    /**
     * Pesquisa um nome: calcula a posição com o hash e procura o nome só
     * na lista daquela posição.
     *
     * @param nome Nome do usuário pesquisado.
     * @return Todos os empréstimos do nome (lista vazia se não existir).
     */
    public ArrayList<Emprestimo> pesquisar(String nome) {
        for (Entrada entrada : tabela[hash(nome)]) {
            if (entrada.nome.equals(nome)) {
                return entrada.emprestimos;
            }
        }
        return new ArrayList<>();
    }

    /** Devolve o primeiro número primo maior ou igual a n. */
    private static int proximoPrimo(int n) {
        while (!ehPrimo(n)) n++;
        return n;
    }

    /** Verifica se n é primo testando divisores de 2 até a raiz quadrada de n. */
    private static boolean ehPrimo(int n) {
        if (n < 2) return false;
        for (int d = 2; (long) d * d <= n; d++) {
            if (n % d == 0) return false;
        }
        return true;
    }
}
