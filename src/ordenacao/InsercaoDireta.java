package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

public class InsercaoDireta {

    /**
     * Ordena a lista inteira por Inserção Direta.
     */
    public static void sort(ArrayList<Emprestimo> lista) {
        sort(lista, 0, lista.size() - 1);
    }

    /**
     * Ordena apenas o trecho lista[esq..dir] por Inserção Direta (usado pelo
     * Quicksort com Inserção nas partições pequenas, sem copiar a lista).
     * É estável: só desloca elementos estritamente maiores.
     */
    public static void sort(ArrayList<Emprestimo> lista, int esq, int dir) {
        for (int i = esq + 1; i <= dir; i++) {
            Emprestimo temp = lista.get(i); // elemento a ser inserido
            int j = i - 1;

            // Desloca para a direita os elementos maiores que 'temp'
            while (j >= esq && lista.get(j).compareTo(temp) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }

            // Insere 'temp' na posição correta
            lista.set(j + 1, temp);
        }
    }
}
