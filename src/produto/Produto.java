package produto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe abstrata que representa um produto genérico.
 * Inclui propriedades comuns a todos os produtos, como código, nome, descrição, quantidade e valor unitário.
 */
public abstract class Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;

    /**
     * Código único do produto.
     */
    protected int codigo;

    /**
     * Nome do produto.
     */
    protected String nome;

    /**
     * Descrição do produto.
     */
    protected String descricao;

    /**
     * Quantidade disponível do produto.
     */
    protected int quantidade;

    /**
     * Valor unitário do produto sem IVA.
     */
    protected double valorUnitario;

    /**
     * Construtor para inicializar os atributos do produto.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível.
     * @param valorUnitario Valor unitário do produto.
     */
    public Produto(int codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    /**
     * Obtém o código do produto.
     *
     * @return Código do produto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtém o valor unitário do produto.
     *
     * @return Valor unitário do produto.
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Obtém a quantidade disponível do produto.
     *
     * @return Quantidade do produto.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    public abstract double calcIva(Cliente.Localizacao localizacao);

    /**
     * Retorna a representação textual do produto.
     *
     * @return String representando o produto.
     */
    public abstract String toString();

    /**
     * Retorna a representação do produto para ser escrita em ficheiros.
     *
     * @return String formatada para ficheiro.
     */
    public abstract String toFile();
}
