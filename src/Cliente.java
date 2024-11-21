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

    public void edit() {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("""
                    1 - Editar nome.
                    2 - Editar NIF.
                    3 - Editar Localização.
                    0 - Sair.
                    """);
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    this.nome = Menu.lerString(scanner, "Novo nome: ");
                    break;
                case 2:
                    this.contribuinte = Menu.lerString(scanner, "Novo contribuinte: ");
                    break;
                case 3:
                    System.out.print("Nova Localização: ");
                    int idx = Menu.lerEnum(scanner, Localizacao.values());
                    this.localizacao = Localizacao.values()[idx];
                    break;
                case 4:
                    System.out.print("Nova Fatura");
            }
        }
        while (input != 0);
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

    /* To String */
    public String toString() {
        return String.format("%s, %s, %s", nome, contribuinte, localizacao);
    }
}