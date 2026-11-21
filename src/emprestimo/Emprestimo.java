package emprestimo;

/**
 * Representa um empréstimo da biblioteca.
 *
 * Chave primária: codigo (identifica unicamente o empréstimo).
 * Chave secundária: nome do usuário (pode se repetir em vários empréstimos).
 */
public class Emprestimo implements Comparable<Emprestimo> {
    private int codigo;            // chave primária, ex: 10025
    private String nome;           // chave secundária, ex: Marcos Silva
    private String titulo;         // título do livro
    private String autor;          // autor do livro
    private String dataEmprestimo; // dd/mm/aaaa
    private String dataDevolucao;  // dd/mm/aaaa

    public Emprestimo(int codigo, String nome, String titulo, String autor,
                      String dataEmprestimo, String dataDevolucao) {
        this.codigo = codigo;
        this.nome = nome;
        this.titulo = titulo;
        this.autor = autor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    /** Mesmo formato do arquivo de entrada: codigo;nome;titulo;autor;dataEmprestimo;dataDevolucao */
    @Override
    public String toString() {
        return codigo + ";" + nome + ";" + titulo + ";" + autor + ";" + dataEmprestimo + ";" + dataDevolucao;
    }

    /**
     * Critério de ordenação: primeiro pelo nome do usuário (chave secundária);
     * em caso de nomes iguais, desempata pelo código do empréstimo (chave primária).
     * O código é comparado como número, para que 2 venha antes de 10.
     * Como o código é único, nunca existem dois registros "iguais", então a ordem
     * final é sempre a mesma, independentemente da estabilidade do algoritmo.
     */
    @Override
    public int compareTo(Emprestimo outro) {
        int nome = this.nome.compareTo(outro.nome);
        if (nome != 0) {
            return nome;
        }
        return Integer.compare(this.codigo, outro.codigo);
    }
}
