package concrete;

import abstraction.Documento;
import implementation.GeradorArquivo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotaFiscal extends Documento {
    private String numero;
    private LocalDate dataEmissao;
    private String empresaEmissora;
    private String cliente;
    private String documentoCliente;
    private double percentualImposto;
    private List<ItemDocumento> itens;

    public NotaFiscal(String numero, LocalDate dataEmissao, String empresaEmissora,
                      String cliente, String documentoCliente, double percentualImposto,
                      GeradorArquivo gerador) {
        super(gerador);
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.empresaEmissora = empresaEmissora;
        this.cliente = cliente;
        this.documentoCliente = documentoCliente;
        this.percentualImposto = percentualImposto;
        this.itens = new ArrayList<ItemDocumento>();
    }

    public void adicionarItem(String descricao, int quantidade, double valorUnitario) {
        itens.add(new ItemDocumento(descricao, quantidade, valorUnitario));
    }

    private double calcularTotalProdutos() {
        double total = 0;

        for (ItemDocumento item : itens) {
            total += item.getSubtotal();
        }

        return total;
    }

    @Override
    public void gerar() {
        StringBuilder conteudo = new StringBuilder();
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        double totalProdutos = calcularTotalProdutos();
        double imposto = totalProdutos * percentualImposto / 100;
        double totalGeral = totalProdutos + imposto;

        conteudo.append("NOTA FISCAL\n");
        conteudo.append("Número: ").append(numero).append("\n");
        conteudo.append("Data de emissão: ").append(dataEmissao.format(formatoData)).append("\n");
        conteudo.append("Empresa emissora: ").append(empresaEmissora).append("\n");
        conteudo.append("Cliente: ").append(cliente).append("\n");
        conteudo.append("Documento do cliente: ").append(documentoCliente).append("\n\n");

        conteudo.append("ITENS\n");

        for (ItemDocumento item : itens) {
            conteudo.append("- ").append(item.getDescricao())
                    .append(" | Qtd: ").append(item.getQuantidade())
                    .append(" | Valor unitário: ").append(moeda.format(item.getValorUnitario()))
                    .append(" | Subtotal: ").append(moeda.format(item.getSubtotal()))
                    .append("\n");
        }

        conteudo.append("\nVALORES\n");
        conteudo.append("Total dos produtos/serviços: ").append(moeda.format(totalProdutos)).append("\n");
        conteudo.append("Imposto aplicado: ").append(percentualImposto).append("%\n");
        conteudo.append("Valor do imposto: ").append(moeda.format(imposto)).append("\n");
        conteudo.append("Total da nota fiscal: ").append(moeda.format(totalGeral)).append("\n");

        gerador.escrever(conteudo.toString());
    }
}