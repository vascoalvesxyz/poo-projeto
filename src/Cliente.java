import Fatura.Categoria;
import ProdutoAlimentar.Taxa;

public class Cliente {

    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }

    private String nome;
    private int contribuinte;
    private Localizacao localizacao;

    Fatura[] faturas;

    // Constructor
    public Cliente(
            String nome,
            int contribuinte,
            Localizacao localizacao,
            Fatura[] faturas
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.faturas = faturas;
    }

    private int[][] tabelaIvaProdutoAlimentar = {
            /* Continente, Madeira, Açores */
            { 6, 5, 4 }, /* 0, Taxa Reduzida */
            { 13, 12, 9 }, /* 1, Taxa Intermédia */
            { 23, 22, 16 } /* 2, Taxa Normal */
    };

    private int[][] tabelaIvaProdutoFarmaceutico = {
            /* Continente, Madeira, Açores */
            { 13, 12, 9 }, /* 0, Sem Prescrição */
            { 6, 5, 4 }, /* 1, Com Prescrição */
    };

    private int calcIvaProduto(Produto prod) {
        int local = this.localizacao.ordinal();
        int taxa = 0, extras = 0;

        if (prod.tipo == TipoProduto.ALIMENTAR) {
            taxa = tabelaIvaProdutoAlimentar[prod.taxa.ordinal()][local];
            if (prod.categoria == Categoria.VINHO && prod.taxa == Taxa.INTERMEDIA) {
                extras = 1;
            } else if (prod.certificacoes.length == 4 && prod.taxa == Taxa.REDUZIDA) {
                extras = -1;
            }
        } else {
            int temPrescricao = prod.temPrescricao ? 1 : 0;
            taxa = tabelaIvaProdutoFarmaceutico[temPrescricao][local];
            if (!prod.temPrescricao && prod.categoria == Categoria.ANIMAIS) {
                extras = -1;
            }
        }

        return taxa + extras;
    }

    // toString
    public String toString() {
        return String.format("%s, %d, %s", nome, contribuinte, localizacaoToString());
    }

    private String localizacaoToString() {
        String loc;
        switch (this.localizacao) {
            case PORTUGAL:
                loc = "Portugal";
                break;
            case MADEIRA:
                loc = "Madeira";
                break;
            case ACORES:
                loc = "Açores";
                break;
            default:
                loc = "Desconhecido";
        }
        return loc;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(int contribuinte) {
        this.contribuinte = contribuinte;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Fatura[] getFaturas() {
        return faturas;
    }

    public void setFaturas(Fatura[] faturas) {
        this.faturas = faturas;
    }

    public int[][] getTabelaIvaProdutoAlimentar() {
        return tabelaIvaProdutoAlimentar;
    }

    public void setTabelaIvaProdutoAlimentar(int[][] tabelaIvaProdutoAlimentar) {
        this.tabelaIvaProdutoAlimentar = tabelaIvaProdutoAlimentar;
    }

    public int[][] getTabelaIvaProdutoFarmaceutico() {
        return tabelaIvaProdutoFarmaceutico;
    }

    public void setTabelaIvaProdutoFarmaceutico(int[][] tabelaIvaProdutoFarmaceutico) {
        this.tabelaIvaProdutoFarmaceutico = tabelaIvaProdutoFarmaceutico;
    }
}
