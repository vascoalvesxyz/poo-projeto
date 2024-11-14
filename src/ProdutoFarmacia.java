public class ProdutoFarmacia extends Produto {
    
    public enum CategoriaFarmacia {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    private String medico;
    private boolean temPrescricao;

    public ProdutoFarmacia(int codigo, int nome, String descrição, int quantidade, int valorUnitario, enum CategoriaFarmacia categoria) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.temPrescricao = false;
        this.categoria = categoria;
    }

    public ProdutoFarmacia(int codigo, int nome, String descrição, int quantidade, int valorUnitario, String medico) {
        super(codigo, nome, descrição, quantidade, valorUnitario);
        this.temPrescricao = true;
        this.medico = medico;
    }

    public String toString() {

        String str = String.format("Codigo: %02d, Nome: %s, Quantidade: %02d, Valor (sem IVA): %02d", this.codigo, this.nome, this.quantidade, this.valorUnitario);

        if (temPrescricao == true) {
            str.concat(String.format(" Médico: %s\n", this.medico));
        } else {
            str.concat(String.format(" Categoria: %s\n", categoriaToString() ));
        }

        return str;
    }

    private String categoriaToString() {
        String categoria;
        switch (this.categoria) {
            case BELEZA:
                categoria = "Beleza";
                break;
            case BEM_ESTAR:
                categoria = "Bem-estar";
                break;
            case BEBES:
                categoria = "Bebés";
                break;
            case ANIMAIS:
                categoria = "Animais";
                break;
            case OUTRO:
                categoria = "Outro";
                break;
            default:
                categoria = "NA";
                break;
        }
        return categoria;
    }

}
