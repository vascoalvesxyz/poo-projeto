package produto;

public class ProdutoFarmaciaSemReceita extends ProdutoFarmacia {

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    private final Categoria categoria;

    public ProdutoFarmaciaSemReceita() {
        super();
        this.categoria = Categoria.OUTRO;
    }

    public ProdutoFarmaciaSemReceita(int codigo, String nome, String descricao, int quantidade, double valorUnitario, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.categoria = categoria;
    }

    public double calcIva(Cliente.Localizacao localizacao) {
        double taxa = new double[]{13, 12, 9}[localizacao.ordinal()];
        if (this.categoria == Categoria.ANIMAIS) {
            taxa--;
        }
        return taxa;
    }

    public String toString() {
        String str = String.format("Codigo: %d, Nome: %s, Quantidade: %d, Valor (sem IVA): %.2f", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        str = str.concat(String.format(" Categoria: %s\n", categoria));
        return str;
    }

    public String toFile() {
        return String.format(
                "ProdutoFarmaciaPrescrito,%d,%s,%s,%d,%.2f,%s\n"
                , codigo, nome, descricao, quantidade, valorUnitario, categoria
        );
    }

}
