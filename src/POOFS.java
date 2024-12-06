import gestao.GestorClientes;
import io.FicheiroIO;
import io.Leitor;
import produto.Cliente;

import java.util.ArrayList;

public class POOFS {
    private final GestorClientes gestorClientes;
    private final Leitor leitor;
    private final FicheiroIO ficheiroIO;

    public POOFS() {
        leitor = new Leitor();
        ficheiroIO = new FicheiroIO(leitor);
        gestorClientes = new GestorClientes();
    }

    public static void main(String[] args) {
        POOFS poofs = new POOFS();
        poofs.carregarDadosIniciais();
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
            case 6 -> importarDados();
            case 7 -> exportarDadosTexto();
            case 8 -> gestorClientes.printEstatisticas();
            case 0 -> salvarSair();
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    private void carregarDadosIniciais() {
        if (ficheiroIO.existeFicheiro("dados.ser")) {
            System.out.println("A importar clientes da ultima sessão.");
            ficheiroIO.importarClientes(gestorClientes, "dados.ser");
        } else if (ficheiroIO.existeFicheiro("dados-iniciais.txt")) {
            System.out.println("A importar clientes do ficheiro de dados iniciais.");
            ficheiroIO.importarClientes(gestorClientes, "dados-iniciais.txt");
        }
    }

    private void importarDados() {
        String caminho = leitor.lerString("Nome do ficheiro: ");
        ficheiroIO.importarClientes(gestorClientes, caminho);
    }

    private void exportarDadosTexto() {
        String caminho = leitor.lerString("Nome do ficheiro: ");
        ficheiroIO.exportarClientesTexto(gestorClientes, caminho);
    }

    private void salvarSair() {
        ficheiroIO.exportarClientesObj(gestorClientes, "dados.ser");
        System.out.println("A terminar a aplicação...");
    }
}
