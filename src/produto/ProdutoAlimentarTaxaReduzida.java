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

    public ProdutoAlimentarTaxaReduzida(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = certificacoes;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {6, 5, 4};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (certificacoes.size() == 4) {
            taxa = taxa - 1;
        }
        if (biologico) {
            taxa = taxa * 0.9;
        }
        return taxa;
    }

    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Certificações: %s",
                this.codigo, this.nome, this.quantidade, this.valorUnitario, certificacoesToString(" ")
        );
    }

    @Override
    public String toFile() {
        return String.format(
                "ProdutoAlimentarTaxaReduzida;%d;%s;%s;%d;%.2f;%s;%s\n"
                , codigo, nome, descricao, quantidade, valorUnitario, biologico, certificacoesToString("-")
        );
    }

    private String certificacoesToString(String sep) {
        StringBuilder res = new StringBuilder();
        for (Certificacao cert: certificacoes) {
            if (!res.isEmpty()) res.append(sep);
            res.append(cert);
        }
        return res.toString();
    }

}
