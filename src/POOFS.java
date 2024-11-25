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
        new POOFS().menu();
    }

    private void menu() {
        int escolha;
        do {
            apresentarMenu();
            escolha = lerEscolha();
            tratarEscolha(escolha);
        }
        while (escolha != 0);
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
            case 1 -> gestorClientes.criarOuEditar();
            case 2 -> gestorClientes.listar();
            case 3 -> gestorClientes.criarOuEditarFatura();
            case 4 -> listarFaturas();
            case 5 -> gestorClientes.consultarFatura();
            case 6 -> System.out.println("Funcionalidade de importar faturas ainda não implementada.");
            case 7 -> System.out.println("Funcionalidade de exportar faturas ainda não implementada.");
            case 8 -> System.out.println("Funcionalidade de estatísticas ainda não implementada.");
            case 0 -> System.out.println("A terminar a aplicação...");
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    private void listarFaturas() {
        System.out.println("Lista de Faturas:");
        for (Cliente c : gestorClientes.getTodosClientes())
            c.getFaturas().listar();
    }

    private int lerEscolha() {
        System.out.print("Selecione uma opção: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
