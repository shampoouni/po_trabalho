package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Ordenação por Inserção Direta.
 *
 * Ideia do algoritmo: funciona como organizar cartas na mão. A parte da
 * esquerda da lista já está ordenada; a cada passo, pega o próximo elemento,
 * desloca para a direita os elementos ordenados que são maiores que ele e
 * insere o elemento no espaço que sobrou.
 *
 * Complexidade: O(n²) no caso médio e no pior caso, e O(n) no melhor caso
 * (lista já ordenada). Por ser simples e ter pouco custo extra, é muito
 * rápida para listas pequenas. Por isso o Quicksort com Inserção a usa nas
 * partições de até 20 elementos.
 *
 * Estabilidade: é estável, porque só desloca elementos estritamente maiores
 * (compareTo > 0); elementos iguais mantêm a ordem original.
 */
public class InsercaoDireta {

    /**
     * Ordena a lista inteira por Inserção Direta.
     *
     * @param lista A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> lista) {
        sort(lista, 0, lista.size() - 1);
    }

    /**
     * Ordena apenas o trecho lista[esq..dir] por Inserção Direta. É usado pelo
     * Quicksort com Inserção nas partições pequenas, ordenando direto na lista
     * original, sem criar cópias.
     *
     * @param lista A lista de objetos Emprestimo.
     * @param esq   Índice inicial do trecho a ser ordenado.
     * @param dir   Índice final do trecho a ser ordenado.
     */
    public static void sort(ArrayList<Emprestimo> lista, int esq, int dir) {
        // O primeiro elemento do trecho sozinho já está ordenado: começa do segundo
        for (int i = esq + 1; i <= dir; i++) {
            Emprestimo temp = lista.get(i); // elemento a ser inserido na parte ordenada
            int j = i - 1; // último elemento da parte já ordenada

            // Desloca para a direita os elementos maiores que 'temp' (sem sair do trecho)
            while (j >= esq && lista.get(j).compareTo(temp) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }

            // Insere 'temp' no espaço aberto, que é a sua posição correta
            lista.set(j + 1, temp);
        }
    }
}
