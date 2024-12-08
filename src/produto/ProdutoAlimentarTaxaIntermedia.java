package produto;

/**
 * Classe que representa um produto alimentar com taxa intermédia de IVA.
 * Inclui propriedades específicas, como a categoria do produto.
 */
public class ProdutoAlimentarTaxaIntermedia extends ProdutoAlimentar {

    /**
     * Enum que representa as categorias de produtos alimentares com taxa intermédia.
     */
    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    /**
     * Categoria do produto alimentar.
     */
    private final Categoria categoria;

    /**
     * Construtor para inicializar os atributos do produto alimentar com taxa intermédia.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param biologico     Indica se o produto é biológico.
     * @param categoria     Categoria do produto alimentar.
     */
    public ProdutoAlimentarTaxaIntermedia(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.categoria = categoria;
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente e na categoria do produto.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
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

    /**
     * Retorna a representação textual do produto alimentar com taxa intermédia.
     *
     * @return String representando o produto.
     */
    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Categoria: %s",
                this.codigo, this.nome, this.quantidade, this.valorUnitario, categoria
        );
    }

    /**
     * Retorna a representação do produto para ser escrita em ficheiros.
     *
     * @return String formatada para ficheiro.
     */
    @Override
    public String toFile() {
        return String.format(
                "ProdutoAlimentarTaxaIntermedia;%d;%s;%s;%d;%.2f;%s;%s\n",
                codigo, nome, descricao, quantidade, valorUnitario, biologico, categoria
        );
    }
}
