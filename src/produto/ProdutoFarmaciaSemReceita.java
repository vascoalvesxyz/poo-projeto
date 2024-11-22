package produto;

public class ProdutoFarmaciaSemReceita extends ProdutoFarmacia {

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    private Categoria categoria;

    public ProdutoFarmaciaSemReceita(int codigo, String nome, String descrição, int quantidade, int valorUnitario, Categoria categoria) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.categoria = categoria;
    }

    public double calcIva(Cliente.Localizacao localizacao) {
        double taxa = new double[]{13, 12, 9}[localizacao.ordinal()];
        if (this.categoria == Categoria.ANIMAIS) {
            taxa += -1;
        }
        return taxa;
    }

    public String toString() {
        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        str = str.concat(String.format(" Categoria: %s\n", categoria));
        return str;
    }

}
