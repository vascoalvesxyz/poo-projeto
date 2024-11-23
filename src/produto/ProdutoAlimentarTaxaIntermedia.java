package produto;

public class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {

    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    public Categoria categoria;

    public ProdutoAlimentarTaxaIntermedia(int codigo, String nome, String descricao, int quantidade, int valorUnitario, boolean biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.categoria = categoria;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {13, 12, 9};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (categoria == Categoria.VINHO) taxa += 1;
        if (biologico) taxa = taxa * 0.9;
        return taxa;
    }

    @Override
    public String toString() {
        String str = String.format("""
        Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d, Categoria: %s
        """, this.codigo, this.nome, this.quantidade, this.valorUnitario, categoria);
        return str;
    }
}
