import gestao.GestorClientes;
import gestao.Leitor;

public class POOFS {
    private final GestorClientes gestorClientes;
    private final Leitor leitor;

    public POOFS() {
        leitor = new Leitor();
        gestorClientes = new GestorClientes(leitor);
    }

    public static void main(String[] args) {
        new POOFS().menu();
    }

    private void menu() {
        int escolha;
        do {
            apresentarMenu();
            escolha = leitor.lerInt("Selecione uma opção: ");
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
            case 4 -> gestorClientes.listarTodasFaturas();
            case 5 -> gestorClientes.consultarFatura();
            case 6 -> System.out.println("Funcionalidade de importar faturas ainda não implementada.");
            case 7 -> System.out.println("Funcionalidade de exportar faturas ainda não implementada.");
            case 8 -> System.out.println("Funcionalidade de estatísticas ainda não implementada.");
            case 0 -> System.out.println("A terminar a aplicação...");
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

}
