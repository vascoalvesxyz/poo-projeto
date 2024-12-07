package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Fatura;
import produto.Produto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class GestorFaturas extends Gestor<Fatura> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private final Cliente cliente;

    public GestorFaturas() {
        super();
        this.cliente = new Cliente();
    }

    public GestorFaturas(Cliente cliente) {
        super(new ArrayList<>());
        this.cliente = cliente;
    }

    public Fatura procurarPorNumero(int codigo) throws IllegalArgumentException {
        for (Fatura f : array)
            if (f.getId() == codigo) {
                return f;
            }
        throw new IllegalArgumentException();
    }

    @Override
    public void criarOuEditar() {
        Leitor leitor = new Leitor();
        int id = leitor.lerInt("Insira o ID da fatura: ");

        try {
            Fatura fatura = procurarPorNumero(id);
            if (leitor.lerBoolean("Fatura já existe, deseja editar?")) {
                editar(fatura);
            }
        } catch (IllegalArgumentException e) {
            criar(id);
        }
    }

    @Override
    public void adicionar(Fatura fatura) {
        array.add(fatura);
    }

    public void criar(int id) {
        Leitor leitor = new Leitor();
        Calendar cal = leitor.lerData();
        Fatura fatura = new Fatura(id, cal, cliente);

        boolean adicionarProduto;
        do {
            fatura.getProdutos().criarOuEditar();
            adicionarProduto = leitor.lerBoolean("Deseja adicionar mais um produto? ");
        }
        while (adicionarProduto);
        adicionar(fatura);
    }

    public void editar(Fatura fatura) {
        Leitor leitor = new Leitor();
        int input;
        do {
            System.out.println("""
                    1 - Editar ID.
                    2 - Editar Data.
                    3 - Gerir Produtos (Adicionar/Remover/Editar).
                    0 - Sair.
                    """);
            input = leitor.lerIntMinMax("Opção: ", 0, 3);

            switch (input) {
                case 1 -> {
                    int novoId;
                    boolean terminado = false;
                    do {
                        novoId = leitor.lerInt("Novo id: ");
                        try {
                            procurarPorNumero(novoId);
                            System.out.println("Já existe uma fatura com esse ID. Por favor, insira outro.");
                        } catch (IllegalArgumentException e) {
                            fatura.setId(novoId);
                            terminado = true;
                        }
                    } while (!terminado);
                }
                case 2 -> {
                    Calendar novaData = leitor.lerData();
                    fatura.setData(novaData);
                }
                case 3 -> gerirProdutos(fatura);
            }
        } while (input != 0);
    }

    private void gerirProdutos(Fatura fatura) {
        Leitor leitor = new Leitor();
        int opcao;
        do {
            System.out.println("""
                    1 - Adicionar Produto.
                    2 - Remover Produto.
                    0 - Voltar.
                    """);
            opcao = leitor.lerIntMinMax("Escolha a ação para produtos: ", 0, 2);

            switch (opcao) {
                case 1 -> { // Adicionar Produto
                    boolean adicionarProduto;
                    do {
                        fatura.getProdutos().criarOuEditar();
                        adicionarProduto = leitor.lerBoolean("Deseja adicionar mais um produto? ");
                    } while (adicionarProduto);
                }
                case 2 -> { // Remover Produto
                    int idProduto;
                    boolean terminado = false;
                    do {
                        idProduto = leitor.lerInt("Id do produto: ");
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
