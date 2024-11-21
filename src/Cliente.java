import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Cliente {

    /* Enums*/
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }

    /* Variaveis */
    private String nome;
    private String contribuinte;
    private Localizacao localizacao;
    private ArrayList<Fatura> faturas;

    // Constructor
    public Cliente(
            String nome,
            String contribuinte,
            Localizacao localizacao,
            ArrayList<Fatura> faturas
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.faturas = faturas;
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

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    /* Setters */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /* To String */
    public String toString() {
        return String.format("%s, %s, %s", nome, contribuinte, localizacao);
    }
}