package ordenacao;

import java.util.ArrayList;
import java.util.List;

import emprestimo.Emprestimo;

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
     */
    public void inserir(Emprestimo emprestimo) {
        this.raiz = inserir(emprestimo, this.raiz);
    }

    /**
     * Inserção recursiva na árvore AVL.
     */
    private NoAVL inserir(Emprestimo emprestimo, NoAVL no) {

        // Caso base: nó vazio → cria novo nó com o empréstimo
        if (no == null) {
            return new NoAVL(emprestimo);
        }

        // *** CHAVE DA ÁRVORE = NOME (chave secundária para pesquisa) ***
        int comparacao = emprestimo.getNome().compareTo(no.getItem().getNome());

        if (comparacao < 0) { // Inserção à esquerda
            no.setEsq(inserir(emprestimo, no.getEsq()));
        }
        else if (comparacao > 0) { // Inserção à direita
            no.setDir(inserir(emprestimo, no.getDir()));
        }
        else {
            // Nome IGUAL → adiciona o empréstimo no mesmo nó
            no.adicionar(emprestimo);
            return no; // Não precisa balancear
        }

        // Após inserir, balanceia o nó
        return balancear(no);
    }

    /**
     * Balanceia um nó após inserção ou remoção.
     */
    private NoAVL balancear(NoAVL no) {

        atualizarAltura(no);

        int fb = fatorBalanceamento(no);

        // Caso LL → Rotação à direita
        if (fb > 1 && fatorBalanceamento(no.getEsq()) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso LR → Rotação dupla esquerda-direita
        if (fb > 1 && fatorBalanceamento(no.getEsq()) < 0) {
            no.setEsq(rotacaoEsquerda(no.getEsq()));
            return rotacaoDireita(no);
        }

        // Caso RR → Rotação à esquerda
        if (fb < -1 && fatorBalanceamento(no.getDir()) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso RL → Rotação dupla direita-esquerda
        if (fb < -1 && fatorBalanceamento(no.getDir()) > 0) {
            no.setDir(rotacaoDireita(no.getDir()));
            return rotacaoEsquerda(no);
        }

        return no; // Já balanceado
    }

    /**
     * Atualiza a altura do nó.
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
     * Calcula o fator de balanceamento do nó.
     */
    private int fatorBalanceamento(NoAVL no) {
        return (no == null) ? 0 : altura(no.getEsq()) - altura(no.getDir());
    }

    /**
     * Rotação simples à direita.
     */
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.getEsq();
        NoAVL T2 = x.getDir();

        // Rotação
        x.setDir(y);
        y.setEsq(T2);

        // Atualiza alturas
        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    /**
     * Rotação simples à esquerda.
     */
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.getDir();
        NoAVL T2 = y.getEsq();

        // Rotação
        y.setEsq(x);
        x.setDir(T2);

        // Atualiza alturas
        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    /**
     * Pesquisa todos os empréstimos de um nome de usuário.
     * @param nome chave buscada (nome do usuário).
     */
    public List<Emprestimo> pesquisar(String nome) {
        NoAVL no = pesquisarNo(nome, this.raiz);
        return (no != null) ? no.getEmprestimos() : new ArrayList<>();
    }

    /**
     * Pesquisa recursivamente um nó pelo nome.
     */
    private NoAVL pesquisarNo(String nome, NoAVL no) {
        if (no == null) {
            return null;
        }

        int comparacao = nome.compareTo(no.getItem().getNome());

        if (comparacao == 0) {
            return no; // encontrado
        }
        else if (comparacao < 0) {
            return pesquisarNo(nome, no.getEsq());
        }
        else {
            return pesquisarNo(nome, no.getDir());
        }
    }
}
