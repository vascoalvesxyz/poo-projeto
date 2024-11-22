import java.util.Scanner;

public class POOFS {
    private final GestorClientes gestorClientes;
    private final GestorFaturas gestorFaturas;
    private final Scanner scanner;

    public POOFS() {
        gestorClientes = new GestorClientes();
        gestorFaturas = new GestorFaturas();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new POOFS().executar();
    }

    private void executar() {
        int escolha;
        do {
            apresentarMenu();
            escolha = lerInteiro("Selecione uma opção: ");
            tratarEscolha(escolha);
        } while (escolha != 0);
    }

    private void apresentarMenu() {
        System.out.println("""
                    Menu Principal
                    1. Criar/Editar Cliente
                    2. Listar Clientes
                    3. Criar/Editar Fatura
                    4. Listar Faturas
                    5. Consultar Fatura
                    6. Importar Faturas
                    7. Exportar Faturas
                    8. Estatísticas
                    0. Sair
                """);
    }

    private void tratarEscolha(int escolha) {
        switch (escolha) {
            case 1 -> criarOuEditarCliente();
            case 2 -> listarClientes();
            case 3 -> criarOuEditarFatura();
            case 4 -> listarFaturas();
            case 5 -> consultarFatura();
            case 6 -> System.out.println("Funcionalidade de importar faturas ainda não implementada.");
            case 7 -> System.out.println("Funcionalidade de exportar faturas ainda não implementada.");
            case 8 -> System.out.println("Funcionalidade de estatísticas ainda não implementada.");
            case 0 -> System.out.println("A terminar a aplicação...");
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    private void criarOuEditarCliente() {
        String nome = lerString("Insira o nome do cliente: ");
        Cliente cliente = gestorClientes.procurarPorNome(nome);

        if (cliente != null) {
            System.out.println("Cliente encontrado. A editar...");
            gestorClientes.editarCliente(cliente, scanner);
        } else {
            System.out.println("Cliente não encontrado. A criar novo cliente...");
            gestorClientes.adicionarCliente(gestorClientes.criarCliente(nome, scanner));
        }
    }

    private void listarClientes() {
        System.out.println("Lista de Clientes:");
        gestorClientes.listarTodosClientes();
    }

    private void criarOuEditarFatura() {
        String nomeCliente = lerString("Insira o nome do cliente: ");
        Cliente cliente = gestorClientes.procurarPorNome(nomeCliente);

        if (cliente != null) {
            gestorFaturas.criarOuEditarFatura(cliente, scanner);
        } else {
            System.out.println("Cliente não encontrado. Por favor, crie o cliente primeiro.");
        }
    }

    private void listarFaturas() {
        System.out.println("Lista de Faturas:");
        gestorFaturas.listarTodasFaturas();
    }

    private void consultarFatura() {
        System.out.println("Funcionalidade de consulta de faturas ainda não implementada.");
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private String lerString(String mensagem) {
        System.out.print(mensagem);
        scanner.nextLine(); // Consumir nova linha pendente
        return scanner.nextLine();
    }
}
