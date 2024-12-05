package produto;

public abstract class ProdutoFarmacia extends Produto {

    public ProdutoFarmacia() {
        super();
    }

    public ProdutoFarmacia(int codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
    }

}