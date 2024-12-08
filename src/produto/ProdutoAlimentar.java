package produto;

/**
 * Classe abstrata que representa um produto alimentar.
 * Inclui atributos comuns aos produtos alimentares, como a indicação de ser biológico.
 */
public abstract class ProdutoAlimentar extends Produto {

    /**
     * Indica se o produto é biológico.
     */
    protected final boolean biologico;

    /**
     * Construtor para inicializar os atributos do produto alimentar.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param biologico     Indica se o produto é biológico.
     */
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.biologico = biologico;
    }
}
