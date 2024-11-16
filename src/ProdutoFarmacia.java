public class ProdutoFarmacia extends Produto {
    
    private enum CategoriaFarmacia {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    private String medico;
    private boolean temPrescricao;
    private CategoriaFarmacia categoria;

    /* Contructor Sem Prescrição */
    public ProdutoFarmacia(int codigo, String nome, String descrição, int quantidade, int valorUnitario, CategoriaFarmacia categoria) {
        super(codigo, nome, descrição, quantidade, valorUnitario, TipoProduto.FARMACEUTICO);
        this.temPrescricao = false;
        this.categoria = categoria;
    }

    /* Contructor Com Prescrição */
    public ProdutoFarmacia(int codigo, String nome, String descrição, int quantidade, int valorUnitario, String medico) {
        super(codigo, nome, descrição, quantidade, valorUnitario, TipoProduto.FARMACEUTICO);
        this.temPrescricao = true;
        this.medico = medico;
    }

    private int[][] tabelaIvaProdutoFarmaceutico = {
            /* Continente, Madeira, Açores */
            { 13, 12, 9 }, /* 0, Sem Prescrição */
            { 6, 5, 4 }, /* 1, Com Prescrição */
    };

    public int calcIva(Cliente.Localizacao localizacao) {
        int idx = this.temPrescricao ? 1 : 0;
        int taxa = tabelaIvaProdutoFarmaceutico[idx][localizacao.ordinal()];
        if (!this.temPrescricao && this.categoria == CategoriaFarmacia.ANIMAIS) {
            taxa += -1;
        }
        return taxa;
    }

    public String toString() {
        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);
        if (temPrescricao) {
            str = str.concat(String.format(" Médico: %s\n", this.medico));
        } else {
            str = str.concat(String.format(" Categoria: %s\n", categoriaToString() ));
        }
        return str;
    }

    private String categoriaToString() {
        String[] categoria = { "Beleza", "Bem-estar", "Bebés", "Animais", "Outro" };
        return categoria[this.categoria.ordinal()];
    }

}
