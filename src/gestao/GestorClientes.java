package gestao;

import produto.Cliente;

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
    public Cliente criar() {
        String nome = lerString("Insira o nome do cliente: ");

        Cliente procurar = procurarPorNome(nome);
        if (procurar != null) {
            boolean desejaEditar = lerBoolean("Cliente existe, deseja editar?");
            if (desejaEditar) {
                editar(procurar);
            }
            return procurar;
        }

        String contribuinte = lerString("Insira o número de contribuinte: ");
        System.out.println("Escolha a localização:");
        System.out.println("1. Continente\n2. Madeira\n3. Açores");
        int escolhaLocalizacao = lerIntMinMax("Min", 1, 3);
        Cliente.Localizacao localizacao = Cliente.Localizacao.values()[escolhaLocalizacao - 1];
        Cliente novoCliente = new Cliente(nome, contribuinte, localizacao);
        adicionar(novoCliente);
        return novoCliente;
    }

    @Override
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
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente registado.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    public ArrayList<Cliente> getTodosClientes() {
        return clientes;
    }

    public Cliente pedirCliente() {
        Cliente procurar = procurarPorNome(lerString("Insira o nome do cliente: "));
        if (procurar == null) {
            System.out.println("Cliente não existe!");
            return null;
        } else {
            return procurar;
        }
    }

}
