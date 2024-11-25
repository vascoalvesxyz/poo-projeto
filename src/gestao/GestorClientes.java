package gestao;

import produto.Cliente;
import produto.Cliente.Localizacao;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorClientes extends Leitor implements Gestor<Cliente> {

    private final ArrayList<Cliente> clientes;

    public GestorClientes() {
        this.clientes = new ArrayList<>();
    }

    public Cliente procurarPorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome))
                return cliente;
        }
        return null;
    }

    @Override
    public void criarOuEditar() {
        String nome = lerString("Insira o nome do cliente: ");

        Cliente cliente = procurarPorNome(nome);
        if (cliente == null) {
            criar(nome);
        } else if (lerBoolean("Cliente existe, deseja editar?")) {
            editar(cliente);
        }
    }

    public void criar(String nome) {
        String contribuinte = lerString("Insira o número de contribuinte: ");
        System.out.println("Escolha a localização:");
        System.out.println("""
                    1. Continente
                    2. Madeira
                    3. Açores
                """);
        int escolhaLocalizacao = lerIntMinMax("Min", 1, 3);
        Localizacao localizacao = Localizacao.values()[escolhaLocalizacao - 1];
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao);
        adicionar(novoCliente);
    }

    public void editar(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("""
                    1 - Editar nome.
                    2 - Editar NIF.
                    3 - Editar Localização.
                    0 - Sair.
                    """);
            input = scanner.nextInt();
            switch (input) {
                case 1 -> {
                    String novoNome = lerString("Novo nome: ");
                    cliente.setNome(novoNome);
                }
                case 2 -> {
                    // FALTA VALIDACAO
                    String novoContribuinte = lerString("Novo contribuinte: ");
                    cliente.setContribuinte(novoContribuinte);
                }
                case 3 -> {
                    System.out.print("Nova Localização: ");
                    int idx = lerEnum(Cliente.Localizacao.values());
                    Cliente.Localizacao novaLocalizacao = Cliente.Localizacao.values()[idx];
                    cliente.setLocalizacao(novaLocalizacao);
                }
                case 4 -> System.out.print("Nova Fatura");
            }
        }
        while (input != 0);
    }

    @Override
    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
    }

    @Override
    public void listar() {
        System.out.println("Lista de Clientes:");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    public ArrayList<Cliente> getTodosClientes() {
        return clientes;
    }

    public void criarOuEditarFatura() {
        String nome = lerString("Insira o nome do cliente: ");
        Cliente cliente = procurarPorNome(nome);
        if (cliente == null) {
            System.out.println("Esse cliente não existe. Tente outro.");
        } else {
            cliente.getFaturas().criarOuEditar();
        }
    }

    public void consultarFatura() {
        String nome = lerString("Insira o nome do cliente: ");
        Cliente cliente = procurarPorNome(nome);
        if (cliente != null) {
            System.out.println("Lista de Faturas:");
            cliente.getFaturas().listar();
        }
    }
}
