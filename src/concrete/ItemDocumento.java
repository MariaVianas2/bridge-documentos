package concrete;

public class ItemDocumento {
    private String descricao;
    private int quantidade;
    private double valorUnitario;

    public ItemDocumento(String descricao, int quantidade, double valorUnitario) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getSubtotal() {
        return quantidade * valorUnitario;
    }
}