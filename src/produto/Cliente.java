package produto;

import gestao.GestorFaturas;
import gestao.Leitor;

public class Cliente {

    /* Variaveis */
    private String nome;
    private String contribuinte;
    private Localizacao localizacao;
    private final GestorFaturas faturas;

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
        return String.format("%s, %s, %s", nome, contribuinte, localizacao);
    }

    /* Enums*/
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }
}