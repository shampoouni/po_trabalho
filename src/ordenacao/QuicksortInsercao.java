package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Ordenação por Quicksort combinado com Inserção Direta.
 *
 * Funciona igual ao Quicksort (ver Quicksort.java), com uma diferença: quando
 * o trecho a ordenar tem 20 elementos ou menos, em vez de continuar dividindo
 * recursivamente, ordena aquele trecho com Inserção Direta (item 3.2 do
 * enunciado).
 *
 * Por que isso ajuda: em trechos pequenos, o custo das chamadas recursivas do
 * Quicksort pesa mais que a própria ordenação, e a Inserção Direta, apesar de
 * ser O(n²), é mais rápida para poucos elementos.
 *
 * Critério de ordenação: compareTo() de Emprestimo (nome do usuário e, em
 * caso de nomes iguais, código do empréstimo).
 */
public class QuicksortInsercao {

    // Partições com até LIMITE elementos são ordenadas por Inserção Direta
    private static final int LIMITE = 20;

    /**
     * Ordena a lista em ordem crescente usando Quicksort com Inserção Direta.
     *
     * @param lista A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> lista) {
        // Só ordena se a lista existir e tiver elementos
        if (lista != null && !lista.isEmpty()) {
            quicksort(lista, 0, lista.size() - 1);
        }
    }

    /**
     * Ordena recursivamente o trecho lista[esq..dir].
     *
     * @param lista A lista de objetos Emprestimo.
     * @param esq   Índice inicial do trecho a ser ordenado.
     * @param dir   Índice final do trecho a ser ordenado.
     */
    private static void quicksort(ArrayList<Emprestimo> lista, int esq, int dir) {
        // Partição pequena (20 elementos ou menos): ordena com Inserção Direta e para
        if (dir - esq + 1 <= LIMITE) {
            InsercaoDireta.sort(lista, esq, dir);
            return;
        }

        int i = esq, j = dir; // i percorre da esquerda para a direita, j da direita para a esquerda
        Emprestimo pivo = lista.get((esq + dir) / 2); // pivô = elemento do meio

        // Particiona: menores que o pivô à esquerda, maiores à direita
        while (i <= j) {
            // Avança i enquanto o elemento for menor que o pivô
            while (lista.get(i).compareTo(pivo) < 0)
                i++;
            // Recua j enquanto o elemento for maior que o pivô
            while (lista.get(j).compareTo(pivo) > 0)
                j--;

            // Se os índices não se cruzaram, troca os elementos que estão no lado errado
            if (i <= j) {
                Emprestimo temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
                i++;
                j--;
            }
        }

        // Ordena recursivamente a parte da esquerda (menores) e a da direita (maiores)
        if (esq < j)
            quicksort(lista, esq, j);
        if (i < dir)
            quicksort(lista, i, dir);
    }
}
