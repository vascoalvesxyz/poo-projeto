package produto;

/**
 * Classe que representa um produto de farmácia sem receita médica.
 * Inclui a lógica para cálculo do IVA e a categoria do produto.
 */
public class ProdutoFarmaciaSemReceita extends ProdutoFarmacia {

    /**
     * Enum que representa as categorias de produtos de farmácia sem receita.
     */
    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    /**
     * Categoria do produto de farmácia.
     */
    private final Categoria categoria;

    /**
     * Construtor para inicializar os atributos do produto de farmácia sem receita.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param categoria     Categoria do produto.
     */
    public ProdutoFarmaciaSemReceita(int codigo, String nome, String descricao, int quantidade, double valorUnitario, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoria = categoria;
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente.
     * Produtos da categoria "ANIMAIS" têm uma redução adicional na taxa de IVA.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        double taxa = new double[]{13, 12, 9}[localizacao.ordinal()];
        if (this.categoria == Categoria.ANIMAIS) {
            taxa--; // Redução adicional para produtos da categoria "ANIMAIS".
        }
        return taxa;
    }

    /**
     * Retorna a representação textual do produto de farmácia sem receita.
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
                "ProdutoFarmaciaSemReceita;%d;%s;%s;%d;%.2f;%s\n",
                codigo, nome, descricao, quantidade, valorUnitario, categoria
        );
    }
}
