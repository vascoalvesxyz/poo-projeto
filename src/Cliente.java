import java.util.Scanner;

public class Cliente {

    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }

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

    public static Cliente initCliente() {

        Scanner scanner = new Scanner(System.in);

        /* Pedir Nome do Cliente */
        System.out.println("Insira o nome do cliente:");
        String nome = scanner.nextLine();

        /* Pedir Contribuinte */
        System.out.println("Insira o número de contribuinte:");
        int contribuinte = scanner.nextInt();

        /* Pedir Localização */
        Cliente.Localizacao localizacao = null;
        while (localizacao == null) {
            System.out.println("Selecione a localização do cliente:");
            System.out.println("1 - Portugal");
            System.out.println("2 - Madeira");
            System.out.println("3 - Açores");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    localizacao = Cliente.Localizacao.PORTUGAL;
                    break;
                case 2:
                    localizacao = Cliente.Localizacao.MADEIRA;
                    break;
                case 3:
                    localizacao = Cliente.Localizacao.ACORES;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        }

        // Lista de faturas vazia
        Fatura[] faturas = new Fatura[0];

        // Criar o cliente
        Cliente cliente = new Cliente(nome, contribuinte, localizacao, faturas);

        System.out.print("Cliente criado com sucesso: ");
        System.out.println(cliente);

        return cliente;
    }

    // toString
    public String toString() {
        String[] localizacao = { "Portugal", "Madeira", "Açores", "Desconhecido" };
        return String.format("%s, %d, %s", nome, contribuinte, localizacao[this.localizacao.ordinal()]);
    }
}