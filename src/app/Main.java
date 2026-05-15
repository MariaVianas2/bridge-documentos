package app;

import abstraction.Documento;
import concrete.ContratoSimplificado;
import concrete.NotaFiscal;
import concrete.PropostaComercial;
import concrete.RelatorioGerencial;
import factory.GeradorArquivoFactory;
import factory.TipoFormato;
import implementation.GeradorArquivo;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== SISTEMA DE DOCUMENTOS COM PADRÃO BRIDGE =====\n");

        gerarRelatorioGerencialEmPDF();
        gerarPropostaComercialEmHTML();
        gerarNotaFiscalEmJSON();
        gerarContratoEmTXT();

        System.out.println("\n===== DESAFIO EXTRA: EXPORTAÇÃO EM XML =====\n");

        gerarNotaFiscalEmXML();

        System.out.println("\nTodos os arquivos foram gerados na pasta: saida");
    }

    private static void gerarRelatorioGerencialEmPDF() {
        GeradorArquivo gerador = GeradorArquivoFactory.criar(TipoFormato.PDF, "relatorio_gerencial");

        RelatorioGerencial relatorio = new RelatorioGerencial(
                "TechPlus Soluções Empresariais",
                "Abril/2026",
                85000.00,
                51400.00,
                "A empresa apresentou lucro no período e aumento na retenção de clientes.",
                "Recomenda-se manter o investimento em suporte e revisar custos operacionais do setor comercial.",
                gerador
        );

        relatorio.adicionarIndicador(
                "Faturamento mensal",
                "R$ 85.000,00",
                "crescimento de 12% em relação ao mês anterior"
        );

        relatorio.adicionarIndicador(
                "Clientes ativos",
                "320",
                "base estável, com entrada de 18 novos clientes"
        );

        relatorio.adicionarIndicador(
                "Chamados resolvidos",
                "94%",
                "equipe manteve bom índice de atendimento"
        );

        Documento documento = relatorio;
        documento.gerar();
    }

    private static void gerarPropostaComercialEmHTML() {
        GeradorArquivo gerador = GeradorArquivoFactory.criar(TipoFormato.HTML, "proposta_comercial");

        PropostaComercial proposta = new PropostaComercial(
                "TechPlus Soluções Empresariais",
                "Mercado Bom Preço LTDA",
                LocalDate.of(2026, 5, 30),
                "20 dias úteis após aprovação",
                "50% na aprovação e 50% na entrega final",
                gerador
        );

        proposta.adicionarServico("Implantação de sistema de controle de estoque", 1, 4200.00);
        proposta.adicionarServico("Treinamento da equipe administrativa", 2, 750.00);
        proposta.adicionarServico("Suporte técnico mensal", 3, 600.00);

        Documento documento = proposta;
        documento.gerar();
    }

    private static void gerarNotaFiscalEmJSON() {
        GeradorArquivo gerador = GeradorArquivoFactory.criar(TipoFormato.JSON, "nota_fiscal");

        NotaFiscal notaFiscal = criarNotaFiscalExemplo(gerador);

        Documento documento = notaFiscal;
        documento.gerar();
    }

    private static void gerarContratoEmTXT() {
        GeradorArquivo gerador = GeradorArquivoFactory.criar(TipoFormato.TXT, "contrato_simplificado");

        ContratoSimplificado contrato = new ContratoSimplificado(
                "Mercado Bom Preço LTDA",
                "12.345.678/0001-90",
                "TechPlus Soluções Empresariais",
                "98.765.432/0001-10",
                "Prestação de serviços de manutenção, suporte técnico e acompanhamento do sistema administrativo.",
                6800.00,
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 8, 1),
                gerador
        );

        contrato.adicionarClausula("A contratada deverá prestar suporte técnico durante o horário comercial.");
        contrato.adicionarClausula("A contratante deverá fornecer as informações necessárias para execução do serviço.");
        contrato.adicionarClausula("O pagamento será realizado conforme as condições acordadas entre as partes.");
        contrato.adicionarClausula("As partes poderão encerrar o contrato mediante aviso prévio de 15 dias.");

        Documento documento = contrato;
        documento.gerar();
    }

    private static void gerarNotaFiscalEmXML() {
        GeradorArquivo gerador = GeradorArquivoFactory.criar(TipoFormato.XML, "nota_fiscal_bonus_xml");

        NotaFiscal notaFiscal = criarNotaFiscalExemplo(gerador);

        notaFiscal.gerar();
    }

    private static NotaFiscal criarNotaFiscalExemplo(GeradorArquivo gerador) {
        NotaFiscal notaFiscal = new NotaFiscal(
                "NF-2026-0001",
                LocalDate.of(2026, 5, 10),
                "TechPlus Soluções Empresariais",
                "Mercado Bom Preço LTDA",
                "12.345.678/0001-90",
                8.5,
                gerador
        );

        notaFiscal.adicionarItem("Licença de software administrativo", 1, 3500.00);
        notaFiscal.adicionarItem("Configuração inicial do sistema", 1, 1200.00);
        notaFiscal.adicionarItem("Horas de suporte técnico", 6, 180.00);

        return notaFiscal;
    }
}