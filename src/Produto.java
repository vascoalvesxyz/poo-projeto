public abstract class Produto {

    public enum TipoProduto {
        ALIMENTAR,
        FARMACEUTICO
    }

    protected int codigo;
    protected String nome;
    protected String descrição;
    protected int quantidade;
    protected int valorUnitario;

    private TipoProduto tipo;

    public Produto(int codigo, String nome, String descrição, int quantidade, int valorUnitario, TipoProduto tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.descrição = descrição;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }

    public boolean isAlimentar() {
        if (this.tipo == TipoProduto.ALIMENTAR)
            return true;
        return false;
    }

    abstract public double calcIva(Cliente.Localizacao localizacao);
    abstract public String toString();
}
