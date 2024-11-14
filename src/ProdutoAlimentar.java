public class ProdutoAlimentar extends Produto {

    public enum Taxa {
        REDUZIDA,
        INTERMEDIA,
        NORMAL
    }

    public enum Certificacoes {
        ISO22000, FSSC22000,
        HACCP,
        GMP
    }

    public enum CategoriaAlimentar {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    private Taxa taxa;
    private Certificacoes certificacoes;
    private CategoriaAlimentar categoria;
    private boolean biologico;

    // Constructor Padrão ou Para Taxa Normal
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.NORMAL; 
        this.biologico = biologico;
    }

    // Constructor Para Taxa Reduzida
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario,  boolean biologico, Certificacoes[] certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.REDUZIDA;
        this.biologico = biologico;
        this.certificacoes = certificacoes;
    }

    // Constructor Para Taxa Intermédia
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, CategoriaAlimentar categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.INTERMEDIA;
        this.biologico = biologico;
        this.categoria = categoria;
    }

    public Certificacoes[] getCertificacoes() {
        return this.certificacoes;
    }

    public CategoriaAlimentar getCategoriaAlimentar() {
        return this.categoria;
    }

    public String toString() {

        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);

        String concat;
        if (this.taxa) {
            case REDUZIDA:
                concat = String.format(", Certificações: %s\n", certificacoesToString() );
                break;
            case INTERMEDIA:
                concat = String.format(", Categoria: %s\n", categoriaToString() );
                break;
            default:
                concat = "\n";
                break;
        }

        str.concat(concat);
        return str;
    }

    private String certificacoesToString() {
        String res = "";
        for (Certificacoes cert: this.certificacoes) {
            String s;
            switch(cert) {
                case ISO22000:
                    s = "ISO22000 "; 
                    break;
                case FSSC22000: 
                    s = "FSSC22000 ";
                    break;
                case HACCP:
                    s = "HACCP ";
                    break;
                case GMP:
                    s = "GMP ";
                    break;
            }
            res.concat(s);
        }
        return res;
    }

    private String categoriaToString() {
        String res;
        switch(this.categoria) {
            case CONGELADOS:
                res = "Congelados";
                break;
            case ENLATADOS:
                res = "Enlatados";
                break;
            case VINHO:
                res = "Vinho";
                break;
        }
        return res;
    }

}
