package concrete;

import abstraction.Documento;
import implementation.GeradorArquivo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RelatorioGerencial extends Documento {
    private String empresa;
    private String periodo;
    private double receita;
    private double despesas;
    private String resultadoGeral;
    private String observacoes;
    private List<Indicador> indicadores;

    public RelatorioGerencial(String empresa, String periodo, double receita, double despesas,
                              String resultadoGeral, String observacoes, GeradorArquivo gerador) {
        super(gerador);
        this.empresa = empresa;
        this.periodo = periodo;
        this.receita = receita;
        this.despesas = despesas;
        this.resultadoGeral = resultadoGeral;
        this.observacoes = observacoes;
        this.indicadores = new ArrayList<Indicador>();
    }

    public void adicionarIndicador(String nome, String valor, String analise) {
        indicadores.add(new Indicador(nome, valor, analise));
    }

    @Override
    public void gerar() {
        StringBuilder conteudo = new StringBuilder();
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        double lucro = receita - despesas;

        conteudo.append("RELATÓRIO GERENCIAL\n");
        conteudo.append("Empresa: ").append(empresa).append("\n");
        conteudo.append("Período analisado: ").append(periodo).append("\n\n");

        conteudo.append("RESUMO FINANCEIRO\n");
        conteudo.append("Receita: ").append(moeda.format(receita)).append("\n");
        conteudo.append("Despesas: ").append(moeda.format(despesas)).append("\n");
        conteudo.append("Resultado líquido: ").append(moeda.format(lucro)).append("\n\n");

        conteudo.append("INDICADORES\n");
        for (Indicador indicador : indicadores) {
            conteudo.append("- ").append(indicador.getNome()).append(": ")
                    .append(indicador.getValor()).append(" | Análise: ")
                    .append(indicador.getAnalise()).append("\n");
        }

        conteudo.append("\nRESULTADO GERAL\n");
        conteudo.append(resultadoGeral).append("\n\n");

        conteudo.append("OBSERVAÇÕES\n");
        conteudo.append(observacoes).append("\n");

        gerador.escrever(conteudo.toString());
    }
}