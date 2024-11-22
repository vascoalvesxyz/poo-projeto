import java.util.ArrayList;

public class Cliente {

    /* Variaveis */
    private String nome;
    private String contribuinte;
    private Localizacao localizacao;
    private final ArrayList<Fatura> faturas;

    // Constructor
    public Cliente(
            String nome,
            String contribuinte,
            Localizacao localizacao
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        faturas = new ArrayList<>();
    }

    /* Gestão de Faturas */
    public void addFatura(Fatura fatura) {
        faturas.add(fatura);
    }

    public void delFatura(Fatura fatura) {
        faturas.remove(fatura);
    }

    public void printFaturas() {
        for (Fatura fatura : this.faturas) {
            fatura.print();
        }
    }

    /* Getters */
    public String getNome() {
        return this.nome;
    }

    /* Setters */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    /* To String */
    public String toString() {
        return String.format("%s, %s, %s", nome, contribuinte, localizacao);
    }

    /* Enums*/
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }
}