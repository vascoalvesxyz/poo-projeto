package Produto;

public class ProdutoFarmaciaPrescrito extends ProdutoFarmacia {

    private String medico;

    public ProdutoFarmaciaPrescrito(int codigo, String nome, String descrição, int quantidade, int valorUnitario, String medico) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.medico = medico;
    }

    public double calcIva(Cliente.Localizacao localizacao) {
        return new double[] {6, 5, 4}[localizacao.ordinal()];
    }

    public String toString() {
        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        str = str.concat(String.format(" Médico: %s\n", this.medico));
        return str;
    }

}
