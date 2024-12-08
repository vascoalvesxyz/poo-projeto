package produto;

/**
 * Classe que representa um produto alimentar com taxa normal de IVA.
 * Inclui a lógica para cálculo do IVA e formatação das informações do produto.
 */
public class ProdutoAlimentarTaxaNormal extends ProdutoAlimentar {

    /**
     * Construtor para inicializar os atributos do produto alimentar com taxa normal.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param biologico     Indica se o produto é biológico.
     */
    public ProdutoAlimentarTaxaNormal(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        final int[] tabelaIva = {23, 22, 16}; // IVA para Continente, Madeira e Açores
        double taxa = tabelaIva[localizacao.ordinal()];
        if (biologico) {
            taxa = taxa * 0.9;
        }
        return taxa;
    }

    /**
     * Retorna a representação textual do produto alimentar com taxa normal.
     *
     * @return String representando o produto.
     */
    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f",
                this.codigo, this.nome, this.quantidade, this.valorUnitario
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
                "ProdutoAlimentarTaxaNormal;%d;%s;%s;%d;%.2f;%s\n",
                codigo, nome, descricao, quantidade, valorUnitario, biologico
        );
    }
}
