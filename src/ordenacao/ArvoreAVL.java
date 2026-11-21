package ordenacao;

import java.util.ArrayList;
import java.util.List;

import emprestimo.Emprestimo;

/**
 * Árvore AVL cuja chave é o nome do usuário (chave secundária).
 *
 * A AVL é uma ABB que se mantém balanceada sozinha: em todo nó, a diferença
 * de altura entre a subárvore esquerda e a direita (fator de balanceamento)
 * fica sempre entre -1 e 1. Sempre que uma inserção quebra essa regra, a
 * árvore faz rotações para se reequilibrar. Por isso a altura fica sempre
 * próxima de log2(n), e inserção e pesquisa são O(log n) em qualquer caso,
 * inclusive com arquivos ordenados ou invertidos.
 *
 * Diferente da ABB, aqui não é preciso balancear depois de carregar: o
 * balanceamento acontece a cada inserção.
 *
 * Nomes repetidos não são descartados: cada nó guarda a lista de todos os
 * empréstimos daquele nome (ver NoAVL).
 */
public class ArvoreAVL {

    private NoAVL raiz; // Raiz da árvore AVL

    /**
     * Construtor da árvore AVL. Inicializa a árvore vazia.
     */
    public ArvoreAVL() {
        this.raiz = null;
    }

    /**
     * Insere um empréstimo na árvore.
     * A árvore é ordenada pelo NOME do usuário (chave secundária).
     * Se o nome já existir, adiciona o empréstimo no mesmo nó (nada é descartado).
     *
     * @param emprestimo Empréstimo a ser inserido.
     */
    public void inserir(Emprestimo emprestimo) {
        // A raiz pode mudar por causa das rotações, então recebe o retorno da inserção
        this.raiz = inserir(emprestimo, this.raiz);
    }

    /**
     * Inserção recursiva na árvore AVL. Desce até a posição correta, insere e,
     * na volta da recursão, balanceia cada nó do caminho.
     *
     * @param emprestimo Empréstimo a ser inserido.
     * @param no         Raiz da subárvore atual.
     * @return A nova raiz da subárvore (pode mudar depois de uma rotação).
     */
    private NoAVL inserir(Emprestimo emprestimo, NoAVL no) {

        // Caso base: nó vazio → cria novo nó com o empréstimo
        if (no == null) {
            return new NoAVL(emprestimo);
        }

        // *** CHAVE DA ÁRVORE = NOME (chave secundária para pesquisa) ***
        int comparacao = emprestimo.getNome().compareTo(no.getItem().getNome());

        if (comparacao < 0) { // Nome menor: inserção à esquerda
            no.setEsq(inserir(emprestimo, no.getEsq()));
        }
        else if (comparacao > 0) { // Nome maior: inserção à direita
            no.setDir(inserir(emprestimo, no.getDir()));
        }
        else {
            // Nome IGUAL → adiciona o empréstimo no mesmo nó
            no.adicionar(emprestimo);
            return no; // Não criou nó novo, então a altura não mudou: não precisa balancear
        }

        // Após inserir, atualiza a altura e balanceia o nó, se necessário
        return balancear(no);
    }

