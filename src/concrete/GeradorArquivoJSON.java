package concrete;

public class GeradorArquivoJSON extends AbstractGeradorArquivo {

    public GeradorArquivoJSON(String nomeArquivo) {
        super(nomeArquivo, ".json");
    }

    @Override
    public void escrever(String conteudo) {
        String[] linhas = conteudo.split("\\n");
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("  \"formato\": \"JSON\",\n");
        json.append("  \"documento\": [\n");

        for (int i = 0; i < linhas.length; i++) {
            json.append("    \"").append(escaparJson(linhas[i])).append("\"");

            if (i < linhas.length - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}\n");

        salvarArquivo(json.toString());
    }
}