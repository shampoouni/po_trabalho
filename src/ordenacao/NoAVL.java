package ordenacao;

import java.util.ArrayList;
import java.util.List;

import emprestimo.Emprestimo;

/**
 * Classe NoAVL
 * Representa um nó de uma Árvore AVL que armazena um Emprestimo
 * e todos os empréstimos associados à mesma chave (nome do usuário).
 */
public class NoAVL {

    private Emprestimo item;           // Primeiro item associado ao nó (chave principal)
    private List<Emprestimo> emprestimos; // Todos os empréstimos com o mesmo nome

    private NoAVL esq;              // Filho esquerdo
    private NoAVL dir;              // Filho direito
    private int altura;             // Altura do nó na árvore AVL

    /**
     * Construtor do nó AVL.
     * @param item Emprestimo a ser armazenado como primeiro elemento do nó.
     */
    public NoAVL(Emprestimo item) {
        this.item = item;
        this.emprestimos = new ArrayList<>();
        this.emprestimos.add(item);   // O primeiro empréstimo define o nó
        this.altura = 0;           // Nó inicial sempre altura 0
    }

    // ================================
    // Getters e Setters
    // ================================

    public Emprestimo getItem() {
        return item;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public NoAVL getEsq() {
        return esq;
    }

    public void setEsq(NoAVL esq) {
        this.esq = esq;
    }

    public NoAVL getDir() {
        return dir;
    }

    public void setDir(NoAVL dir) {
        this.dir = dir;
    }

    public int getAltura() {
        return altura;
    }

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