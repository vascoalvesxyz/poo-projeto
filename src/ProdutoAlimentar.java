public class ProdutoAlimentar extends Produto {

    public enum Taxa {
        REDUZIDA,
        INTERMEDIA,
        NORMAL
    }

    public enum Certificacoes {
        ISO22000,
        FSSC22000,
        HACCP,
        GMP
    }

    private Taxa taxa;
    private Certificacoes[] certificacoes;
    private boolean biologico;

    public ProdutoAlimentar(int codigo, int nome, String descrição, int quantidade, int valorUnitario,
            Taxa taxa,
            Certificacoes[] certificacoes,
            boolean biologico
        ) {
        super(
                codigo,
                nome,
                descrição,
                quantidade,
                valorUnitario

             );

        this.taxa = taxa;
        this.certificacoes = certificacoes;
        this.biologico = biologico;
    }

}
