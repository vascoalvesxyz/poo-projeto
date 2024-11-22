package produto;

public class ProdutoAlimentar extends Produto {

    // Tabela Utilizada Para Calcular IVA
    private final int[][] tabelaIvaProdutoAlimentar = {
            /* Continente, Madeira, Açores */
            {6, 5, 4}, /* 0, Taxa Reduzida */
            {13, 12, 9}, /* 1, Taxa Intermédia */
            {23, 22, 16} /* 2, Taxa Normal */
    };

    public Taxa taxa;
    public Categoria categoria;
    private Certificacao[] certificacoes;
    private final boolean biologico;

    // Constructor Padrão ou Para Taxa Normal
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.NORMAL;
        this.biologico = biologico;
    }

    // Constructor Para Taxa Reduzida
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, Certificacao[] certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.REDUZIDA;
        this.biologico = biologico;
        this.certificacoes = certificacoes;
    }

    // Constructor Para Taxa Intermédia
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.taxa = Taxa.INTERMEDIA;
        this.biologico = biologico;
        this.categoria = categoria;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    public double calcIva(Cliente.Localizacao localizacao) {
        double taxa = tabelaIvaProdutoAlimentar[this.taxa.ordinal()][localizacao.ordinal()];
        if (this.taxa == Taxa.INTERMEDIA && this.categoria == Categoria.VINHO) {
            taxa += 1;
        } else if (this.taxa == Taxa.REDUZIDA && this.certificacoes.length == 4) {
            taxa = -1;
        }
        if (biologico) taxa = taxa * 0.9;
        return taxa;
    }

    public String toString() {

        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);

        String concat = "\n";
        if (this.taxa == Taxa.REDUZIDA) {
            concat = String.format(", Certificações: %s\n", certificacoesToString());
        } else if (this.taxa == Taxa.INTERMEDIA) {
            concat = String.format(", Categoria: %s\n", categoria);
        }

        str.concat(concat);
        return str;
    }

    private String certificacoesToString() {
        String res = "";
        for (Certificacao cert : this.certificacoes) {
            res = res.concat(certificacoes + " ");
        }
        return res;
    }

    public enum Taxa {
        REDUZIDA,
        INTERMEDIA,
        NORMAL
    }

    public enum Certificacao {
        ISO22000,
        FSSC22000,
        HACCP,
        GMP
    }

    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }
}