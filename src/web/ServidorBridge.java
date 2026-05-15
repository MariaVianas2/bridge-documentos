package web;

import abstraction.Documento;
import concrete.ContratoSimplificado;
import concrete.NotaFiscal;
import concrete.PropostaComercial;
import concrete.RelatorioGerencial;
import factory.GeradorArquivoFactory;
import factory.TipoFormato;
import implementation.GeradorArquivo;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.awt.Desktop;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ServidorBridge {

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/", ServidorBridge::mostrarTela);
        servidor.createContext("/style.css", ServidorBridge::mostrarCss);
        servidor.createContext("/gerar", ServidorBridge::gerarDocumento);

        servidor.setExecutor(null);
        servidor.start();

        System.out.println("Servidor iniciado em: http://localhost:8080");

        abrirNavegador();
    }

    private static void mostrarTela(HttpExchange exchange) throws IOException {
        Path caminho = Path.of("public", "index.html");

        if (!Files.exists(caminho)) {
            enviarTexto(exchange, "Arquivo index.html não encontrado.", "text/plain");
            return;
        }

        byte[] resposta = Files.readAllBytes(caminho);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, resposta.length);

        OutputStream os = exchange.getResponseBody();
        os.write(resposta);
        os.close();
    }

    private static void mostrarCss(HttpExchange exchange) throws IOException {
        Path caminho = Path.of("public", "style.css");

        if (!Files.exists(caminho)) {
            enviarTexto(exchange, "Arquivo style.css não encontrado.", "text/plain");
            return;
        }

        byte[] resposta = Files.readAllBytes(caminho);
        exchange.getResponseHeaders().set("Content-Type", "text/css; charset=UTF-8");
        exchange.sendResponseHeaders(200, resposta.length);

        OutputStream os = exchange.getResponseBody();
        os.write(resposta);
        os.close();
    }

    private static void gerarDocumento(HttpExchange exchange) throws IOException {
        Map<String, String> parametros = lerParametros(exchange.getRequestURI().getQuery());

        String tipoDocumento = parametros.getOrDefault("documento", "relatorio");
        String formatoTexto = parametros.getOrDefault("formato", "TXT");

        TipoFormato formato = TipoFormato.valueOf(formatoTexto);

        String nomeBase = tipoDocumento + "_" + formatoTexto.toLowerCase() + "_" + System.currentTimeMillis();

        GeradorArquivo gerador = GeradorArquivoFactory.criar(formato, nomeBase);

        Documento documento = criarDocumento(tipoDocumento, gerador);
        documento.gerar();

        String extensao = obterExtensao(formato);
        Path arquivoGerado = Path.of("saida", nomeBase + extensao);

        if (!Files.exists(arquivoGerado)) {
            enviarTexto(exchange, "Erro: arquivo não foi gerado.", "text/plain");
            return;
        }

        byte[] bytes = Files.readAllBytes(arquivoGerado);

        exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
        exchange.getResponseHeaders().set(
                "Content-Disposition",
                "attachment; filename=\"" + arquivoGerado.getFileName().toString() + "\""
        );

        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static Documento criarDocumento(String tipoDocumento, GeradorArquivo gerador) {
        switch (tipoDocumento) {
            case "relatorio":
                RelatorioGerencial relatorio = new RelatorioGerencial(
                        "TechPlus Soluções Empresariais",
                        "Maio/2026",
                        85000.00,
                        51400.00,
                        "A empresa apresentou lucro e crescimento no período analisado.",
                        "Recomenda-se manter o investimento em suporte e revisar custos operacionais.",
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
                        "base estável com entrada de novos clientes"
                );

                relatorio.adicionarIndicador(
                        "Chamados resolvidos",
                        "94%",
                        "bom índice de atendimento da equipe"
                );

                return relatorio;

            case "nota":
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

            case "proposta":
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

                return proposta;

            case "contrato":
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

                return contrato;

            default:
                throw new IllegalArgumentException("Documento inválido: " + tipoDocumento);
        }
    }

    private static String obterExtensao(TipoFormato formato) {
        switch (formato) {
            case TXT:
                return ".txt";

            case HTML:
                return ".html";

            case PDF:
                return ".pdf.txt";

            case JSON:
                return ".json";

            case XML:
                return ".xml";

            default:
                return ".txt";
        }
    }

    private static Map<String, String> lerParametros(String query) throws IOException {
        Map<String, String> parametros = new HashMap<>();

        if (query == null || query.isEmpty()) {
            return parametros;
        }

        String[] pares = query.split("&");

        for (String par : pares) {
            String[] chaveValor = par.split("=");

            if (chaveValor.length == 2) {
                String chave = URLDecoder.decode(chaveValor[0], StandardCharsets.UTF_8.name());
                String valor = URLDecoder.decode(chaveValor[1], StandardCharsets.UTF_8.name());

                parametros.put(chave, valor);
            }
        }

        return parametros;
    }

    private static void enviarTexto(HttpExchange exchange, String texto, String contentType) throws IOException {
        byte[] resposta = texto.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=UTF-8");
        exchange.sendResponseHeaders(200, resposta.length);

        OutputStream os = exchange.getResponseBody();
        os.write(resposta);
        os.close();
    }

    private static void abrirNavegador() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI.create("http://localhost:8080"));
            }
        } catch (IOException e) {
            System.out.println("Abra manualmente no navegador: http://localhost:8080");
        }
    }
}