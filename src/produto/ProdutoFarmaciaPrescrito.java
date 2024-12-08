package produto;

/**
 * Classe que representa um produto de farmácia prescrito, associado a um médico.
 */
public class ProdutoFarmaciaPrescrito extends ProdutoFarmacia {

    /**
     * Nome do médico que prescreveu o produto.
     */
    private final String medico;

    /**
     * Construtor para inicializar os atributos do produto de farmácia prescrito.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param medico        Nome do médico que prescreveu o produto.
     */
    public ProdutoFarmaciaPrescrito(int codigo, String nome, String descricao, int quantidade, double valorUnitario, String medico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.medico = medico;
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        return new double[]{6, 5, 4}[localizacao.ordinal()]; // IVA para Continente, Madeira e Açores
    }

    /**
     * Retorna a representação textual do produto de farmácia prescrito.
     *
     * @return String representando o produto.
     */
    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Médico: %s",
                this.codigo, this.nome, this.quantidade, this.valorUnitario, this.medico
        );
    }

    /**
     * Retorna a representação do produto para ser escrita em ficheiros.
     *
     * @return String formatada para ficheiro.
     */
    @Override
    public String toFile() {
        return String.format(
                "ProdutoFarmaciaPrescrito;%d;%s;%s;%d;%.2f;%s\n",
                codigo, nome, descricao, quantidade, valorUnitario, medico
        );
    }
}
