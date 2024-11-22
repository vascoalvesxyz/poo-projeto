public class ProdutoFarmacia extends Produto {

    private String medico;
    private final boolean temPrescricao;
    private Categoria categoria;
    private final int[][] tabelaIvaProdutoFarmaceutico = {
            /* Continente, Madeira, Açores */
            {13, 12, 9}, /* 0, Sem Prescrição */
            {6, 5, 4}, /* 1, Com Prescrição */
    };

    /* Contructor Sem Prescrição */
    public ProdutoFarmacia(int codigo, String nome, String descrição, int quantidade, int valorUnitario, Categoria categoria) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.temPrescricao = false;
        this.categoria = categoria;
    }

    /* Contructor Com Prescrição */
    public ProdutoFarmacia(int codigo, String nome, String descrição, int quantidade, int valorUnitario, String medico) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.temPrescricao = true;
        this.medico = medico;
    }

    public double calcIva(Cliente.Localizacao localizacao) {
        int idx = this.temPrescricao ? 1 : 0;
        double taxa = tabelaIvaProdutoFarmaceutico[idx][localizacao.ordinal()];
        if (!this.temPrescricao && this.categoria == Categoria.ANIMAIS) {
            taxa += -1;
        }
        return taxa;
    }

    public String toString() {
        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        if (temPrescricao) {
            str = str.concat(String.format(" Médico: %s\n", this.medico));
        } else {
            str = str.concat(String.format(" Categoria: %s\n", categoria));
        }
        return str;
    }

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

}