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

    public static void main(String[] args) {
        POOFS poofs = new POOFS();
        poofs.menu();
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

    private void menu() {
        Menu menu = new Menu(scanner);

        int inputInt = 0;

        do {
            System.out.println("""
                        1 - Criar/editar cliente
                        2 - Listar Clientes
                        3 - Criar Fatura/editar fatura
                        4 - Listar faturas
                        5 - Visualizar fatura
                        6 - Importar Fatura
                        7 - Exportar faturas
                        8 - Estatistica
                        0 - Sair
                    """);

            inputInt = menu.lerInt("Opção: ");

            Cliente cliente;
            switch (inputInt) {
                /* Criar / Editar Cliente */
                case 1: {
                    String nome = menu.lerString("Insira o nome do cliente: ");
                    /* Verificar se já existe */
                    cliente = buscarPorNome(nome);
                    if (cliente != null) {
                        if (menu.lerBoolean("Cliente já existe! Deseja editar?")) {
                            menu.editarCliente(cliente);
                        }
                        break; /* Para a execução. */
                    } else {
                        Cliente novoCliente = menu.criarCliente(nome);
                        clientes.add(novoCliente);
                    }
                    break;
                }
                /* Listar Clientes */
                case 2: {
                    print();
                    break;
                }
                /* Adicionar Faturas */
                case 3: {
                    cliente = buscarPorNome(menu.lerString("Nome do cliente: "));
                    if (cliente != null) {
                        System.out.println("Cliente encontrado, insere os dados da fatura:");
                        Fatura novaFatura = menu.lerFatura(cliente);
                        cliente.addFatura(novaFatura);
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                    break;
                }
                /* Listar faturas */
                case 4: {

                }
                /* Visualizar Faturas */
                case 5: {
                    cliente = buscarPorNome(menu.lerString("Nome do cliente: "));
                    if (cliente != null) {
                        cliente.printFaturas();
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                    break;
                }
                /* Importar Faturas */
                case 6: {

                }
                /* Exportar Faturas */
                case 7: {

                }
                /* Estatísticas */
                case 8: {

                }
            }
        }
        while (inputInt != 0);

        scanner.close();
    }

}
