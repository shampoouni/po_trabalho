package ordenacao;

import java.util.ArrayList;

import reserva.Reserva;

public class QuicksortInsercao {

    // Limite de tamanho para quando usar inserção direta
    private static final int LIMITE = 20;

    /**
     * Método principal de ordenação que inicia o processo de QuickSort
     * com a inserção direta em listas pequenas.
     * 
     * @param lista A lista de objetos Reserva a ser ordenada.
     */
    public static void sort(ArrayList<Reserva> lista) {
        // Verifica se a lista não está vazia
        if (lista != null && !lista.isEmpty()) {
            // Chama o método recursivo quicksort para ordenar a lista
            quicksort(lista, 0, lista.size() - 1);
        }
    }

    /**
     * Método recursivo que executa o algoritmo QuickSort.
     * Se a sublista for pequena o suficiente, utiliza Inserção Direta.
     * 
     * @param lista A lista de objetos Reserva.
     * @param esq   O índice inicial da parte da lista a ser ordenada.
     * @param dir   O índice final da parte da lista a ser ordenada.
     */
    private static void quicksort(ArrayList<Reserva> lista, int esq, int dir) {
        // Se o tamanho da sublista for pequeno, usa inserção direta
        if (dir - esq + 1 <= LIMITE) {
            // Cria uma sublista da parte da lista que será ordenada
            InsercaoDireta insercao = new InsercaoDireta();
            ArrayList<Reserva> sublista = new ArrayList<>(lista.subList(esq, dir + 1));
            insercao.insercaoDireta(sublista);
            // Copia os elementos ordenados de volta para a lista original
            for (int i = 0; i < sublista.size(); i++) {
                lista.set(esq + i, sublista.get(i));
            }
        } else {
            int i = esq, j = dir;
            // Escolhe o pivô da lista
            Reserva pivo = lista.get((esq + dir) / 2);

            // Particiona a lista em dois subarrays, com elementos menores e maiores que o
            // pivô
            while (i <= j) {
                while (lista.get(i).compareTo(pivo) < 0)
                    i++; // Encontra o primeiro valor maior ou igual ao pivô
                while (lista.get(j).compareTo(pivo) > 0)
                    j--; // Encontra o primeiro valor menor ou igual ao pivô

                if (i <= j) {
                    // Troca os elementos nos índices i e j
                    Reserva temp = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, temp);
                    i++;
                    j--;
                }
            }

            // Recursivamente ordena as sublistas à esquerda e à direita do pivô
            if (esq < j)
                quicksort(lista, esq, j);
            if (i < dir)
                quicksort(lista, i, dir);
        }
    }
}
