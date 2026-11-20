package ordenacao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import reserva.Reserva;

// Representa uma entrada na tabela hash, que armazena uma chave (nome) e a lista de reservas associadas a ela
class HashEntry {
    String nome; // A chave (nome) associada às reservas
    List<Reserva> Reservas; // Lista de reservas associadas ao nome

    // Construtor da entrada
    HashEntry(String nome) {
        this.nome = nome;
        this.Reservas = new ArrayList<>();
    }
}

public class Hash {
    private LinkedList<HashEntry>[] table; // Tabela hash, onde cada posição é uma lista ligada
    private int size; // Tamanho da tabela hash

    // Construtor da tabela hash
    @SuppressWarnings("unchecked")
    public Hash(int size) {
        this.size = size;
        table = new LinkedList[size]; // Cria a tabela com o número de posições fornecido
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>(); // Inicializa cada posição com uma lista ligada vazia
        }
    }

    // Função de hash: calcula o índice da tabela baseado no código hash da chave (nome)
    private int hash(String nome) {
        return Math.abs(nome.hashCode()) % size; // Retorna o índice da tabela
    }

    // Insere uma Reserva na tabela hash
    public void inserir(Reserva Reserva) {
        String nome = Reserva.getNome(); // chave (nome) da reserva
        int index = hash(nome); // Calcula o índice na tabela usando a função hash

        // Procura a entrada da chave na lista ligada correspondente ao índice
        for (HashEntry entry : table[index]) {
            if (entry.nome.equals(nome)) {
                entry.Reservas.add(Reserva); // Se a mesma chave já existir, adiciona a reserva à lista
                return;
            }
        }

        // Se a chave não existir, cria uma nova entrada e adiciona a reserva
        HashEntry novoHash = new HashEntry(nome);
        novoHash.Reservas.add(Reserva);
        table[index].add(novoHash); // Adiciona a nova entrada na lista ligada
    }

    // Retorna todas as reservas associadas a uma chave
    public List<Reserva> get(String nome) {
        int index = hash(nome); // Calcula o índice na tabela usando a função hash

        // Procura a entrada da chave na lista ligada correspondente ao índice
        for (HashEntry entry : table[index]) {
            if (entry.nome.equals(nome)) {
                return entry.Reservas; // Se a chave for encontrada, retorna a lista de reservas
            }
        }

        return new ArrayList<>(); // Se a chave não for encontrada, retorna uma lista vazia
    }
}
