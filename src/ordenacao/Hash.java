package ordenacao;

import java.util.ArrayList;
import java.util.LinkedList;

import emprestimo.Emprestimo;

/**
 * Hashing Encadeado cuja chave é o nome do usuário (chave secundária).
 *
 * Cada posição do vetor é uma lista encadeada (LinkedList) de entradas; cada
 * entrada guarda um nome e TODOS os empréstimos desse nome. Assim, ao pesquisar
 * um nome, todos os registros associados a ele são encontrados.
 */
public class Hash {

    /** Entrada da tabela: um nome e a lista de empréstimos desse nome. */
    private static class Entrada {
        String nome;
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        Entrada(String nome) {
            this.nome = nome;
        }
    }

    private LinkedList<Entrada>[] tabela;
    private int tamanho;

    /**
     * @param quantRegistros quantidade de registros que serão inseridos; o
     *                       tamanho da tabela é o primeiro primo maior ou igual
     *                       a esse valor (primo espalha melhor no resto da divisão).
     */
    @SuppressWarnings("unchecked")
    public Hash(int quantRegistros) {
        this.tamanho = proximoPrimo(Math.max(quantRegistros, 11));
        this.tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    /**
     * Função de espalhamento feita sobre o nome do usuário (método polinomial):
     * h = (h * 31 + caractere) mod tamanho, para cada caractere do nome.
     * Calcular o mod a cada passo mantém o valor sempre positivo e sem estouro.
     */
    private int hash(String nome) {
        long h = 0;
        for (int i = 0; i < nome.length(); i++) {
            h = (h * 31 + nome.charAt(i)) % tamanho;
        }
        return (int) h;
    }

    /** Insere um empréstimo. Se o nome já existe, adiciona na lista desse nome. */
    public void inserir(Emprestimo e) {
        String nome = e.getNome();
        LinkedList<Entrada> lista = tabela[hash(nome)];

        for (Entrada entrada : lista) {
            if (entrada.nome.equals(nome)) {
                entrada.emprestimos.add(e); // nome repetido: mesmo grupo
                return;
            }
        }

        Entrada nova = new Entrada(nome);
        nova.emprestimos.add(e);
        lista.add(nova); // colisão: encadeia na mesma posição
    }

    /** Devolve todos os empréstimos do nome (lista vazia se não existir). */
    public ArrayList<Emprestimo> pesquisar(String nome) {
        for (Entrada entrada : tabela[hash(nome)]) {
            if (entrada.nome.equals(nome)) {
                return entrada.emprestimos;
            }
        }
        return new ArrayList<>();
    }

    private static int proximoPrimo(int n) {
        while (!ehPrimo(n)) n++;
        return n;
    }

    private static boolean ehPrimo(int n) {
        if (n < 2) return false;
        for (int d = 2; (long) d * d <= n; d++) {
            if (n % d == 0) return false;
        }
        return true;
    }
}
