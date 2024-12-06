package produto;

import gestao.GestorFaturas;
import io.Leitor;

import java.io.Serializable;

public class Cliente implements Serializable {
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
            Localizacao localizacao,
            Leitor leitor
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        faturas = new GestorFaturas(this, leitor);
    }

    public void printFaturas() {
        faturas.listar();
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
        return String.format("CLIENTE,%d,%s,%s,%s\n", getFaturas().size(), nome, contribuinte, localizacao);
    }

    /* Enums*/
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }
}