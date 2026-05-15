package concrete;

public class GeradorArquivoPDF extends AbstractGeradorArquivo {

    public GeradorArquivoPDF(String nomeArquivo) {
        super(nomeArquivo, ".pdf.txt");
    }

    @Override
    public void escrever(String conteudo) {
        String pdfSimulado = "========================================\n" +
                "          PDF SIMULADO VIA CONSOLE       \n" +
                "========================================\n\n" +
                conteudo +
                "\n========================================\n" +
                "              FIM DO PDF SIMULADO        \n" +
                "========================================\n";

        System.out.println(pdfSimulado);
        salvarArquivo(pdfSimulado);
    }
}