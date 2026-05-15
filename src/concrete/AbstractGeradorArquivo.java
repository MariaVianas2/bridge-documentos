package concrete;

import implementation.GeradorArquivo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractGeradorArquivo implements GeradorArquivo {
    private String nomeArquivo;
    private String extensao;

    public AbstractGeradorArquivo(String nomeArquivo, String extensao) {
        this.nomeArquivo = limparNome(nomeArquivo);
        this.extensao = extensao;
    }

    protected void salvarArquivo(String conteudoFinal) {
        try {
            Files.createDirectories(Paths.get("saida"));
            Path caminho = Paths.get("saida", nomeArquivo + extensao);
            Files.write(caminho, conteudoFinal.getBytes(StandardCharsets.UTF_8));
            System.out.println("Arquivo gerado: " + caminho.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo: " + e.getMessage());
        }
    }

    private String limparNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "documento";
        }

        return nome.toLowerCase()
                .replace("ç", "c")
                .replace("ã", "a")
                .replace("á", "a")
                .replace("à", "a")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("õ", "o")
                .replace("ú", "u")
                .replaceAll("[^a-z0-9_-]", "_")
                .replaceAll("_+", "_");
    }

    protected String escaparHtml(String texto) {
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    protected String escaparJson(String texto) {
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\t", "\\t");
    }

    protected String escaparXml(String texto) {
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}