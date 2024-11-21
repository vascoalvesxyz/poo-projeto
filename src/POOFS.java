import java.util.ArrayList;
import java.util.Scanner;

public class POOFS {

    /* Gestão de Clientes */
    private final ArrayList<Cliente> clientes;
    private final Scanner scanner;

    public POOFS() {
        clientes = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    private Cliente buscarPorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome))
                return c;
        }
        return null;
    }

    public void print() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente.toString());
        }
    }

    public static void main(String[] args) {
        POOFS poofs = new POOFS();
        poofs.menu();
    }

    private void menu() {
        Menu menu = new Menu(scanner);

        int inputInt = 0;

        do {
            System.out.println("""
                1 - Criar/editar cliente
                2 - Listar Clientes
                3 - Criar Fatura
                4 - Visualizar fatura
                5 - Importar Fatura
                6 - Exportar faturas
                7 - Estatistica
                0 - Sair
            """);

            inputInt = menu.lerInt("Opção: ");

            Cliente cliente;
            switch (inputInt) {
                /* Criar / Editar Cliente */
                case 1:
                    String nome = menu.lerString("Insira o nome do cliente: ");
                    /* Verificar se já existe */
                    cliente = buscarPorNome(nome);
                    if(cliente != null) {
                        if (menu.lerBoolean("Cliente já existe! Deseja editar?")) {
                            menu.editarCliente(cliente);
                        }
                        break; /* Para a execução. */
                    } else {
                        Cliente novoCliente = menu.criarCliente(nome);
                        clientes.add(novoCliente);
                    }
                    break;
                /* Listar Clientes */
                case 2:
                    print();
                    break;
                /* Adicionar Faturas */
                case 3:
                    cliente = buscarPorNome(menu.lerString("Nome do cliente: "));
                    if ( cliente != null ) {
                        System.out.println("Cliente encontrado, insere os dados da fatura:");
                        Fatura novaFatura = menu.lerFatura(cliente);
                        cliente.addFatura(novaFatura);
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                    break;
                /* Visualizar Faturas */
                case 4:
                    cliente = buscarPorNome(menu.lerString("Nome do cliente: "));
                    if ( cliente != null ) {
                        cliente.printFaturas();
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                    break;
            }
        }
        while (inputInt != 0);

        scanner.close();
    }

}
