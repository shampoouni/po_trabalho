package metodos.pesquisa;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Nó da ABB (Árvore Binária de Busca).
 *
 * A chave do nó é o nome do usuário (chave secundária). Como um mesmo usuário
 * pode ter vários empréstimos, o nó guarda uma lista com TODOS os empréstimos
 * daquele nome. Assim, nenhum registro é descartado quando o nome se repete
 * (exigência do item 4.1 do enunciado), e a pesquisa por um nome devolve
 * todos os empréstimos dele de uma vez.
 */
public class NoABB {
    private String nome;                       // chave do nó (nome do usuário)
    private ArrayList<Emprestimo> emprestimos; // todos os empréstimos desse nome
    private NoABB esq, dir;                    // filhos: esq tem nomes menores, dir tem nomes maiores

    /**
     * Cria o nó a partir do primeiro empréstimo de um nome. O nome desse
     * empréstimo passa a ser a chave do nó.
     *
     * @param primeiro Primeiro empréstimo encontrado com esse nome.
     */
    public NoABB(Emprestimo primeiro) {
        this.nome = primeiro.getNome();
        this.emprestimos = new ArrayList<>();
        this.emprestimos.add(primeiro);
    }

    /** @return O nome do usuário, que é a chave do nó. */
    public String getNome() {
        return nome;
    }

    /** @return A lista com todos os empréstimos desse nome. */
    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    /**
     * Adiciona mais um empréstimo a este nó. É usado quando o nome do
     * empréstimo já existe na árvore.
     *
     * @param e Empréstimo com o mesmo nome do nó.
     */
    public void adicionar(Emprestimo e) {
        emprestimos.add(e);
    }

    /** @return O filho esquerdo (subárvore com nomes menores). */
    public NoABB getEsq() {
        return esq;
    }

    /** @param esq Novo filho esquerdo. */
    public void setEsq(NoABB esq) {
        this.esq = esq;
    }

    /** @return O filho direito (subárvore com nomes maiores). */
    public NoABB getDir() {
        return dir;
    }

    /** @param dir Novo filho direito. */
    public void setDir(NoABB dir) {
        this.dir = dir;
    }
}
