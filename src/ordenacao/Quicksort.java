package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * A classe Quicksort implementa o algoritmo de ordenação QuickSort para ordenar
 * uma lista de objetos Emprestimo. O QuickSort é um algoritmo eficiente de ordenação
 * baseado
 * na estratégia de dividir e conquistar.
 */
public class Quicksort {

    /**
     * Método principal que inicia o processo de ordenação QuickSort.
     * 
     * @param emprestimos A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> emprestimos) {
        // Verifica se a lista não está vazia
        if (emprestimos != null && !emprestimos.isEmpty()) {
            // Chama o método recursivo quicksort para ordenar a lista
            quicksort(emprestimos, 0, emprestimos.size() - 1);
        }
    }

    /**
     * Método recursivo que executa o algoritmo QuickSort para a lista de emprestimos.
     * 
     * @param emprestimos A lista de objetos Emprestimo.
     * @param esq    O índice inicial (esquerda) da parte da lista a ser ordenada.
     * @param dir    O índice final (direita) da parte da lista a ser ordenada.
     */
    private static void quicksort(ArrayList<Emprestimo> emprestimos, int esq, int dir) {
        int i = esq, j = dir;
        // Seleciona o pivô (elemento do meio da lista)
        Emprestimo pivo = emprestimos.get((esq + dir) / 2);

        // Loop para dividir a lista em duas partes (menores e maiores que o pivô)
        while (i <= j) {
            // Move o índice i até encontrar um valor maior ou igual ao pivô
            while (emprestimos.get(i).compareTo(pivo) < 0)
                i++;
            // Move o índice j até encontrar um valor menor ou igual ao pivô
            while (emprestimos.get(j).compareTo(pivo) > 0)
                j--;

            // Se os índices ainda estiverem válidos, troca os elementos
            if (i <= j) {
                // Troca os elementos nas posições i e j
                Emprestimo temp = emprestimos.get(i);
                emprestimos.set(i, emprestimos.get(j));
                emprestimos.set(j, temp);
                i++; // Move o índice i para a direita
                j--; // Move o índice j para a esquerda
            }
        }

        // Chama recursivamente o método quicksort para a sublista à esquerda
        if (esq < j)
            quicksort(emprestimos, esq, j);
        // Chama recursivamente o método quicksort para a sublista à direita
        if (i < dir)
            quicksort(emprestimos, i, dir);
    }
}
