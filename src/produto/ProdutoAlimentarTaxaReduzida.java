package produto;

import java.util.ArrayList;

/**
 * Classe que representa um produto alimentar com taxa reduzida de IVA.
 * Inclui a lógica para cálculo do IVA e as certificações associadas ao produto.
 */
public class ProdutoAlimentarTaxaReduzida extends ProdutoAlimentar {

    /**
     * Enum que representa as certificações possíveis de um produto alimentar.
     */
    public enum Certificacao {
        ISO22000,
        FSSC22000,
        HACCP,
        GMP
    }

    /**
     * Lista de certificações associadas ao produto.
     */
    private final ArrayList<Certificacao> certificacoes;

    /**
     * Construtor para inicializar os atributos do produto alimentar com taxa reduzida.
     *
     * @param codigo        Código único do produto.
     * @param nome          Nome do produto.
     * @param descricao     Descrição do produto.
     * @param quantidade    Quantidade disponível do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     * @param biologico     Indica se o produto é biológico.
     * @param certificacoes Lista de certificações associadas ao produto.
     */
    public ProdutoAlimentarTaxaReduzida(int codigo, String nome, String descricao, int quantidade, double valorUnitario, boolean biologico, ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, biologico);
        this.certificacoes = certificacoes;
    }

    /**
     * Calcula o IVA aplicado ao produto com base na localização do cliente.
     * Certificações completas ou produtos biológicos podem alterar o valor do IVA.
     *
     * @param localizacao Localização do cliente.
     * @return Valor da taxa de IVA aplicada.
     */
    @Override
    public double calcIva(Cliente.Localizacao localizacao) {
        final int[] tabelaIva = {6, 5, 4}; // IVA para Continente, Madeira e Açores
        double taxa = tabelaIva[localizacao.ordinal()];
        if (certificacoes.size() == 4) {
            taxa -= 1; // Redução adicional para produtos com todas as certificações.
        }
        if (biologico) {
            taxa *= 0.9; // Redução de 10% para produtos biológicos.
        }
        return taxa;
    }

    /**
     * Retorna a representação textual do produto alimentar com taxa reduzida.
     *
     * @return String representando o produto.
     */
    @Override
    public String toString() {
        return String.format(
                "Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f, Certificações: %s",
                this.codigo, this.nome, this.quantidade, this.valorUnitario, certificacoesToString(" ")
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
                "ProdutoAlimentarTaxaReduzida;%d;%s;%s;%d;%.2f;%s;%s\n",
                codigo, nome, descricao, quantidade, valorUnitario, biologico, certificacoesToString("-")
        );
    }

    /**
     * Converte a lista de certificações para uma string, separada pelo delimitador fornecido.
     *
     * @param sep Separador utilizado para juntar as certificações.
     * @return String das certificações.
     */
    private String certificacoesToString(String sep) {
        StringBuilder res = new StringBuilder();
        for (Certificacao cert : certificacoes) {
            if (!res.isEmpty()) {
                res.append(sep);
            }
            res.append(cert);
        }
        return res.toString();
    }
}