    /**
     * Balanceia um nó após inserção: atualiza a altura, calcula o fator de
     * balanceamento e, se ele for maior que 1 ou menor que -1, aplica a
     * rotação adequada.
     *
     * Fator de balanceamento (fb) = altura da esquerda - altura da direita.
     *   fb > 1  → o lado esquerdo está mais alto
     *   fb < -1 → o lado direito está mais alto
     *
     * @param no Nó a ser balanceado.
     * @return A nova raiz da subárvore depois do balanceamento.
     */
    private NoAVL balancear(NoAVL no) {

        atualizarAltura(no);

        int fb = fatorBalanceamento(no);

        // Caso LL (esquerda-esquerda): pesado à esquerda, e o filho esquerdo também
        // pende para a esquerda → Rotação simples à direita
        if (fb > 1 && fatorBalanceamento(no.getEsq()) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso LR (esquerda-direita): pesado à esquerda, mas o filho esquerdo pende
        // para a direita → Rotação dupla: esquerda no filho, depois direita no nó
        if (fb > 1 && fatorBalanceamento(no.getEsq()) < 0) {
            no.setEsq(rotacaoEsquerda(no.getEsq()));
            return rotacaoDireita(no);
        }

        // Caso RR (direita-direita): pesado à direita, e o filho direito também
        // pende para a direita → Rotação simples à esquerda
        if (fb < -1 && fatorBalanceamento(no.getDir()) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso RL (direita-esquerda): pesado à direita, mas o filho direito pende
        // para a esquerda → Rotação dupla: direita no filho, depois esquerda no nó
        if (fb < -1 && fatorBalanceamento(no.getDir()) > 0) {
            no.setDir(rotacaoDireita(no.getDir()));
            return rotacaoEsquerda(no);
        }

        return no; // Já balanceado (fb entre -1 e 1)
    }

    /**
     * Atualiza a altura do nó: 1 + a altura do filho mais alto.
     */
    private void atualizarAltura(NoAVL no) {
        no.setAltura(1 + Math.max(altura(no.getEsq()), altura(no.getDir())));
    }

    /**
     * Retorna a altura de um nó (ou -1 se vazio).
     */
    private int altura(NoAVL no) {
        return (no == null) ? -1 : no.getAltura();
    }

    /**
     * Calcula o fator de balanceamento do nó: altura da esquerda - altura da direita.
     */
    private int fatorBalanceamento(NoAVL no) {
        return (no == null) ? 0 : altura(no.getEsq()) - altura(no.getDir());
    }

    /**
     * Rotação simples à direita: o filho esquerdo (x) sobe e vira a raiz da
     * subárvore, e o nó atual (y) desce para a direita de x.
     *
     *        y                x
     *       / \              / \
     *      x   C    →       A   y
     *     / \                  / \
     *    A   T2              T2   C
     *
     * @param y Nó desbalanceado.
     * @return A nova raiz da subárvore (x).
     */
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.getEsq();
        NoAVL T2 = x.getDir(); // subárvore que muda de pai

        // Rotação
        x.setDir(y);
        y.setEsq(T2);

        // Atualiza alturas (primeiro y, que agora está embaixo, depois x)
        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    /**
     * Rotação simples à esquerda: o filho direito (y) sobe e vira a raiz da
     * subárvore, e o nó atual (x) desce para a esquerda de y.
     *
     *      x                    y
     *     / \                  / \
     *    A   y      →         x   C
     *       / \              / \
     *      T2  C            A   T2
     *
     * @param x Nó desbalanceado.
     * @return A nova raiz da subárvore (y).
     */
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.getDir();
        NoAVL T2 = y.getEsq(); // subárvore que muda de pai

        // Rotação
        y.setEsq(x);
        x.setDir(T2);

        // Atualiza alturas (primeiro x, que agora está embaixo, depois y)
        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    /**
     * Pesquisa todos os empréstimos de um nome de usuário.
     * @param nome chave buscada (nome do usuário).
     * @return Lista com todos os empréstimos do nome (vazia se não existir).
     */
    public List<Emprestimo> pesquisar(String nome) {
        NoAVL no = pesquisarNo(nome, this.raiz);
        return (no != null) ? no.getEmprestimos() : new ArrayList<>();
    }

    /**
     * Pesquisa recursivamente um nó pelo nome: compara com o nó atual e desce
     * para a esquerda (nome menor) ou para a direita (nome maior).
     *
     * @return O nó com o nome, ou null se não existir.
     */
    private NoAVL pesquisarNo(String nome, NoAVL no) {
        if (no == null) {
            return null; // chegou numa posição vazia: o nome não existe
        }

        int comparacao = nome.compareTo(no.getItem().getNome());

        if (comparacao == 0) {
            return no; // encontrado
        }
        else if (comparacao < 0) {
            return pesquisarNo(nome, no.getEsq()); // nome menor: procura à esquerda
        }
        else {
            return pesquisarNo(nome, no.getDir()); // nome maior: procura à direita
        }
    }
}
