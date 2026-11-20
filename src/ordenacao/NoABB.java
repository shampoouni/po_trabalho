package ordenacao;

import java.util.ArrayList;
import java.util.List;

import reserva.Reserva;

public class NoABB {
    private Reserva item; // Representa a primeira reserva associada à chave (nome)
    private List<Reserva> Reservas; // Lista de reservas associadas à mesma chave
    private NoABB esq, dir; // Ponteiros para os filhos esquerdo e direito

    /**
     * Construtor da classe NoABB.
     * 
     * @param item A primeira Reserva associada ao nó.
     */
    public NoABB(Reserva item) {
        this.item = item; // A primeira reserva que pertence à chave do nó
        this.Reservas = new ArrayList<>(); // Inicializa a lista de Reservas
        this.Reservas.add(item); // Adiciona a primeira Reserva à lista
    }

    /**
     * Retorna o item (primeira Reserva) armazenado neste nó.
     * 
     * @return A Reserva do nó.
     */
    public Reserva getItem() {
        return item;
    }

    /**
    * Retorna a lista de reservas associadas à chave.
     * 
     * @return Lista de Reservas.
     */
    public List<Reserva> getReservas() {
        return Reservas;
    }

    /**
     * Retorna o filho esquerdo deste nó.
     * 
     * @return O nó filho esquerdo.
     */
    public NoABB getEsq() {
        return esq;
    }

    /**
     * Define o filho esquerdo deste nó.
     * 
     * @param esq O nó filho esquerdo a ser definido.
     */
    public void setEsq(NoABB esq) {
        this.esq = esq;
    }

    /**
     * Retorna o filho direito deste nó.
     * 
     * @return O nó filho direito.
     */
    public NoABB getDir() {
        return dir;
    }

    /**
     * Define o filho direito deste nó.
     * 
     * @param dir O nó filho direito a ser definido.
     */
    public void setDir(NoABB dir) {
        this.dir = dir;
    }

    /**
     * Adiciona uma nova Reserva à lista de Reservas. Se a Reserva já existir,
     * não será adicionada novamente (evitando duplicatas).
     * 
     * @param Reserva A Reserva a ser adicionada.
     */
    public void adicionarReserva(Reserva Reserva) {
        // Verifica se a Reserva já não existe na lista para evitar duplicatas
        if (!this.Reservas.contains(Reserva)) {
            this.Reservas.add(Reserva);
        }
    }
}
