public enum TipoProduto {
    ALIMENTAR,
    FARMACEUTICO
}

public abstract class Produto {

    int codigo;
    String nome;
    String descrição;
    int quantidade;
    int valorUnitario;

    TipoProduto tipo;

    abstract public String toString();
}
