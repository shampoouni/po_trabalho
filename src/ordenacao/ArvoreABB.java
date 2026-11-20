package ordenacao;

import java.util.ArrayList;
import java.util.List;

import reserva.Reserva;

/**
 * A classe ArvoreABB representa uma Árvore Binária de Busca (ABB)
 * para armazenar objetos do tipo Reserva.
 * 
 * A comparação entre os nós é feita usando o "codigo" da reserva.
 * 
 * A árvore também realiza balanceamento automático a cada 5000 inserções.
 */
public class ArvoreABB {

    private NoABB raiz;              // Nó raiz da árvore
    private int contadorInsercoes;   // Contador de inserções para acionar balanceamento

    /**
     * Construtor da árvore.
     * Inicialmente a árvore está vazia e o contador é zerado.
     */
    public ArvoreABB() {
        this.raiz = null;
        this.contadorInsercoes = 0;
    }

    /**
     * Insere uma Reserva na árvore.
     * 
     * @param reserva - objeto Reserva a ser inserido.
     * @return true se a inserção for realizada corretamente.
     */
    public boolean inserir(Reserva reserva) {
        // Caso a árvore esteja vazia, cria a raiz
        if (this.raiz == null) {
            this.raiz = new NoABB(reserva);
            contadorInsercoes++;
            return true;
        }

        // Inserção recursiva
        boolean inserido = inserir(reserva, this.raiz);

        // Se inserido, incrementa contador
        if (inserido) contadorInsercoes++;

        // Após 5000 inserções, balanceia a árvore
        if (contadorInsercoes >= 5000) {
            balancear();
            contadorInsercoes = 0;
        }

        return inserido;
    }

    /**
     * Método recursivo de inserção.
     * 
     * O código da Reserva é usado como chave de comparação.
     */
    private boolean inserir(Reserva reserva, NoABB no) {

        // Compara o NOME da nova reserva com o nome do nó atual (chave secundária)
        int comparacao = reserva.getNome().compareTo(no.getItem().getNome());

        if (comparacao == 0) {
            // Caso o código já exista, adiciona a reserva na lista deste nó
            no.adicionarReserva(reserva);
            return true;
        } 
        else if (comparacao < 0) {
            // Se menor, vai para a subárvore esquerda
            if (no.getEsq() == null) {
                no.setEsq(new NoABB(reserva));
                return true;
            }
            return inserir(reserva, no.getEsq());
        } 
        else {
            // Se maior, vai para a subárvore direita
            if (no.getDir() == null) {
                no.setDir(new NoABB(reserva));
                return true;
            }
            return inserir(reserva, no.getDir());
        }
    }

    /**
     * Pesquisa uma reserva pelo código.
     * 
     * @param codigo - código da reserva pesquisada.
     * @return lista de reservas associadas ao código, ou lista vazia se não houver.
     */
    public List<Reserva> pesquisar(String codigo) {
        NoABB no = pesquisarNo(codigo, this.raiz);
        return (no != null) ? no.getReservas() : new ArrayList<>();
    }

    /**
     * Método recursivo de busca na ABB.
     */
    private NoABB pesquisarNo(String codigo, NoABB no) {
        if (no == null) return null;

        int comparacao = codigo.compareTo(no.getItem().getNome());

        if (comparacao == 0) {
            return no; // Achou
        } 
        else if (comparacao < 0) {
            return pesquisarNo(codigo, no.getEsq()); // Vai à esquerda
        } 
        else {
            return pesquisarNo(codigo, no.getDir()); // Vai à direita
        }
    }

    /**
     * Retorna todas as Reservas da árvore em ordem crescente do código.
     * 
     * Usa caminhamento central (in-order).
     */
    public List<Reserva> camCentral() {
        List<Reserva> lista = new ArrayList<>();
        camCentral(this.raiz, lista);
        return lista;
    }

    /**
     * Método recursivo para caminhamento central (in-order).
     */
    private void camCentral(NoABB no, List<Reserva> lista) {
        if (no != null) {
            camCentral(no.getEsq(), lista);   // Visita esquerda
            lista.addAll(no.getReservas());     // Adiciona o item do nó
            camCentral(no.getDir(), lista);   // Visita direita
        }
    }

    /**
     * Balanceia a árvore reconstruindo-a a partir da lista ordenada.
     */
    public void balancear() {
        List<Reserva> lista = camCentral();
        this.raiz = balancear(lista, 0, lista.size() - 1);
    }

    /**
     * Cria recursivamente uma árvore balanceada a partir da lista ordenada.
     */
    private NoABB balancear(List<Reserva> lista, int inicio, int fim) {
        if (inicio > fim) return null;

        int meio = (inicio + fim) / 2;

        NoABB no = new NoABB(lista.get(meio));

        // Constrói subárvore esquerda e direita
        no.setEsq(balancear(lista, inicio, meio - 1));
        no.setDir(balancear(lista, meio + 1, fim));

        return no;
    }
}
