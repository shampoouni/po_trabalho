package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Árvore Binária de Busca (ABB) cuja chave é o nome do usuário (chave secundária).
 *
 * Regra da ABB: para cada nó, todos os nomes da subárvore esquerda são menores
 * que o nome do nó, e todos os da subárvore direita são maiores. Assim, para
 * procurar um nome, basta comparar e descer para um dos lados a cada passo.
 *
 * Nomes repetidos não são descartados: cada nó guarda a lista de todos os
 * empréstimos daquele nome (ver NoABB).
 *
 * Balanceamento (item 4.1 do enunciado): depois de carregar o arquivo,
 * chama-se balancear(), que reconstrói a árvore com altura mínima. Isso é
 * importante porque, nos arquivos ordenados ou invertidos, os nomes chegam em
 * sequência e a ABB vira uma "lista" (árvore degenerada), com pesquisa O(n).
 * Depois de balanceada, a pesquisa passa a ser O(log n).
 *
 * Inserção e caminhamento são iterativos (com laços, sem recursão) porque,
 * antes de balancear, a árvore degenerada pode ter milhares de níveis, o que
 * estouraria a pilha de chamadas se fosse feito com recursão.
 */
public class ArvoreABB {

    private NoABB raiz;   // raiz da árvore (null quando a árvore está vazia)
    private int quantNos; // quantidade de nomes distintos (nós)

    /** Cria uma árvore vazia. */
    public ArvoreABB() {
        this.raiz = null;
        this.quantNos = 0;
    }

    /**
     * Insere um empréstimo. Se o nome já existe, adiciona na lista daquele nó;
     * senão, cria um nó novo no lugar certo.
     *
     * @param e Empréstimo a ser inserido.
     */
    public void inserir(Emprestimo e) {
        // Árvore vazia: o primeiro empréstimo vira a raiz
        if (this.raiz == null) {
            this.raiz = new NoABB(e);
            quantNos++;
            return;
        }

        // Desce a partir da raiz até achar o nome ou uma posição vazia
        NoABB atual = this.raiz;
        while (true) {
            int cmp = e.getNome().compareTo(atual.getNome());
            if (cmp == 0) {
                atual.adicionar(e); // nome repetido: guarda no mesmo nó
                return;
            } else if (cmp < 0) {
                // Nome menor: vai para a esquerda; se não houver filho, insere ali
                if (atual.getEsq() == null) {
                    atual.setEsq(new NoABB(e));
                    quantNos++;
                    return;
                }
                atual = atual.getEsq();
            } else {
                // Nome maior: vai para a direita; se não houver filho, insere ali
                if (atual.getDir() == null) {
                    atual.setDir(new NoABB(e));
                    quantNos++;
                    return;
                }
                atual = atual.getDir();
            }
        }
    }

    /**
     * Pesquisa pelo nome e devolve todos os empréstimos desse usuário
     * (lista vazia se o nome não existir).
     *
     * @param nome Nome do usuário pesquisado.
     * @return Lista com todos os empréstimos do nome.
     */
    public ArrayList<Emprestimo> pesquisar(String nome) {
        NoABB atual = this.raiz;
        while (atual != null) {
            int cmp = nome.compareTo(atual.getNome());
            if (cmp == 0) {
                return atual.getEmprestimos(); // encontrou o nó do nome
            }
            // Nome menor: procura à esquerda; maior: procura à direita
            atual = (cmp < 0) ? atual.getEsq() : atual.getDir();
        }
        return new ArrayList<>(); // chegou numa posição vazia: o nome não existe
    }

    /**
     * Balanceia a árvore: coloca os nós em ordem (caminhamento central) num
     * vetor e reconstrói a árvore escolhendo sempre o nó do meio como raiz.
     * Os nós são reaproveitados, então cada nome continua com a sua lista.
     */
    public void balancear() {
        ArrayList<NoABB> nos = new ArrayList<>(quantNos); // nós em ordem crescente de nome

        // Caminhamento central iterativo (esquerda, nó, direita) usando um ArrayList como pilha
        ArrayList<NoABB> pilha = new ArrayList<>();
        NoABB atual = this.raiz;
        while (atual != null || !pilha.isEmpty()) {
            // Desce o máximo possível para a esquerda, empilhando os nós do caminho
            while (atual != null) {
                pilha.add(atual);
                atual = atual.getEsq();
            }
            // Desempilha o menor nó ainda não visitado e o coloca no vetor
            atual = pilha.remove(pilha.size() - 1);
            nos.add(atual);
            // Depois do nó, visita a sua subárvore direita
            atual = atual.getDir();
        }

        // Reconstrói a árvore balanceada a partir do vetor ordenado
        this.raiz = construir(nos, 0, nos.size() - 1);
    }

    /**
     * Monta a subárvore balanceada com os nós nos[inicio..fim]: o nó do meio
     * vira a raiz, a metade da esquerda vira a subárvore esquerda e a metade
     * da direita vira a subárvore direita. Profundidade da recursão ~ log2(n).
     *
     * @param nos    Nós em ordem crescente de nome.
     * @param inicio Índice inicial do trecho.
     * @param fim    Índice final do trecho.
     * @return A raiz da subárvore montada (null se o trecho estiver vazio).
     */
    private NoABB construir(ArrayList<NoABB> nos, int inicio, int fim) {
        if (inicio > fim) return null; // trecho vazio: sem nó

        int meio = (inicio + fim) / 2;
        NoABB no = nos.get(meio);
        no.setEsq(construir(nos, inicio, meio - 1)); // metade menor à esquerda
        no.setDir(construir(nos, meio + 1, fim));    // metade maior à direita
        return no;
    }

    /** Altura da árvore (útil para conferir o balanceamento). */
    public int altura() {
        return altura(this.raiz);
    }

    /** Altura de uma subárvore: -1 se vazia, senão 1 + a altura do filho mais alto. */
    private int altura(NoABB no) {
        if (no == null) return -1;
        return 1 + Math.max(altura(no.getEsq()), altura(no.getDir()));
    }
}
