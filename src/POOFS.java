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
        gestorClientes = new GestorClientes(leitor);
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
            case 7 -> System.out.println("Funcionalidade de exportar faturas ainda não implementada.");
            case 8 -> gestorClientes.printEstatisticas();
            case 0 -> System.out.println("A terminar a aplicação...");
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    private void carregarDadosIniciais() {
        ArrayList<Cliente> res = new ArrayList<>();
        if (ficheiroIO.existeFicheiro("dados.ser")) {
            res = ficheiroIO.importarClientes("dados.ser");
        } else if (ficheiroIO.existeFicheiro("dados-iniciais.txt")) {
            System.out.println("A importar clientes do ficheiro de dados iniciais.");
            res = ficheiroIO.importarClientes("dados-iniciais.txt");
        }

        if (!res.isEmpty()) {
            gestorClientes.getTodosClientes().addAll(res);
        }
    }

    private void importarDados() {
        String caminho = leitor.lerString("Nome do ficheiro: ");
        ArrayList<Cliente> arr = ficheiroIO.importarClientes(caminho);
        if (!arr.isEmpty()) {
            gestorClientes.getTodosClientes().addAll(arr);
        } else {
            System.out.println("Ficheiro Vazio.");
        }
    }
}
