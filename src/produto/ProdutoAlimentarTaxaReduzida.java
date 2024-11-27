package produto;

import java.util.ArrayList;

public class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {

    public enum Certificacao {
        ISO22000,
        FSSC22000,
        HACCP,
        GMP
    }

    private final ArrayList<Certificacao> certificacoes;

    // Constructor Para Taxa Reduzida
    public ProdutoAlimentarTaxaReduzida(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = certificacoes;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {6, 5, 4};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (certificacoes.size() == 4) taxa = taxa - 1;
        if (biologico) taxa = taxa * 0.9;
        return taxa;
    }

    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Certificações: %s",
                this.codigo, this.nome, this.quantidade, this.valorUnitario, certificacoesToString()
        );
    }

    private String certificacoesToString() {
        String res = "";
        for (Certificacao cert : this.certificacoes) {
            res = res.concat(cert + " ");
        }
        return res;
    }

}
