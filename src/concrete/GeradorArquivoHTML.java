package concrete;

public class GeradorArquivoHTML extends AbstractGeradorArquivo {

    public GeradorArquivoHTML(String nomeArquivo) {
        super(nomeArquivo, ".html");
    }

    @Override
    public void escrever(String conteudo) {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Documento Empresarial</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background: #f4f6f8; margin: 0; padding: 30px; }\n" +
                "        .documento { background: white; max-width: 900px; margin: auto; padding: 30px; border-radius: 10px; box-shadow: 0 0 12px rgba(0,0,0,0.12); }\n" +
                "        pre { white-space: pre-wrap; font-size: 15px; line-height: 1.6; color: #222; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"documento\">\n" +
                "        <pre>" + escaparHtml(conteudo) + "</pre>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        salvarArquivo(html);
    }
}