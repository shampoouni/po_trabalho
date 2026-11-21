package ordenacao;

import java.util.ArrayList;
import java.util.List;

import emprestimo.Emprestimo;

/**
 * Nó da Árvore AVL.
 *
 * A chave do nó é o nome do usuário (chave secundária), lido do primeiro
 * empréstimo guardado (item). Como um mesmo usuário pode ter vários
 * empréstimos, o nó guarda uma lista com TODOS os empréstimos daquele nome,
 * para que nenhum registro seja descartado.
 *
 * Além dos filhos, o nó guarda a sua altura, que a AVL usa para calcular o
 * fator de balanceamento sem precisar percorrer a árvore.
 */
public class NoAVL {

    private Emprestimo item;           // Primeiro empréstimo do nó: o nome dele é a chave
    private List<Emprestimo> emprestimos; // Todos os empréstimos com o mesmo nome

    private NoAVL esq;              // Filho esquerdo (nomes menores)
    private NoAVL dir;              // Filho direito (nomes maiores)
    private int altura;             // Altura do nó na árvore AVL (folha = 0)

    /**
     * Construtor do nó AVL.
     * @param item Emprestimo a ser armazenado como primeiro elemento do nó.
     */
    public NoAVL(Emprestimo item) {
        this.item = item;
        this.emprestimos = new ArrayList<>();
        this.emprestimos.add(item);   // O primeiro empréstimo define o nó
        this.altura = 0;           // Nó novo é sempre folha: altura 0
    }

    // ================================
    // Getters e Setters
    // ================================

    /** @return O primeiro empréstimo do nó (usado para ler o nome, que é a chave). */
    public Emprestimo getItem() {
        return item;
    }

    /** @return A lista com todos os empréstimos desse nome. */
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    /** @return O filho esquerdo (subárvore com nomes menores). */
    public NoAVL getEsq() {
        return esq;
    }

    /** @param esq Novo filho esquerdo. */
    public void setEsq(NoAVL esq) {
        this.esq = esq;
    }

    /** @return O filho direito (subárvore com nomes maiores). */
    public NoAVL getDir() {
        return dir;
    }

    /** @param dir Novo filho direito. */
    public void setDir(NoAVL dir) {
        this.dir = dir;
    }

    /** @return A altura do nó. */
    public int getAltura() {
        return altura;
    }

    /** @param altura Nova altura do nó (atualizada pela AVL após inserções e rotações). */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Adiciona um novo empréstimo à lista de empréstimos do nó.
     * Este método é usado quando já existe um nó com a mesma chave.
     *
     * @param emprestimo O novo empréstimo a ser adicionado.
     */
    public void adicionar(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }
}
