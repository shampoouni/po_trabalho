package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Quicksort que, quando a partição tem 20 elementos ou menos, ordena aquela
 * parte da lista com Inserção Direta.
 */
public class QuicksortInsercao {

    // Partições com até LIMITE elementos são ordenadas por Inserção Direta
    private static final int LIMITE = 20;

    public static void sort(ArrayList<Emprestimo> lista) {
        if (lista != null && !lista.isEmpty()) {
            quicksort(lista, 0, lista.size() - 1);
        }
    }

    private static void quicksort(ArrayList<Emprestimo> lista, int esq, int dir) {
        // Partição pequena (20 elementos ou menos): usa Inserção Direta
        if (dir - esq + 1 <= LIMITE) {
            InsercaoDireta.sort(lista, esq, dir);
            return;
        }

        int i = esq, j = dir;
        Emprestimo pivo = lista.get((esq + dir) / 2); // pivô = elemento do meio

        // Particiona: menores que o pivô à esquerda, maiores à direita
        while (i <= j) {
            while (lista.get(i).compareTo(pivo) < 0)
                i++;
            while (lista.get(j).compareTo(pivo) > 0)
                j--;

            if (i <= j) {
                Emprestimo temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
                i++;
                j--;
            }
        }

        if (esq < j)
            quicksort(lista, esq, j);
        if (i < dir)
            quicksort(lista, i, dir);
    }
}
