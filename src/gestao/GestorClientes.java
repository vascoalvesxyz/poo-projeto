package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Cliente.Localizacao;
import produto.Fatura;
import produto.Produto;

import java.util.ArrayList;

/**
 * Classe responsável pela gestão de clientes, incluindo a criação, edição,
 * listagem e manipulação de faturas associadas a clientes.
 */
public class GestorClientes extends Gestor<Cliente> {

    /**
     * Construtor que inicializa o gestor de clientes com uma lista vazia.
     */
    public GestorClientes() {
        super(new ArrayList<>());
    }

    /**
     * Procura um cliente pelo nome.
     *
     * @param nome Nome do cliente a procurar.
     * @return O cliente encontrado.
     * @throws IllegalArgumentException Se não existir cliente com o nome fornecido.
     */
    public Cliente procurarPorNome(String nome) throws IllegalArgumentException {
        for (Cliente cliente : array) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        throw new IllegalArgumentException("Não existe cliente com esse nome!");
    }

    /**
     * Permite criar ou editar clientes, de acordo com a escolha do utilizador.
     */
    @Override
    public void criarOuEditar() {
        System.out.println("""
                1 - Criar
                2 - Editar
                0 - Sair
                """);
        int i = Leitor.lerIntMinMax("Opção", 0, 2);
        switch (i) {
            case 1 -> {
                String nome = Leitor.lerNome("Insira o nome do cliente: ");
                try {
                    Cliente cliente = procurarPorNome(nome);
                    if (Leitor.lerBoolean("Cliente existe, deseja editar?")) {
                        editar(cliente);
                    }
                } catch (IllegalArgumentException e) {
                    criar(nome);
                }
            }
            case 2 -> {
                try {
                    Cliente cliente = selecionar();
                    editar(cliente);
                } catch (IllegalArgumentException e) {
                    System.out.println("Não há clientes.");
                }
            }
            case 0 -> {
            }
        }
    }

    /**
     * Cria um novo cliente com base no nome fornecido e nas informações adicionais solicitadas.
     *
     * @param nome Nome do cliente.
     */
    private void criar(String nome) {
        String contribuinte = Leitor.lerContribuinte("Contribuinte novo:");
        int escolhaLocalizacao = Leitor.lerEnum(Localizacao.values());
        Localizacao localizacao = Localizacao.values()[escolhaLocalizacao];
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao);
        adicionar(novoCliente);
        System.out.println("O cliente foi criado.");
    }

    /**
     * Edita as informações de um cliente existente.
     *
     * @param item Cliente a ser editado.
     */
    @Override
    protected void editar(Cliente item) {
        int input;
        do {
            System.out.println("""
                    1 - Editar nome.
                    2 - Editar NIF.
                    3 - Editar Localização.
                    0 - Sair.
                    """);
            input = Leitor.lerIntMinMax("Opção: ", 0, 3);

            switch (input) {
                case 1 -> {
                    String novoNome = Leitor.lerNome("Novo nome: ");
                    item.setNome(novoNome);
                }
                case 2 -> {
                    String novoContribuinte = Leitor.lerContribuinte("Novo contribuinte: ");
                    item.setContribuinte(novoContribuinte);
                }
                case 3 -> {
                    System.out.print("Nova Localização: ");
                    int idx = Leitor.lerEnum(Localizacao.values());
                    Localizacao novaLocalizacao = Localizacao.values()[idx];
                    item.setLocalizacao(novaLocalizacao);
                }
            }
        }
        while (input != 0);
    }

    /**
     * Lista todos os clientes registados.
     * Caso não existam clientes, é apresentada uma mensagem indicando que a lista está vazia.
     */
    @Override
    public void listar() {
        System.out.println("Lista de Clientes:");
        if (array.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
        } else {
            array.forEach(System.out::println);
        }
    }

    /**
     * Permite criar ou editar faturas associadas aos clientes.
     */
    public void criarOuEditarFaturas() {
        System.out.println("""
                1 - Criar
                2 - Editar
                0 - Sair
                """);
        int i = Leitor.lerIntMinMax("Opção", 0, 2);
        switch (i) {
            case 1 -> selecionar().getFaturas().criarOuEditar();
            case 2 -> {
                try {
                    GestorFaturas gf = selecionar().getFaturas();
                    try {
                        gf.editar(gf.selecionar());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Não há faturas para este cliente.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Não há clientes.");
                }
            }
            case 0 -> {
            }
        }
    }

    /**
     * Consulta e lista as faturas de um cliente selecionado.
     */
    public void consultarFatura() {
        try {
            Cliente cliente = selecionar();
            cliente.getFaturas().listar();
        } catch (IllegalArgumentException e) {
            System.out.println("Não existe clientes.");
        }
    }

    /**
     * Lista todas as faturas de todos os clientes.
     */
    public void listarTodasFaturas() {
        System.out.println("Lista de Faturas:");
        for (Cliente c : getArray())
            c.getFaturas().listar();
    }

    /**
     * Imprime as estatísticas da aplicação, incluindo o número de clientes,
     * faturas, produtos e valores totais com e sem IVA.
     */
    public void printEstatisticas() {
        int nrClientes = 0, nrFaturas = 0, nrProdutos = 0;
        double valorTotalSemIva = 0, valorTotalComIva = 0;
        for (Cliente cliente : array) {
            nrClientes += 1;
            for (Fatura fatura : cliente.getFaturas().getArray()) {
                nrFaturas += 1;
                for (Produto produto : fatura.getProdutos().getArray()) {
                    nrProdutos += 1;
                    double valorProdutoSemIva = produto.getValorUnitario() * produto.getQuantidade();
                    valorTotalSemIva += valorProdutoSemIva;
                    valorTotalComIva += valorProdutoSemIva * (1 + produto.calcIva(cliente.getLocalizacao()) / 100);
                }
            }
        }
        double valorTotalIva = valorTotalComIva - valorTotalSemIva;
        System.out.printf("""
                        Estatísticas do programa:
                            Número de clientes: %d
                            Número de faturas: %d
                            Número de produtos: %d
                            Valor total sem IVA: %.2f€
                            Valor total do IVA: %.2f€
                            Valor total com IVA: %.2f€
                        """,
                nrClientes, nrFaturas, nrProdutos, valorTotalSemIva, valorTotalIva, valorTotalComIva
        );
    }
}
