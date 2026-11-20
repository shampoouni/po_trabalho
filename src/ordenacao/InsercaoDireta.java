package ordenacao;

import java.util.ArrayList;

import reserva.Reserva;

public class InsercaoDireta {

    /**
     * Método de ordenação por Inserção Direta.
     * Este método organiza a lista de Reservas em ordem crescente.
     * 
     * @param lista A lista de objetos Reserva a ser ordenada.
     */
    public void insercaoDireta(ArrayList<Reserva> lista) {
        int i, j;
        Reserva temp;

        // Inicia o processo de inserção a partir do segundo elemento
        for (i = 1; i < lista.size(); i++) {
            temp = lista.get(i); // Guarda o elemento a ser inserido
            j = i - 1; // O índice j aponta para o elemento anterior

            // Move os elementos maiores que 'temp' uma posição à direita
            while (j >= 0 && lista.get(j).compareTo(temp) > 0) {
                lista.set(j + 1, lista.get(j)); // Move o elemento para a direita
                j--; // Decrementa j para continuar comparando com os elementos anteriores
            }

            // Insere o elemento 'temp' na posição correta
            lista.set(j + 1, temp);
        }
    }
}
