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
    private int contribuinte;
    private Localizacao localizacao;
    private Fatura[] faturas;

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

    public static Cliente init() {

        Scanner scanner = new Scanner(System.in);

        /* Pedir Nome do Cliente */
        System.out.println("Insira o nome do cliente:");
        String nome = scanner.nextLine();

        /* Pedir Contribuinte */
        System.out.println("Insira o número de contribuinte:");
        int contribuinte = scanner.nextInt();

        /* Pedir Localizacação */
        Localizacao localizacao = pedirLocalizacao();


        // Lista de faturas vazia
        Fatura[] faturas = new Fatura[0];

        // Criar o cliente
        Cliente cliente = new Cliente(nome, contribuinte, localizacao, faturas);

        System.out.print("Cliente criado com sucesso: ");
        System.out.println(cliente);

        return cliente;
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
                    this.contribuinte = scanner.nextInt();
                    break;
                case 3:
                    System.out.print("Nova Localização: ");
                    this.localizacao = pedirLocalizacao();
                    break;
            }
        }
        while (input != 0);
    }

    public String getNome() {
        return this.nome;
    }

    /* To String */
    public String toString() {
        String[] localizacao = { "Portugal", "Madeira", "Açores", "Desconhecido" };
        return String.format("%s, %d, %s", nome, contribuinte, localizacao[this.localizacao.ordinal()]);
    }
}