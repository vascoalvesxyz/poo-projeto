package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Cliente.Localizacao;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorClientes extends Gestor<Cliente> {

    public GestorClientes(Leitor leitor) {
        super(new ArrayList<Cliente>(), leitor);
    }

    public Cliente procurarPorNome(String nome) {
        for (Cliente cliente : array) {
            if (cliente.getNome().equalsIgnoreCase(nome))
                return cliente;
        }
        return null;
    }

    @Override
    public void criarOuEditar() {
        String nome = leitor.lerString("Insira o nome do cliente: ");

        Cliente cliente = procurarPorNome(nome);
        if (cliente == null) {
            criar(nome);
        } else if (leitor.lerBoolean("Cliente existe, deseja editar?")) {
            editar(cliente);
        }
    }

    public void criar(String nome) {
        String contribuinte = leitor.lerString("Insira o número de contribuinte: ");
        int escolhaLocalizacao = leitor.lerEnum(Localizacao.values());
        Localizacao localizacao = Localizacao.values()[escolhaLocalizacao];
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao, leitor);
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
                    String novoNome = leitor.lerString("Novo nome: ");
                    cliente.setNome(novoNome);
                }
                case 2 -> {
                    // FALTA VALIDACAO
                    String novoContribuinte = leitor.lerString("Novo contribuinte: ");
                    cliente.setContribuinte(novoContribuinte);
                }
                case 3 -> {
                    System.out.print("Nova Localização: ");
                    int idx = leitor.lerEnum(Cliente.Localizacao.values());
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
        array.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
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
        String nome = leitor.lerString("Insira o nome do cliente: ");
        Cliente cliente = procurarPorNome(nome);
        if (cliente == null) {
            System.out.println("Esse cliente não existe. Tente outro.");
        } else {
            cliente.getFaturas().criarOuEditar();
        }
    }

    public void consultarFatura() {
        String nome = leitor.lerString("Insira o nome do cliente: ");
        Cliente cliente = procurarPorNome(nome);
        if (cliente != null) {
            System.out.println("Lista de Faturas:");
            cliente.getFaturas().listar();
        }
    }

    public void listarTodasFaturas() {
        System.out.println("Lista de Faturas:");
        for (Cliente c : getTodosClientes())
            c.getFaturas().listar();
    }
}
