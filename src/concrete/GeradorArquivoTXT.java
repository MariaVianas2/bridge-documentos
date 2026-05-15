package concrete;

public class GeradorArquivoTXT extends AbstractGeradorArquivo {

    public GeradorArquivoTXT(String nomeArquivo) {
        super(nomeArquivo, ".txt");
    }

    @Override
    public void escrever(String conteudo) {
        salvarArquivo(conteudo);
    }
}