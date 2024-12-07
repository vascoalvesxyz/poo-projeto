package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Cliente.Localizacao;
import produto.Fatura;
import produto.Produto;

import java.util.ArrayList;

public class GestorClientes extends Gestor<Cliente> {

    public GestorClientes() {
        super(new ArrayList<>());
    }

    public Cliente procurarPorNome(String nome) throws IllegalArgumentException {
        for (Cliente cliente : array) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        throw new IllegalArgumentException("Não existe cliente com esse nome!");
    }

    @Override
    public void criarOuEditar() {
        Leitor l = new Leitor();
        String nome = l.lerNome("Insira o nome do cliente: ");
        try {
            Cliente cliente = procurarPorNome(nome);
            if (l.lerBoolean("Cliente existe, deseja editar?")) {
                editar(cliente);
            }
        } catch (IllegalArgumentException e) {
            criar(nome);
        }
    }

    public void criar(String nome) {
        Leitor leitor = new Leitor();
        String contribuinte = leitor.lerContribuinte("Contribuinte novo:");
        int escolhaLocalizacao = leitor.lerEnum(Localizacao.values());
        Localizacao localizacao = Localizacao.values()[escolhaLocalizacao];
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, leitor);
        adicionar(novoCliente);
        System.out.println("O cliente foi criado.");
    }

    public void editar(Cliente cliente) {
        Leitor leitor = new Leitor();
        int input;
        do {
            System.out.println("""
                    1 - Editar nome.
                    2 - Editar NIF.
                    3 - Editar Localização.
                    0 - Sair.
                    """);
            input = leitor.lerIntMinMax("Opção: ", 0, 3);

            switch (input) {
                case 1 -> {
                    String novoNome = leitor.lerNome("Novo nome: ");
                    cliente.setNome(novoNome);
                }
                case 2 -> {
                    String novoContribuinte = leitor.lerContribuinte("Novo contribuinte: ");
                    cliente.setContribuinte(novoContribuinte);
                }
                case 3 -> {
                    System.out.print("Nova Localização: ");
                    int idx = leitor.lerEnum(Cliente.Localizacao.values());
                    Cliente.Localizacao novaLocalizacao = Cliente.Localizacao.values()[idx];
                    cliente.setLocalizacao(novaLocalizacao);
                }
            }
        }
        while (input != 0);
    }

    @Override
    public void listar() {
        System.out.println("Lista de Clientes:");
        if (array.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
        } else {
            array.forEach(System.out::println);
        }
    }

    public ArrayList<Cliente> getTodosClientes() {
        return array;
    }

    public void criarOuEditarFatura() {
        try {
            Cliente cliente = selecionar();
            System.out.printf("Cliente selecionado: %s\n", cliente.toString());
            cliente.getFaturas().criarOuEditar();
        } catch (IllegalArgumentException e) {
            System.out.println("Não existe clientes.");
        }
    }

    public void consultarFatura() {
        try {
            Cliente cliente = selecionar();
            cliente.getFaturas().listar();
        } catch (IllegalArgumentException e) {
            System.out.println("Não existe clientes.");
        }
    }

    public void listarTodasFaturas() {
        System.out.println("Lista de Faturas:");
        for (Cliente c : getTodosClientes())
            c.getFaturas().listar();
    }

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
