package reserva;

public class Reserva implements Comparable<Reserva> {
    private String codigo;   // R000001
    private String nome;     // Nome da pessoa
    private String voo;    // V947
    private String data;     // 21/04/2024
    private String assento;     

    public Reserva(String codigo, String nome, String voo, String data, String assento) {
        this.codigo = codigo;
        this.nome = nome;
        this.voo = voo;
        this.data = data;
        this.assento = assento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getVoo() {
        return voo;
    }

    public String getData() {
        return data;
    }

    public String getInfo() {
        return assento;
    }

    @Override
    public String toString() {
        return codigo + ";" + nome + ";" + voo + ";" + data + ";" + assento;
    }

    @Override
    public int compareTo(Reserva outre) {
        // Ordena primeiro pelo nome; em caso de nomes iguais, desempata pelo código da reserva
        int nome = this.nome.compareTo(outre.nome);
        if (nome != 0) {
            return nome;
        }
        return this.codigo.compareTo(outre.codigo);
    }
}
