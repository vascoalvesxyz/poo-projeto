import java.util.ArrayList;
import java.util.Scanner;

public class GestorClientes {
    private final ArrayList<Cliente> clientes;

    public GestorClientes() {
        this.clientes = new ArrayList<>();
    }

    public Cliente procurarPorNome(String nome) {
        return clientes.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
    }

    public void editarCliente(Cliente cliente, Scanner scanner) {
        System.out.println("A editar o cliente...");

        System.out.print("Novo nome (deixe em branco para manter o atual): ");
        scanner.nextLine(); // Consumir nova linha
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) cliente.setNome(novoNome);

        System.out.print("Novo número de contribuinte (deixe em branco para manter o atual): ");
        String novoContribuinte = scanner.nextLine();
        if (!novoContribuinte.isBlank()) cliente.setContribuinte(novoContribuinte);

        System.out.println("Escolha a nova localização (1. Continente, 2. Madeira, 3. Açores): ");
        int escolhaLocalizacao = lerInteiro(scanner, 1, 3);
        cliente.setLocalizacao(Cliente.Localizacao.values()[escolhaLocalizacao - 1]);

        System.out.println("Cliente atualizado com sucesso.");
    }

    public Cliente criarCliente(String nome, Scanner scanner) {
        System.out.print("Insira o número de contribuinte: ");
        String contribuinte = scanner.next();

        System.out.println("Escolha a localização:");
        System.out.println("1. Continente\n2. Madeira\n3. Açores");
        int escolhaLocalizacao = lerInteiro(scanner, 1, 3);
        Cliente.Localizacao localizacao = Cliente.Localizacao.values()[escolhaLocalizacao - 1];

        return new Cliente(nome, contribuinte, localizacao);
    }

    public void listarTodosClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private int lerInteiro(Scanner scanner, int min, int max) {
        int valor;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, insira um número válido.");
                scanner.next();
            }
            valor = scanner.nextInt();
            if (valor < min || valor > max) {
                System.out.println("Número fora do intervalo permitido. Tente novamente.");
            }
        } while (valor < min || valor > max);
        return valor;
    }
}
