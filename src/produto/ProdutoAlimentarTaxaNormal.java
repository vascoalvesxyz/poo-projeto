package produto;

public class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar {

    // Constructor Padrão
    public ProdutoAlimentarTaxaNormal(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {23, 22, 16};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (biologico) taxa = taxa * 0.9;
        return taxa;
    }

    @Override
    public String toString() {
        String str = String.format("""
        Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d
        """, this.codigo, this.nome, this.quantidade, this.valorUnitario);
        return str;
    }

}
