package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Nó da ABB. A chave do nó é o nome do usuário (chave secundária) e o nó guarda
 * TODOS os empréstimos com esse nome, para não descartar nomes repetidos.
 */
public class NoABB {
    private String nome;                       // chave do nó
    private ArrayList<Emprestimo> emprestimos; // todos os empréstimos desse nome
    private NoABB esq, dir;

    public NoABB(Emprestimo primeiro) {
        this.nome = primeiro.getNome();
        this.emprestimos = new ArrayList<>();
        this.emprestimos.add(primeiro);
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionar(Emprestimo e) {
        emprestimos.add(e);
    }

    public NoABB getEsq() {
        return esq;
    }

    public void setEsq(NoABB esq) {
        this.esq = esq;
    }

    public NoABB getDir() {
        return dir;
    }

    public void setDir(NoABB dir) {
        this.dir = dir;
    }
}
