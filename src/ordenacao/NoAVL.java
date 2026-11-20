package ordenacao;

import java.util.ArrayList;
import java.util.List;

import reserva.Reserva;

/**
 * Classe NoAVL
 * Representa um nó de uma Árvore AVL que armazena uma Reserva
 * e todas as reservas associadas à mesma chave (ex: mesmo CPF, nome, etc).
 */
public class NoAVL {

    private Reserva item;           // Primeiro item associado ao nó (chave principal)
    private List<Reserva> reservas; // Lista de reservas com a mesma chave

    private NoAVL esq;              // Filho esquerdo
    private NoAVL dir;              // Filho direito
    private int altura;             // Altura do nó na árvore AVL

    /**
     * Construtor do nó AVL.
     * @param item Reserva a ser armazenada como primeiro elemento do nó.
     */
    public NoAVL(Reserva item) {
        this.item = item;
        this.reservas = new ArrayList<>();
        this.reservas.add(item);   // A primeira reserva define o nó
        this.altura = 0;           // Nó inicial sempre altura 0
    }

    // ================================
    // Getters e Setters
    // ================================

    public Reserva getItem() {
        return item;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public NoAVL getEsq() {
        return esq;
    }

    public void setEsq(NoAVL esq) {
        this.esq = esq;
    }

    public NoAVL getDir() {
        return dir;
    }

    public void setDir(NoAVL dir) {
        this.dir = dir;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * Adiciona uma nova reserva à lista de reservas associadas ao nó.
     * Este método é usado quando já existe um nó com a mesma chave.
     *
     * @param reserva A nova reserva a ser adicionada.
     */
    public void adicionarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
}