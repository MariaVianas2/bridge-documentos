package concrete;

import abstraction.Documento;
import implementation.GeradorArquivo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContratoSimplificado extends Documento {
    private String contratante;
    private String documentoContratante;
    private String contratado;
    private String documentoContratado;
    private String objeto;
    private double valor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<String> clausulas;

    public ContratoSimplificado(String contratante, String documentoContratante,
                                String contratado, String documentoContratado,
                                String objeto, double valor, LocalDate dataInicio,
                                LocalDate dataFim, GeradorArquivo gerador) {
        super(gerador);
        this.contratante = contratante;
        this.documentoContratante = documentoContratante;
        this.contratado = contratado;
        this.documentoContratado = documentoContratado;
        this.objeto = objeto;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.clausulas = new ArrayList<String>();
    }

    public void adicionarClausula(String clausula) {
        clausulas.add(clausula);
    }

    @Override
    public void gerar() {
        StringBuilder conteudo = new StringBuilder();
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        conteudo.append("CONTRATO SIMPLIFICADO DE PRESTAÇÃO DE SERVIÇOS\n");
        conteudo.append("Contratante: ").append(contratante)
                .append(" | Documento: ").append(documentoContratante).append("\n");

        conteudo.append("Contratado: ").append(contratado)
                .append(" | Documento: ").append(documentoContratado).append("\n\n");

        conteudo.append("OBJETO DO CONTRATO\n");
        conteudo.append(objeto).append("\n\n");

        conteudo.append("VIGÊNCIA E VALOR\n");
        conteudo.append("Início: ").append(dataInicio.format(formatoData)).append("\n");
        conteudo.append("Fim: ").append(dataFim.format(formatoData)).append("\n");
        conteudo.append("Valor acordado: ").append(moeda.format(valor)).append("\n\n");

        conteudo.append("CLÁUSULAS BÁSICAS\n");

        for (int i = 0; i < clausulas.size(); i++) {
            conteudo.append(i + 1).append(". ").append(clausulas.get(i)).append("\n");
        }

        conteudo.append("\nASSINATURAS\n");
        conteudo.append("Contratante: ________________________________\n");
        conteudo.append("Contratado: __________________________________\n");

        gerador.escrever(conteudo.toString());
    }
}