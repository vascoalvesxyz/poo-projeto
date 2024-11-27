import gestao.GestorClientes;
import io.FicheiroIO;
import io.Leitor;

import java.io.File;

public class POOFS {
    private final GestorClientes gestorClientes;
    private final Leitor leitor;
    private final FicheiroIO ficheiroIO;

    public POOFS() {
        leitor = new Leitor();
        ficheiroIO = new FicheiroIO(leitor);
        gestorClientes = carregarDados();
    }

    public static void main(String[] args) {
        POOFS poofs = new POOFS();
        poofs.menu();
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

    private GestorClientes carregarDados() {
        if (ficheiroIO.existeFicheiro("dados.ser"))
            return ficheiroIO.importarClientes("dados.ser");
        else if (ficheiroIO.existeFicheiro("dados-inicias.txt"))
            return ficheiroIO.importarClientes("dados-inicias.txt");
        return new GestorClientes(leitor);
    }
}
