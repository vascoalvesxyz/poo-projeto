import gestao.GestorClientes;
import produto.Cliente;

import java.util.Scanner;

public class POOFS {
    private final GestorClientes gestorClientes;
    private final Scanner scanner;

    public POOFS() {
        scanner = new Scanner(System.in);
        gestorClientes = new GestorClientes();
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
        gestorClientes.criar();
    }

    private void listarClientes() {
        System.out.println("Lista de Clientes:");
        gestorClientes.listar();
    }

    private void criarOuEditarFatura() {
        Cliente c = gestorClientes.pedirCliente();
        c.getFaturas().criar();
    }

    private void listarFaturas() {
        System.out.println("Lista de Faturas:");
        for (Cliente c : gestorClientes.getTodosClientes())
            c.getFaturas().listar();
    }

    private void consultarFatura() {
        Cliente c = gestorClientes.pedirCliente();
        if (c != null) {
            System.out.println("Lista de Faturas:");
            c.getFaturas().listar();
        }
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
