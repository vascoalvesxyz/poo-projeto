package produto;

public abstract class Produto {

    protected int codigo;
    protected String nome;
    protected String descricao;
    protected int quantidade;
    protected double valorUnitario;

    public Produto(int codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public int getCodigo() {
        return codigo;
    }

    abstract public double calcIva(Cliente.Localizacao localizacao);

    abstract public String toString();
}
