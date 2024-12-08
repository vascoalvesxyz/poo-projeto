package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Fatura;
import produto.Produto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe responsável pela gestão de faturas associadas a um cliente.
 * Permite criar, editar, listar e remover faturas, bem como gerir produtos dentro das faturas.
 */
public class GestorFaturas extends Gestor<Fatura> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * Cliente associado a este gestor de faturas.
     */
    private final Cliente cliente;

    /**
     * Construtor padrão que inicializa o gestor com um cliente vazio.
     */
    public GestorFaturas() {
        super();
        this.cliente = new Cliente();
    }

    /**
     * Construtor que inicializa o gestor com um cliente específico.
     *
     * @param cliente Cliente a ser associado ao gestor.
     */
    public GestorFaturas(Cliente cliente) {
        super(new ArrayList<>());
        this.cliente = cliente;
    }

    /**
     * Procura uma fatura pelo número de ID.
     *
     * @param codigo ID da fatura a procurar.
     * @return A fatura encontrada.
     * @throws IllegalArgumentException Se não existir fatura com o ID fornecido.
     */
    public Fatura procurarPorNumero(int codigo) throws IllegalArgumentException {
        for (Fatura f : array)
            if (f.getId() == codigo) {
                return f;
            }
        throw new IllegalArgumentException();
    }

    /**
     * Permite criar ou editar uma fatura com base no ID fornecido pelo utilizador.
     */
    @Override
    public void criarOuEditar() {
        int id = Leitor.lerUInt("Insira o ID da fatura: ");

        try {
            Fatura fatura = procurarPorNumero(id);
            if (Leitor.lerBoolean("Fatura já existe, deseja editar?")) {
                editar(fatura);
            }
        } catch (IllegalArgumentException e) {
            criar(id);
        }
    }

    /**
     * Adiciona uma nova fatura ao gestor.
     *
     * @param fatura Fatura a ser adicionada.
     */
    @Override
    public void adicionar(Fatura fatura) {
        array.add(fatura);
    }

    /**
     * Cria uma nova fatura com base no ID fornecido e permite adicionar produtos à mesma.
     *
     * @param id ID da nova fatura.
     */
    public void criar(int id) {
        Calendar cal = Leitor.lerData();
        Fatura fatura = new Fatura(id, cal, cliente);

        boolean adicionarProduto;
        do {
            fatura.getProdutos().criarOuEditar();
            adicionarProduto = Leitor.lerBoolean("Deseja adicionar mais um produto? ");
        } while (adicionarProduto);
        adicionar(fatura);
    }

    /**
     * Edita os dados de uma fatura existente, como o ID, a data ou os produtos associados.
     *
     * @param item Fatura a ser editada.
     */
    @Override
    public void editar(Fatura item) {
        int input;
        do {
            System.out.println("""
                    1 - Editar ID.
                    2 - Editar Data.
                    3 - Gerir Produtos (Adicionar/Remover).
                    0 - Sair.
                    """);
            input = Leitor.lerIntMinMax("Opção: ", 0, 3);

            switch (input) {
                case 1 -> {
                    int novoId;
                    boolean terminado = false;
                    do {
                        novoId = Leitor.lerUInt("Novo id: ");
                        try {
                            procurarPorNumero(novoId);
                            System.out.println("Já existe uma fatura com esse ID. Por favor, insira outro.");
                        } catch (IllegalArgumentException e) {
                            item.setId(novoId);
                            terminado = true;
                        }
                    } while (!terminado);
                }
                case 2 -> {
                    Calendar novaData = Leitor.lerData();
                    item.setData(novaData);
                }
                case 3 -> gerirProdutos(item);
            }
        } while (input != 0);
    }

    /**
     * Permite gerir os produtos de uma fatura, incluindo adicionar ou remover produtos.
     *
     * @param fatura Fatura cujos produtos serão geridos.
     */
    private void gerirProdutos(Fatura fatura) {
        int opcao;
        do {
            System.out.println("""
                    1 - Adicionar Produto.
                    2 - Remover Produto.
                    0 - Voltar.
                    """);
            opcao = Leitor.lerIntMinMax("Escolha a ação para produtos: ", 0, 2);

            switch (opcao) {
                case 1 -> { // Adicionar Produto
                    boolean adicionarProduto;
                    do {
                        fatura.getProdutos().criarOuEditar();
                        adicionarProduto = Leitor.lerBoolean("Deseja adicionar mais um produto? ");
                    } while (adicionarProduto);
                }
                case 2 -> { // Remover Produto
                    int idProduto;
                    boolean terminado = false;
                    do {
                        idProduto = Leitor.lerUInt("Id do produto: ");
                        try {
                            Produto produto = fatura.getProdutos().procurarPorCodigo(idProduto);
                            fatura.getProdutos().remover(produto);
                            System.out.printf("Produto com id %d removido da fatura.\n", idProduto);
                            terminado = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Não existe nenhum produto com esse id.");
                        }
                    } while (!terminado);
                }
            }
        } while (opcao != 0);
    }
}
