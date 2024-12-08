import gestao.GestorClientes;
import io.FicheiroIO;
import io.Leitor;

/**
 * Classe principal da aplicação POOFS, responsável pela interação com o utilizador
 * e pela gestão das funcionalidades relacionadas com clientes e faturas.
 */
public class POOFS {

    /**
     * Gestor responsável pela gestão dos clientes e respetivas faturas.
     */
    private final GestorClientes gestorClientes;

    /**
     * Classe utilitária para operações de leitura e escrita em ficheiros.
     */
    private final FicheiroIO ficheiroIO;

    /**
     * Construtor que inicializa os componentes principais da aplicação.
     */
    public POOFS() {
        ficheiroIO = new FicheiroIO();
        gestorClientes = new GestorClientes();
    }

    /**
     * Método principal da aplicação, responsável por iniciar a execução.
     *
     * @param args Argumentos da linha de comandos (não utilizados).
     */
    public static void main(String[] args) {
        POOFS poofs = new POOFS();
        poofs.carregarDadosIniciais();
        poofs.menuPrincipal();
    }

    /**
     * Apresenta o menu principal da aplicação e permite ao utilizador
     * navegar pelas diferentes opções.
     */
    private void menuPrincipal() {
        int escolha;
        do {
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
            escolha = Leitor.lerIntMinMax("Selecione uma opção", 0, 8);
            tratarEscolha(escolha);
        } while (escolha != 0);
    }

    /**
     * Trata a escolha efetuada pelo utilizador no menu principal,
     * chamando os métodos correspondentes.
     *
     * @param escolha A opção selecionada pelo utilizador.
     */
    private void tratarEscolha(int escolha) {
        switch (escolha) {
            case 1 -> gestorClientes.criarOuEditar();
            case 2 -> gestorClientes.listar();
            case 3 -> gestorClientes.criarOuEditarFaturas();
            case 4 -> gestorClientes.listarTodasFaturas();
            case 5 -> gestorClientes.consultarFatura();
            case 6 -> importarDados();
            case 7 -> exportarDadosTexto();
            case 8 -> gestorClientes.printEstatisticas();
            case 0 -> salvarSair();
            default -> System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    /**
     * Carrega os dados iniciais da aplicação, importando-os de ficheiros
     * previamente existentes, caso estejam disponíveis.
     */
    private void carregarDadosIniciais() {
        if (ficheiroIO.existeFicheiro("dados.ser")) {
            System.out.println("A importar clientes da última sessão.");
            ficheiroIO.importarClientes(gestorClientes, "dados.ser");
        } else if (ficheiroIO.existeFicheiro("dados-iniciais.txt")) {
            System.out.println("A importar clientes do ficheiro de dados iniciais.");
            ficheiroIO.importarClientes(gestorClientes, "dados-iniciais.txt");
        }
    }

    /**
     * Permite ao utilizador importar dados de clientes a partir de um ficheiro.
     * O caminho do ficheiro é solicitado ao utilizador.
     */
    private void importarDados() {
        String caminho = Leitor.lerString("Nome do ficheiro: ");
        ficheiroIO.importarClientes(gestorClientes, caminho);
    }

    /**
     * Permite ao utilizador exportar os dados dos clientes para um ficheiro de texto.
     * O caminho do ficheiro é solicitado ao utilizador.
     */
    private void exportarDadosTexto() {
        String caminho = Leitor.lerString("Nome do ficheiro: ");
        ficheiroIO.exportarClientesTexto(gestorClientes, caminho);
    }

    /**
     * Salva os dados dos clientes num ficheiro de objetos e encerra a aplicação.
     */
    private void salvarSair() {
        ficheiroIO.exportarClientesObj(gestorClientes, "dados.ser");
        System.out.println("A terminar a aplicação...");
    }
}
