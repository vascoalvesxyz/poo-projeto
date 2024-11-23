package produto;

public class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {

    public enum Certificacao {
        ISO22000,
        FSSC22000,
        HACCP,
        GMP
    }

    private Certificacao[] certificacoes;

    // Constructor Para Taxa Reduzida
    public ProdutoAlimentarTaxaReduzida(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, Certificacao[] certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = certificacoes;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {6, 5, 4};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (certificacoes.length == 4) taxa = taxa - 1;
        if (biologico) taxa = taxa * 0.9;
        return taxa;
    }

    @Override
    public String toString() {
        String str = String.format("""
        Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d, Certificações: %s
        """, this.codigo, this.nome, this.quantidade, this.valorUnitario, certificacoesToString());
        return str;
    }

    private String certificacoesToString() {
        String res = "";
        for (Certificacao cert : this.certificacoes) {
            res = res.concat(certificacoes + " ");
        }
        return res;
    }

}
