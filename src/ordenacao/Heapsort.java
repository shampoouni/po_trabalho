package ordenacao;

import java.util.ArrayList;

import emprestimo.Emprestimo;

/**
 * Ordenação por Heapsort.
 *
 * Ideia do algoritmo: a lista é vista como uma árvore binária guardada num
 * vetor, em que o nó da posição i tem os filhos nas posições 2i+1 (esquerdo)
 * e 2i+2 (direito). O algoritmo tem duas fases:
 *   1) Construção do heap: reorganiza a lista para que todo pai seja maior
 *      ou igual aos seus filhos (heap máximo). Assim, o maior elemento fica
 *      na posição 0.
 *   2) Extração: troca o maior elemento (posição 0) com o último da parte
 *      ainda não ordenada, diminui essa parte em 1 e refaz o heap. Repetindo
 *      isso, os maiores vão sendo colocados no final, do maior para o menor.
 *
 * Complexidade: O(n log n) em todos os casos (melhor, médio e pior).
 *
 * Critério de ordenação: compareTo() de Emprestimo, ou seja, pelo nome do
 * usuário e, em caso de nomes iguais, pelo código do empréstimo.
 *
 * Estabilidade: o Heapsort NÃO é estável (registros iguais podem trocar de
 * ordem). Isso não afeta o resultado aqui porque o desempate pelo código, que
 * é único, garante que nunca existam dois registros "iguais" na comparação.
 */
public class Heapsort {

    /**
     * Ordena a lista em ordem crescente usando Heapsort.
     *
     * @param lista A lista de objetos Emprestimo a ser ordenada.
     */
    public static void sort(ArrayList<Emprestimo> lista) {
        int dir = lista.size() - 1; // Índice do último elemento
        int esq = (dir - 1) / 2; // Índice do último nó que tem filhos (pai do último elemento)
        Emprestimo temp; // Variável temporária para troca de elementos

        // FASE 1 - Constrói o heap: ajusta cada nó que tem filhos, do último até a raiz
        while (esq >= 0) {
            // Garante que a subárvore que começa na posição 'esq' seja um heap válido
            refazHeap(lista, esq, dir);
            esq--; // Vai para o pai anterior (subindo na árvore)
        }

        // FASE 2 - Extração: a cada passo, o maior elemento vai para o final da lista
        while (dir > 0) {
            // Troca a raiz (maior valor do heap) com o último elemento da parte não ordenada
            temp = lista.get(0);
            lista.set(0, lista.get(dir));
            lista.set(dir, temp);
            dir--; // O maior já está na posição final: diminui o tamanho do heap

            // A nova raiz pode estar fora do lugar: refaz o heap com o restante da lista
            refazHeap(lista, 0, dir);
        }
    }

    /**
     * Refaz o heap a partir de uma posição: "desce" o elemento da posição 'esq'
     * pela árvore, trocando-o com o maior filho, até que ele seja maior ou igual
     * aos dois filhos (ou chegue a uma folha).
     *
     * @param lista A lista de objetos Emprestimo.
     * @param esq   O índice do nó que deve ser ajustado.
     * @param dir   O índice do último elemento que ainda faz parte do heap.
     */
    private static void refazHeap(ArrayList<Emprestimo> lista, int esq, int dir) {
        int i = esq; // Posição atual do elemento que está descendo
        int mF = 2 * i + 1; // Índice do maior filho (começa pelo filho esquerdo)
        Emprestimo raiz = lista.get(i); // Guarda o elemento que vai descer na árvore
        boolean heap = false; // Fica true quando o elemento já está na posição correta

        // Continua enquanto o nó tiver filhos e o heap ainda não estiver correto
        while ((mF <= dir) && (!heap)) {
            // Se existe filho direito e ele é maior que o esquerdo, o maior filho é o direito
            if (mF < dir && lista.get(mF).compareTo(lista.get(mF + 1)) < 0) {
                mF++; // Escolhe o maior filho
            }

            // Se o maior filho for maior que o elemento guardado, o filho sobe uma posição
            if (raiz.compareTo(lista.get(mF)) < 0) {
                lista.set(i, lista.get(mF)); // Sobe o maior filho para a posição do pai
                i = mF; // O elemento guardado desce para a posição do filho
                mF = 2 * i + 1; // Calcula o filho esquerdo da nova posição
            } else {
                heap = true; // O elemento é maior que os filhos: heap está correto
            }
        }

        // Coloca o elemento guardado na posição final que ele encontrou
        lista.set(i, raiz);
    }
}
