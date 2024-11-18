import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int inputInt = 0;

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        do {
            System.out.println("1 - Criar cliente\n2 - Listar Clientes\n3 - Criar Fatura\n4 - Visualizar fatura\n5 - Importar Fatura\n6 - Exportar faturas\n7 - Extatistica\n 0 - Sair.");
            inputInt = sc.nextInt();

            switch (inputInt) {
                case 0:
                    return;
                /* Criar Cliente */
                case 1:
                    Cliente clienteNovo = Cliente.initCliente();
                    clientes.add(clienteNovo);
                    break;
                case 2:
                    for (Cliente cliente : clientes) {
                        System.out.println(cliente.toString());
                    }
            }
        }
        while (inputInt != 0);

    }

}
