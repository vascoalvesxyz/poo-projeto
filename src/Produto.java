import java.util.Scanner;

public abstract class Produto {

    protected int codigo;
    protected String nome;
    protected String descrição;
    protected int quantidade;
    protected int valorUnitario;

    public Produto(int codigo, String nome, String descrição, int quantidade, int valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descrição = descrição;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    abstract public double calcIva(Cliente.Localizacao localizacao);
    abstract public String toString();
}
