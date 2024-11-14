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
        this.faturas = faturas
    }

    public String toString() {
        return String.format("%02d, %s,");
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
        }
        return loc;
    }
}
