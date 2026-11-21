package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * A classe Heapsort implementa o algoritmo de ordenação HeapSort para ordenar
 * uma lista de objetos Emprestimo. O HeapSort é um algoritmo baseado na estrutura de
 * dados heap.
 */
public class Heapsort {

    /**
     * Método principal de ordenação utilizando o algoritmo HeapSort.
     * 
     * @param lista A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> lista) {
        int dir = lista.size() - 1; // Índice do último elemento
        int esq = (dir - 1) / 2; // Índice do penúltimo nível (pai do último elemento)
        Emprestimo temp; // Variável temporária para troca de elementos

        // Constrói o heap (rearranja a lista para garantir a propriedade de heap)
        while (esq >= 0) {
            // Chama o método refazHeap para garantir que a subárvore da posição 'esq' seja
            // válida
            refazHeap(lista, esq, dir);
            esq--; // Move para o próximo pai (subindo na árvore)
        }

        // Extração dos elementos do heap (ordenando a lista)
        while (dir > 0) {
            // Troca a raiz (máximo valor) com o último elemento
            temp = lista.get(0);
            lista.set(0, lista.get(dir));
            lista.set(dir, temp);
            dir--; // Reduz o tamanho do heap (eliminando o maior valor)

            // Refaz o heap com o restante da lista
            refazHeap(lista, 0, dir);
        }
    }

    /**
     * Refaz o heap a partir de uma posição específica (manipula a lista em uma
     * subárvore).
     * 
     * @param lista A lista de objetos Emprestimo.
     * @param esq   O índice do nó que deve ser ajustado.
     * @param dir   O índice do último elemento da subárvore.
     */
    private static void refazHeap(ArrayList<Emprestimo> lista, int esq, int dir) {
        int i = esq; // Posição inicial (nó que será ajustado)
        int mF = 2 * i + 1; // Índice do filho à esquerda
        Emprestimo raiz = lista.get(i); // Armazena o valor da raiz da subárvore
        boolean heap = false; // Variável de controle para saber se o heap está correto

        // Enquanto o filho maior não for maior que a raiz e a subárvore não estiver bem
        // estruturada
        while ((mF <= dir) && (!heap)) {
            // Se o filho à direita existe e é maior que o filho à esquerda, move para a
            // direita
            if (mF < dir && lista.get(mF).compareTo(lista.get(mF + 1)) < 0) {
                mF++; // Escolhe o maior filho
            }

            // Se o filho escolhido for maior que a raiz, troca os valores
            if (raiz.compareTo(lista.get(mF)) < 0) {
                lista.set(i, lista.get(mF)); // Troca a raiz com o maior filho
                i = mF; // Atualiza o índice para o filho
                mF = 2 * i + 1; // Move para o próximo nível da árvore
            } else {
                heap = true; // O heap está correto, não há mais necessidade de ajustes
            }
        }

        // Coloca o valor da raiz na posição correta
        lista.set(i, raiz);
    }
}
