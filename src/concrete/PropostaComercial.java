package concrete;

import abstraction.Documento;
import implementation.GeradorArquivo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PropostaComercial extends Documento {
    private String empresa;
    private String cliente;
    private LocalDate validade;
    private String prazoExecucao;
    private String condicoesPagamento;
    private List<ItemDocumento> servicos;

    public PropostaComercial(String empresa, String cliente, LocalDate validade,
                             String prazoExecucao, String condicoesPagamento,
                             GeradorArquivo gerador) {
        super(gerador);
        this.empresa = empresa;
        this.cliente = cliente;
        this.validade = validade;
        this.prazoExecucao = prazoExecucao;
        this.condicoesPagamento = condicoesPagamento;
        this.servicos = new ArrayList<ItemDocumento>();
    }

    public void adicionarServico(String descricao, int quantidade, double valorUnitario) {
        servicos.add(new ItemDocumento(descricao, quantidade, valorUnitario));
    }

    private double calcularTotal() {
        double total = 0;

        for (ItemDocumento servico : servicos) {
            total += servico.getSubtotal();
        }

        return total;
    }

    @Override
    public void gerar() {
        StringBuilder conteudo = new StringBuilder();
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        conteudo.append("PROPOSTA COMERCIAL\n");
        conteudo.append("Empresa responsável: ").append(empresa).append("\n");
        conteudo.append("Cliente: ").append(cliente).append("\n");
        conteudo.append("Validade da proposta: ").append(validade.format(formatoData)).append("\n");
        conteudo.append("Prazo de execução: ").append(prazoExecucao).append("\n\n");

        conteudo.append("SERVIÇOS/PRODUTOS PROPOSTOS\n");

        for (ItemDocumento servico : servicos) {
            conteudo.append("- ").append(servico.getDescricao())
                    .append(" | Qtd: ").append(servico.getQuantidade())
                    .append(" | Valor unitário: ").append(moeda.format(servico.getValorUnitario()))
                    .append(" | Subtotal: ").append(moeda.format(servico.getSubtotal()))
                    .append("\n");
        }

        conteudo.append("\nCONDIÇÕES COMERCIAIS\n");
        conteudo.append("Pagamento: ").append(condicoesPagamento).append("\n");
        conteudo.append("Valor total da proposta: ").append(moeda.format(calcularTotal())).append("\n");

        gerador.escrever(conteudo.toString());
    }
}