package produto;

/**
 * Classe abstrata que representa um produto de farmácia.
 */
public abstract class ProdutoFarmacia extends Produto {

    /**
     * Construtor para inicializar os atributos do produto de farmácia.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     */
    public ProdutoFarmacia(int codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
    }
}
