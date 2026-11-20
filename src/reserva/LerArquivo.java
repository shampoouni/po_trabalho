package reserva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LerArquivo {

    // Lê um arquivo no formato codigo;nome;voo;data;assento e devolve a lista de reservas
    public static ArrayList<Reserva> lerArquivo(String caminho) {
        ArrayList<Reserva> reservas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    reservas.add(new Reserva(dados[0], dados[1], dados[2], dados[3], dados[4]));
                } else {
                    System.err.println("Linha inválida: " + linha);
                }
            }
            System.out.println("Arquivo lido: " + caminho + " — registros: " + reservas.size());
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo: " + caminho + " (" + e.getMessage() + ")");
        }

        return reservas;
    }
}
