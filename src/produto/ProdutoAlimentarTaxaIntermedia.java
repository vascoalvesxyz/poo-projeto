package produto;

public class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {

    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    private final Categoria categoria;

    public ProdutoAlimentarTaxaIntermedia(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.categoria = categoria;
    }

    /* Devolve o IVA atribuido a este produto baseado na localização */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        /* Continente, Madeira, Açores */
        final int[] tabelaIva = {13, 12, 9};
        double taxa = tabelaIva[localizacao.ordinal()];
        if (categoria == Categoria.VINHO) {
            taxa += 1;
        }
        if (biologico) {
            taxa = taxa * 0.9;
        }
        return taxa;
    }

    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Categoria: %s"
                , this.codigo, this.nome, this.quantidade, this.valorUnitario, categoria
        );
    }

    @Override
    public String toFile() {
        return String.format(
                "ProdutoAlimentarTaxaIntermedia;%d;%s;%s;%d;%.2f;%s;%s\n"
                , codigo, nome, descricao, quantidade, valorUnitario, biologico, categoria
        );
    }
}
