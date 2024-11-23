package produto;

public abstract class ProdutoAlimentar extends Produto {

    protected final boolean biologico;

    // Constructor Padrão
    public ProdutoAlimentar(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.biologico = biologico;
    }

}