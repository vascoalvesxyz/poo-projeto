package produto;

import gestao.GestorFaturas;

import java.io.Serial;
import java.io.Serializable;

public class Cliente implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    /* Variaveis */
    private String nome;
    private String contribuinte;
    private Localizacao localizacao;
    private final GestorFaturas faturas;

    public Cliente() {
        this.nome = "";
        this.contribuinte = "";
        this.localizacao = Localizacao.PORTUGAL;
        faturas = new GestorFaturas();
    }

    // Constructor
    public Cliente(
            String nome,
            String contribuinte,
            Localizacao localizacao
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        faturas = new GestorFaturas(this);
    }

    /* Getters */
    public String getNome() {
        return this.nome;
    }

    public GestorFaturas getFaturas() {
        return faturas;
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
        return String.format("Nome: %s, Contribuinte: %s, Localização: %s", nome, contribuinte, localizacao);
    }

    public String toFile() {
        return String.format("CLIENTE;%d;%s;%s;%s\n", getFaturas().getArraySize(), nome, contribuinte, localizacao);
    }

    /* Enums*/
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }
}