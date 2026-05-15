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
import java.util.Scanner;

public class AppConfiguravel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== SISTEMA CONFIGURÁVEL =====");

        System.out.println("Escolha o documento:");
        System.out.println("1 - Relatório Gerencial");
        System.out.println("2 - Nota Fiscal");
        System.out.println("3 - Proposta Comercial");
        System.out.println("4 - Contrato Simplificado");
        System.out.print("Opção: ");
        int opcaoDocumento = scanner.nextInt();

        System.out.println("\nEscolha o formato:");
        System.out.println("1 - TXT");
        System.out.println("2 - HTML");
        System.out.println("3 - PDF simulado");
        System.out.println("4 - JSON");
        System.out.println("5 - XML");
        System.out.print("Opção: ");
        int opcaoFormato = scanner.nextInt();

        TipoFormato formato = converterFormato(opcaoFormato);

        GeradorArquivo gerador = GeradorArquivoFactory.criar(
                formato,
                "documento_configuravel"
        );

        Documento documento = criarDocumento(opcaoDocumento, gerador);

        documento.gerar();

        System.out.println("Documento configurável gerado na pasta saida.");

        scanner.close();
    }

    private static TipoFormato converterFormato(int opcao) {
        switch (opcao) {
            case 1:
                return TipoFormato.TXT;

            case 2:
                return TipoFormato.HTML;

            case 3:
                return TipoFormato.PDF;

            case 4:
                return TipoFormato.JSON;

            case 5:
                return TipoFormato.XML;

            default:
                System.out.println("Formato inválido. Usando TXT como padrão.");
                return TipoFormato.TXT;
        }
    }

    private static Documento criarDocumento(int opcao, GeradorArquivo gerador) {
        switch (opcao) {
            case 1:
                RelatorioGerencial relatorio = new RelatorioGerencial(
                        "Empresa Exemplo SA",
                        "Maio/2026",
                        60000.00,
                        41000.00,
                        "Resultado positivo, com margem operacional dentro do esperado.",
                        "A diretoria deve acompanhar o custo fixo nos próximos meses.",
                        gerador
                );

                relatorio.adicionarIndicador(
                        "Margem de lucro",
                        "31,6%",
                        "resultado acima da meta mínima"
                );

                relatorio.adicionarIndicador(
                        "Satisfação dos clientes",
                        "89%",
                        "bom nível de aceitação dos serviços"
                );

                return relatorio;

            case 2:
                NotaFiscal notaFiscal = new NotaFiscal(
                        "NF-2026-0002",
                        LocalDate.of(2026, 5, 10),
                        "Empresa Exemplo SA",
                        "Cliente Demonstração LTDA",
                        "11.222.333/0001-44",
                        7.0,
                        gerador
                );

                notaFiscal.adicionarItem("Consultoria técnica", 2, 500.00);
                notaFiscal.adicionarItem("Instalação de sistema", 1, 900.00);

                return notaFiscal;

            case 3:
                PropostaComercial proposta = new PropostaComercial(
                        "Empresa Exemplo SA",
                        "Cliente Demonstração LTDA",
                        LocalDate.of(2026, 6, 15),
                        "15 dias úteis",
                        "Pagamento em duas parcelas iguais",
                        gerador
                );

                proposta.adicionarServico("Desenvolvimento de página institucional", 1, 2500.00);
                proposta.adicionarServico("Hospedagem e suporte por 3 meses", 3, 250.00);

                return proposta;

            case 4:
                ContratoSimplificado contrato = new ContratoSimplificado(
                        "Cliente Demonstração LTDA",
                        "11.222.333/0001-44",
                        "Empresa Exemplo SA",
                        "55.666.777/0001-88",
                        "Prestação de serviços de suporte e manutenção de sistema.",
                        3200.00,
                        LocalDate.of(2026, 5, 20),
                        LocalDate.of(2026, 7, 20),
                        gerador
                );

                contrato.adicionarClausula("O serviço será prestado remotamente e presencialmente quando necessário.");
                contrato.adicionarClausula("O pagamento deverá ocorrer até o quinto dia útil de cada mês.");
                contrato.adicionarClausula("As partes concordam com sigilo sobre dados acessados durante o serviço.");

                return contrato;

            default:
                System.out.println("Documento inválido. Gerando relatório como padrão.");
                return criarDocumento(1, gerador);
        }
    }
}