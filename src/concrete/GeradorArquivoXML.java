package concrete;

public class GeradorArquivoXML extends AbstractGeradorArquivo {

    public GeradorArquivoXML(String nomeArquivo) {
        super(nomeArquivo, ".xml");
    }

    @Override
    public void escrever(String conteudo) {
        String[] linhas = conteudo.split("\\n");
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<documento formato=\"XML\">\n");

        for (String linha : linhas) {
            xml.append("    <linha>").append(escaparXml(linha)).append("</linha>\n");
        }

        xml.append("</documento>\n");

        salvarArquivo(xml.toString());
    }
}