package metodos.ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Ordenação por Quicksort (estratégia de dividir e conquistar).
 *
 * Ideia do algoritmo:
 *   1) Escolhe um elemento como pivô (aqui, o elemento do meio do trecho).
 *   2) Particiona: move para a esquerda os elementos menores que o pivô e
 *      para a direita os maiores.
 *   3) Ordena recursivamente cada uma das duas partes.
 *
 * Complexidade: O(n log n) no caso médio. O pior caso é O(n²), quando o pivô
 * escolhido é sempre o menor ou o maior elemento. Escolher o pivô no meio do
 * trecho evita esse pior caso nos arquivos já ordenados ou invertidos.
 *
 * Critério de ordenação: compareTo() de Emprestimo, ou seja, pelo nome do
 * usuário e, em caso de nomes iguais, pelo código do empréstimo.
 *
 * Estabilidade: o Quicksort NÃO é estável. Como o desempate pelo código
 * (único) deixa a ordem totalmente definida, o resultado final é o mesmo de
 * um algoritmo estável.
 */
public class Quicksort {

    /**
     * Ordena a lista em ordem crescente usando Quicksort.
     *
     * @param emprestimos A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> emprestimos) {
        // Só ordena se a lista existir e tiver elementos
        if (emprestimos != null && !emprestimos.isEmpty()) {
            // Ordena a lista inteira: do índice 0 até o último
            quicksort(emprestimos, 0, emprestimos.size() - 1);
        }
    }

    /**
     * Ordena recursivamente o trecho emprestimos[esq..dir].
     *
     * @param emprestimos A lista de objetos Emprestimo.
     * @param esq    O índice inicial (esquerda) do trecho a ser ordenado.
     * @param dir    O índice final (direita) do trecho a ser ordenado.
     */
    private static void quicksort(ArrayList<Emprestimo> emprestimos, int esq, int dir) {
        int i = esq, j = dir; // i percorre da esquerda para a direita, j da direita para a esquerda
        // Seleciona o pivô (elemento do meio do trecho)
        Emprestimo pivo = emprestimos.get((esq + dir) / 2);

        // Particionamento: divide o trecho em duas partes (menores e maiores que o pivô)
        while (i <= j) {
            // Avança i enquanto o elemento estiver no lado certo (menor que o pivô)
            while (emprestimos.get(i).compareTo(pivo) < 0)
                i++;
            // Recua j enquanto o elemento estiver no lado certo (maior que o pivô)
            while (emprestimos.get(j).compareTo(pivo) > 0)
                j--;

            // i parou num elemento que deveria ir para a direita e j num que deveria
            // ir para a esquerda: se os índices ainda não se cruzaram, troca os dois
            if (i <= j) {
                // Troca os elementos nas posições i e j
                Emprestimo temp = emprestimos.get(i);
                emprestimos.set(i, emprestimos.get(j));
                emprestimos.set(j, temp);
                i++; // Move o índice i para a direita
                j--; // Move o índice j para a esquerda
            }
        }

        // Ao final, emprestimos[esq..j] tem os menores e emprestimos[i..dir] os maiores
        // Ordena recursivamente a parte da esquerda (se tiver mais de um elemento)
        if (esq < j)
            quicksort(emprestimos, esq, j);
        // Ordena recursivamente a parte da direita (se tiver mais de um elemento)
        if (i < dir)
            quicksort(emprestimos, i, dir);
    }
}
