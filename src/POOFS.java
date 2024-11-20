import java.util.ArrayList;
import java.util.Scanner;

public class POOFS {

    /* Gestão de Clientes */
    private ArrayList<Cliente> clientes = new ArrayList<>();

    private Cliente buscarPorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome))
                return c;
        }
        return null;
    }

    public void criarClienteNovo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o nome do cliente:");
        String nome = scanner.nextLine();

        /* Verificar se já existe */
        Cliente procurarIgual;
        if( (procurarIgual = buscarPorNome(nome)) != null) {
            System.out.print("Cliente já existe! Deseja editar? (s/N): ");
            if (scanner.nextLine().equals("s")) {
                procurarIgual.edit();
            }

            /* else {
                criarClienteNovo();
            }*/

            return;
        };

        System.out.println("Insira o número de contribuinte:");
        String contribuinte = scanner.next();

        Cliente.Localizacao localizacao = Cliente.pedirLocalizacao();
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
        Scanner sc = new Scanner(System.in);
        POOFS clientesEmpresa = new POOFS();

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
            inputInt = sc.nextInt();

            switch (inputInt) {
                case 0:
                    return;
                /* Criar / Editar Cliente */
                case 1:
                    clientesEmpresa.criarClienteNovo();
                    break;
                /* Listar Clientes */
                case 2:
                    clientesEmpresa.print();
                    break;
                /* Adicionar Faturas */
                case 3:
                    System.out.print("Nome do cliente: ");
                    Cliente c;
                    if ( null != (c = clientesEmpresa.buscarPorNome(sc.next())) ) {
                        c.novaFatura();
                    } else {
                        System.out.println("Cliente não existe!");
                    }
                break;
            }
        }
        while (inputInt != 0);

    }

}
