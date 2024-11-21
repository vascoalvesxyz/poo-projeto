import java.util.ArrayList;
import java.util.Scanner;

public class POOFS {

    /* Gestão de Clientes */
    private final ArrayList<Cliente> clientes = new ArrayList<>();

    private Cliente buscarPorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome))
                return c;
        }
        return null;
    }

    public void pedirClienteNovo(Scanner scanner) {
        String nome = Menu.lerString(scanner, "Insira o nome do cliente: ");
        /* Verificar se já existe */
        Cliente procurarIgual;
        if( (procurarIgual = buscarPorNome(nome)) != null) {
            if (Menu.lerBoolean(scanner, "Cliente já existe! Deseja editar?")) {
                procurarIgual.edit();
            }
            return; /* Para a execução. */
        }

        String contribuinte = Menu.lerString(scanner, "Insira o número de contribuinte: ");

        int idx = Menu.lerEnum(scanner, Cliente.Localizacao.values());
        Cliente.Localizacao localizacao = Cliente.Localizacao.values()[idx];

        ArrayList<Fatura> faturas = new ArrayList<>(); // Lista de faturas vazia

        // Criar o cliente
        Cliente clienteNovo = new Cliente(nome, contribuinte, localizacao, faturas);
        clientes.add(clienteNovo);
    }

    public void print() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente.toString());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        POOFS empresa = new POOFS();

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
0 - Sair.""");

            inputInt = Menu.lerInt(scanner, "Opção: ");

            Cliente cliente;
            switch (inputInt) {
                case 0:
                    return;

                /* Criar / Editar Cliente */
                case 1:
                    empresa.pedirClienteNovo(scanner);
                    break;
                /* Listar Clientes */
                case 2:
                    empresa.print();
                    break;
                /* Adicionar Faturas */
                case 3:
                    cliente = empresa.buscarPorNome(Menu.lerString(scanner, "Nome do cliente: "));
                    if ( cliente != null ) {
                        System.out.println("Cliente encontrado, insere os dados da fatura:");
                        Fatura novaFatura = Menu.lerFatura(scanner, cliente);
                        cliente.addFatura(novaFatura);
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                    break;
                /* Visualizar Faturas */
                case 4:
                    cliente = empresa.buscarPorNome(Menu.lerString(scanner, "Nome do cliente: "));
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
