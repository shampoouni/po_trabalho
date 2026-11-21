package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Árvore Binária de Busca cuja chave é o nome do usuário (chave secundária).
 *
 * Nomes repetidos não são descartados: cada nó guarda a lista de todos os
 * empréstimos daquele nome. Depois de carregar o arquivo, chama-se balancear(),
 * que reconstrói a árvore com altura mínima.
 *
 * Inserção e caminhamento são iterativos (sem recursão) porque, antes de
 * balancear, um arquivo ordenado por nome gera uma árvore degenerada (uma
 * "lista" com milhares de níveis), o que estouraria a pilha com recursão.
 */
public class ArvoreABB {

    private NoABB raiz;
    private int quantNos; // quantidade de nomes distintos (nós)

    public ArvoreABB() {
        this.raiz = null;
        this.quantNos = 0;
    }

    /** Insere um empréstimo. Se o nome já existe, adiciona na lista daquele nó. */
    public void inserir(Emprestimo e) {
        if (raiz == null) {
            raiz = new NoABB(e);
            quantNos++;
            return;
        }

        NoABB atual = raiz;
        while (true) {
            int cmp = e.getNome().compareTo(atual.getNome());
            if (cmp == 0) {
                atual.adicionar(e); // nome repetido: guarda no mesmo nó
                return;
            } else if (cmp < 0) {
                if (atual.getEsq() == null) {
                    atual.setEsq(new NoABB(e));
                    quantNos++;
                    return;
                }
                atual = atual.getEsq();
            } else {
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
     */
    public ArrayList<Emprestimo> pesquisar(String nome) {
        NoABB atual = raiz;
        while (atual != null) {
            int cmp = nome.compareTo(atual.getNome());
            if (cmp == 0) {
                return atual.getEmprestimos();
            }
            atual = (cmp < 0) ? atual.getEsq() : atual.getDir();
        }
        return new ArrayList<>();
    }

    /**
     * Balanceia a árvore: coloca os nós em ordem (caminhamento central) num
     * vetor e reconstrói a árvore escolhendo sempre o nó do meio como raiz.
     * Os nós são reaproveitados, então cada nome continua com a sua lista.
     */
    public void balancear() {
        ArrayList<NoABB> nos = new ArrayList<>(quantNos);

        // Caminhamento central iterativo usando um ArrayList como pilha
        ArrayList<NoABB> pilha = new ArrayList<>();
        NoABB atual = raiz;
        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.add(atual);
                atual = atual.getEsq();
            }
            atual = pilha.remove(pilha.size() - 1);
            nos.add(atual);
            atual = atual.getDir();
        }

        raiz = construir(nos, 0, nos.size() - 1);
    }

    /** Monta a subárvore balanceada com os nós nos[inicio..fim]. Profundidade ~ log2(n). */
    private NoABB construir(ArrayList<NoABB> nos, int inicio, int fim) {
        if (inicio > fim) return null;

        int meio = (inicio + fim) / 2;
        NoABB no = nos.get(meio);
        no.setEsq(construir(nos, inicio, meio - 1));
        no.setDir(construir(nos, meio + 1, fim));
        return no;
    }

    /** Altura da árvore (útil para conferir o balanceamento). */
    public int altura() {
        return altura(raiz);
    }

    private int altura(NoABB no) {
        if (no == null) return -1;
        return 1 + Math.max(altura(no.getEsq()), altura(no.getDir()));
    }
}
