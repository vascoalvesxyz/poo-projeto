public class Main {

    public static Cliente criarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Insira o número de contribuinte:");
        int contribuinte = scanner.nextInt();

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

        // Simular a criação de faturas (vazio neste caso)
        Fatura[] faturas = new Fatura[0]; // Pode ser alterado para criar faturas reais

        // Criar o cliente
        Cliente cliente = new Cliente(nome, contribuinte, localizacao, faturas);

        System.out.println("Cliente criado com sucesso:");
        System.out.println(cliente);

        return cliente;
    }

    public static void main(String[] args) {
        Cliente c1 = criarCliente();
    }
}
