import produto.Produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class GestorFaturas {
    private final ArrayList<Fatura> faturas;

    public GestorFaturas() {
        this.faturas = new ArrayList<>();
    }

    private Calendar lerData(Scanner scanner) {
        System.out.print("Insere a Data (dd/mm/YY)): ");
        String[] dados = scanner.next().split("/");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dados[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dados[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dados[2]));
        return cal;
    }

    public void criarOuEditarFatura(Cliente cliente, Scanner scanner) {
        System.out.print("Insira o número da fatura: ");
        int numero = scanner.nextInt();

        Fatura faturaExistente = procurarPorNumero(numero);
        if (faturaExistente != null) {
            System.out.println("Fatura encontrada. A editar...");
            // Adicione aqui a lógica para editar a fatura
        } else {
            System.out.println("Criar nova fatura...");
            Fatura novaFatura = criarFatura(cliente, scanner);
            faturas.add(novaFatura);
            System.out.println("Fatura criada com sucesso.");
        }
    }

    public Fatura procurarPorNumero(int codigo) {
        return faturas.stream()
                .filter(f -> f.getId() == codigo)
                .findFirst()
                .orElse(null);
    }

    public void listarTodasFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura registada.");
        } else {
            faturas.forEach(System.out::println);
        }
    }

    private Fatura criarFatura(Cliente cliente, Scanner scanner) {
        Calendar data = lerData(scanner);

        ArrayList<Produto> produtos = new ArrayList<>();
        boolean adicionarMais;
        do {
            System.out.println("Deseja adicionar um produto? (s/n)");
            String resposta = scanner.next();
            adicionarMais = resposta.equalsIgnoreCase("s");

            if (adicionarMais) {
                Produto produto = criarProduto(scanner);
                produtos.add(produto);
            }
        } while (adicionarMais);

        return new Fatura(faturas.size() + 1, data, cliente, produtos);
    }

    private Produto criarProduto(Scanner scanner) {
        System.out.print("Insira o código do produto: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha
        System.out.print("Insira o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Insira a descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Insira a quantidade de produto: ");
        int quantidade = scanner.nextInt();
        System.out.print("Insira o preço do produto: ");
        double valorUnitario = scanner.nextDouble();

        return new Produto(codigo, nome, descricao, quantidade, valorUnitario);
    }
}
