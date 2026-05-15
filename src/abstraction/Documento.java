package abstraction;

import implementation.GeradorArquivo;

public abstract class Documento {
    protected GeradorArquivo gerador;

    public Documento(GeradorArquivo gerador) {
        this.gerador = gerador;
    }

    public abstract void gerar();
}