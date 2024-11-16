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

}
