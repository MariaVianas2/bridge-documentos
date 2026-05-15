package factory;

import concrete.GeradorArquivoHTML;
import concrete.GeradorArquivoJSON;
import concrete.GeradorArquivoPDF;
import concrete.GeradorArquivoTXT;
import concrete.GeradorArquivoXML;
import implementation.GeradorArquivo;

public class GeradorArquivoFactory {

    public static GeradorArquivo criar(TipoFormato tipoFormato, String nomeArquivo) {
        switch (tipoFormato) {
            case TXT:
                return new GeradorArquivoTXT(nomeArquivo);

            case HTML:
                return new GeradorArquivoHTML(nomeArquivo);

            case PDF:
                return new GeradorArquivoPDF(nomeArquivo);

            case JSON:
                return new GeradorArquivoJSON(nomeArquivo);

            case XML:
                return new GeradorArquivoXML(nomeArquivo);

            default:
                throw new IllegalArgumentException("Formato não suportado: " + tipoFormato);
        }
    }
}