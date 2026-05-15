package concrete;

public class Indicador {
    private String nome;
    private String valor;
    private String analise;

    public Indicador(String nome, String valor, String analise) {
        this.nome = nome;
        this.valor = valor;
        this.analise = analise;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    public String getAnalise() {
        return analise;
    }
}