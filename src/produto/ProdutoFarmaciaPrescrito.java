package produto;

public class ProdutoFarmaciaPrescrito extends ProdutoFarmacia {
    private final String medico;

    public ProdutoFarmaciaPrescrito(int codigo, String nome, String descricao, int quantidade, double valorUnitario, String medico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.medico = medico;
    }

    public double calcIva(Cliente.Localizacao localizacao) {
        return new double[]{6, 5, 4}[localizacao.ordinal()];
    }

    public String toString() {
        String str = String.format("Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        str = str.concat(String.format(" Médico: %s\n", this.medico));
        return str;
    }

}
