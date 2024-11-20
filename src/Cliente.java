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

    public static Localizacao pedirLocalizacao() {
        Scanner scanner = new Scanner(System.in);
        Localizacao localizacao = null;
        while (localizacao == null) {
            System.out.println("""
            Selecione a localização do cliente:
            1 - Portugal
            2 - Madeira
            3 - Açores
            """);

            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    localizacao = Localizacao.PORTUGAL;
                    break;
                case 2:
                    localizacao = Localizacao.MADEIRA;
                    break;
                case 3:
                    localizacao = Localizacao.ACORES;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        return localizacao;
    }

    public void edit() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
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
                    System.out.print("Novo nome: ");
                    this.nome = scanner.next();
                    break;
                case 2:
                    System.out.print("Novo NIF: ");
                    this.contribuinte = scanner.next();
                    break;
                case 3:
                    System.out.print("Nova Localização: ");
                    this.localizacao = pedirLocalizacao();
                    break;
                case 4:
                    System.out.print("Nova Fatura");
            }
        }
        while (input != 0);
    }

    /* Gestão de Faturas */
    private void addFatura(Fatura fatura) {
        faturas.add(fatura);
    }
    private void delFatura(Fatura fatura) {
        faturas.remove(fatura);
    }

    public void novaFatura() {
        Scanner sc = new Scanner(System.in);

        /* Pedir ID */
        System.out.print("Insere o ID: ");
        int id = sc.nextInt();

        /* Pedir Data */
        Calendar cal = pedirData();

        /* Pedir Produtos */
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        Fatura fatura = new Fatura(id, cal, produtos);
        addFatura(fatura);
    }

    private Calendar pedirData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insere a Data (dd/mm/YY)): ");
        String[] dados = scanner.next().split("/");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dados[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dados[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dados[2]));

        scanner.close();
        return cal;
    }

    /*public void printFaturas() { } */

    /* Getters */
    public String getNome() {
        return this.nome;
    }

    /* To String */
    public String toString() {
        return String.format("%s, %d, %s", nome, contribuinte, localizacao);
    }
}