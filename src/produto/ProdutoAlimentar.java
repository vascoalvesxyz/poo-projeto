package produto;

public abstract class ProdutoAlimentar extends Produto {

    protected final boolean biologico;

    public ProdutoAlimentar() {
        super();
        this.biologico = true;
    }

    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.biologico = biologico;
    }

}