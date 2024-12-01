package produto;

import java.io.Serializable;

public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 6L;

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
    public double getValorUnitario() {
        return valorUnitario;
    }
    public int getQuantidade() {
        return quantidade;
    }

    abstract public double calcIva(Cliente.Localizacao localizacao);

    abstract public String toString();

}
