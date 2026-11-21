package emprestimo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LerArquivo {

    /**
     * Lê um arquivo no formato
     * codigoEmprestimo;nomeUsuario;tituloLivro;autor;dataEmprestimo;dataDevolucao
     * e devolve a lista de empréstimos.
     */
    public static ArrayList<Emprestimo> lerArquivo(String caminho) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = limparLinha(linha);
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    try {
                        int codigo = Integer.parseInt(dados[0].trim());
                        emprestimos.add(new Emprestimo(codigo, dados[1], dados[2], dados[3], dados[4], dados[5]));
                    } catch (NumberFormatException e) {
                        System.err.println("Código inválido: " + linha);
                    }
                } else {
                    System.err.println("Linha inválida: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo: " + caminho + " (" + e.getMessage() + ")");
        }

        return emprestimos;
    }

    /** Lê o arquivo de nomes a pesquisar (um nome por linha). */
    public static ArrayList<String> lerNomes(String caminho) {
        ArrayList<String> nomes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = limparLinha(linha);
                if (!linha.isEmpty()) nomes.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo: " + caminho + " (" + e.getMessage() + ")");
        }

        return nomes;
    }

    /** Remove o BOM (caractere invisível no início de alguns arquivos UTF-8) e espaços nas pontas. */
    private static String limparLinha(String linha) {
        if (!linha.isEmpty() && linha.charAt(0) == '﻿') {
            linha = linha.substring(1);
        }
        return linha.trim();
    }
}
