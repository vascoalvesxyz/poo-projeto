import Produto.Produto;
import Produto.ProdutoFarmaciaPrescrito;
import Produto.ProdutoFarmaciaSemReceita;
import Produto.ProdutoAlimentar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public int lerInt(String mensagem) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            // REGEX: '-' 0 ou 1 vezes seguido de todos os digitos 1 ou mais vezes
            if (entrada.matches("-?[0-9]+")) {
                numero = Integer.parseInt(entrada);
                valido = true;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
        return numero;
    }

    public int lerIntMinMax(String mensagem, int min, int max) {
        int res;
        mensagem = mensagem + String.format(" (%d-%d): ", min, max);
        do res = lerInt(mensagem);
        while (res < min || res > max);
        return res;
    }

    public boolean lerBoolean(String questao) {
        System.out.println(questao + "(s/n)");

        String inputStr;
        do inputStr = scanner.nextLine();
        while (!inputStr.equalsIgnoreCase("s") && !inputStr.equalsIgnoreCase("n"));

        return inputStr.equalsIgnoreCase("s");
    }

    public Calendar lerData() {
        System.out.print("Insere a Data (dd/mm/YY)): ");
        String[] dados = scanner.next().split("/");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dados[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dados[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dados[2]));
        return cal;
    }

    public void printEnum(Object[] valores) {
        System.out.println("Escolha uma das opções:");
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%d - %s%n", i + 1, valores[i].toString());
        }
    }

    public int lerEnum(Object[] valoresEnum) {
        printEnum(valoresEnum);
        int input = lerIntMinMax("Opção", 1, valoresEnum.length);
        return input - 1; // devolve o indice
    }

    public Produto lerProduto() {
        String resposta;
        do resposta = lerString("O produto é alimentar ou farmaceutico? (a/f) ");
        while (!resposta.equalsIgnoreCase("a") && !resposta.equalsIgnoreCase("f"));
        boolean isAlimentar = resposta.equalsIgnoreCase("a");

        Produto novoProduto;
        if (isAlimentar) {
            novoProduto = pedirProdutoAlimentar();
        } else novoProduto = pedirProdutoFarmaceutico();
        return novoProduto;
    }

    private Produto pedirProdutoFarmaceutico() {
        int codigo = lerInt("Codigo: ");
        String nome = lerString("Nome: ");
        String descricao = lerString("Descrição: ");
        int quantidade = lerInt("Quantidade : ");
        int valor = lerInt("Valor Unitário: ");
        boolean temPrescricao = lerBoolean("O produto tem prescrição? ");

        Produto produto = null;
        if (temPrescricao) {
            String medico = lerString("Nome do médico que fez a receita: ");
            produto = new ProdutoFarmaciaPrescrito(codigo, nome, descricao, quantidade, valor, medico);
        } else {
            /* Produto.Produto Farmaceutico Sem Prescrição */
            ProdutoFarmaciaSemReceita.Categoria categoria = null;
            System.out.print("1 - BELEZA,\n2 - BEM_ESTAR,\n3 - BEBES,\n 4 - ANIMAIS,\n 5 - OUTRO\n");
            while (categoria == null) {
                System.out.print("Escolha uma categoria (1-5): ");
                int escolha = scanner.nextInt();
                if (escolha >= 1 && escolha <= 5) {
                    categoria = ProdutoFarmaciaSemReceita.Categoria.values()[escolha - 1];
                }
            }
            produto = new ProdutoFarmaciaSemReceita(codigo, nome, descricao, quantidade, valor, categoria);
        }
        return produto;
    }

    private Produto pedirProdutoAlimentar() {
        int codigo = lerInt("Codigo: ");
        String nome = lerString("Nome: ");
        String descricao = lerString("Descrição: ");
        int quantidade = lerInt("Quantidade : ");
        int valor = lerInt("Valor Unitário: ");

        Produto produto = null;
        /* Produto.Produto Alimentar */
        boolean biologico = lerBoolean("O produto é biologico? ");

        ProdutoAlimentar.Taxa taxa;
        System.out.println("""
                1 - Taxa Reduzida
                2 - Taxa Intermédia
                3 - Taxa Normal
                """);

        int inputInt;
        do inputInt = scanner.nextInt();
        while (inputInt < 1 || inputInt > 3); // ~[0...3]

        switch (inputInt) {
            case 1:
                /* Taxa Reduzida (Necessita Certificações) */
                ProdutoAlimentar.Certificacao[] certificacoes = lerCertificacoes();
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico, certificacoes);
                break;
            case 2:
                /* Taxa Intermedia (Necessita Categoria) */
                ProdutoAlimentar.Categoria categoria = null;
                System.out.print("""
                        1 - Congelados,
                        2 - Enlatados,
                        3 - Vinhos
                        """);
                while (categoria == null) {
                    System.out.print("escolha uma categoria (1-3): ");
                    int escolha = scanner.nextInt();
                    if (escolha >= 1 && escolha <= 3) {
                        categoria = ProdutoAlimentar.Categoria.values()[escolha - 1];
                    }
                }
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico, categoria);
                break;
            case 3:
                /* Taxa Normal */
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico);
                break;
            default:
                System.out.println("Output impossível.");
                return null;
        }
        return produto;
    }

    private ProdutoAlimentar.Certificacao[] lerCertificacoes() {
        // Bloco de texto para exibir as opções
        System.out.println("""
                Certificações disponíveis:
                1 - ISO22000
                2 - FSSC22000
                3 - HACCP
                4 - GMP
                Insira até 4 certificações separadas por vírgulas (ex: 1,2,3):""");

        // Array para armazenar as certificações escolhidas
        ProdutoAlimentar.Certificacao[] certificacoesEscolhidas = new ProdutoAlimentar.Certificacao[4];
        int count = 0; // Contador para rastrear quantas certificações foram selecionadas

        while (count < 4) {
            // Lê a entrada do usuário
            System.out.print("Escolha suas certificações: ");
            String entrada = scanner.nextLine();

            // Divide a entrada por vírgulas
            String[] escolhas = entrada.split(",");

            // Valida cada escolha
            boolean valido = true; // Rastreamento de validade
            for (String escolha : escolhas) {
                int indice;
                try {
                    indice = Integer.parseInt(escolha.trim());
                } catch (NumberFormatException e) {
                    valido = false;
                    System.out.println("Entrada inválida: " + escolha);
                    break;
                }

                if (indice < 1 || indice > 4) {
                    System.out.println("Certificação inválida: " + escolha);
                    valido = false;
                    break;
                }

                // Obtém a certificação correspondente
                ProdutoAlimentar.Certificacao certificacao = ProdutoAlimentar.Certificacao.values()[indice - 1];

                // Verifica duplicatas no array
                boolean duplicado = false;
                for (ProdutoAlimentar.Certificacao c : certificacoesEscolhidas) {
                    if (c == certificacao) {
                        duplicado = true;
                        System.out.println("Certificação já selecionada: " + certificacao);
                        break;
                    }
                }

                if (!duplicado) {
                    certificacoesEscolhidas[count] = certificacao;
                    count++;
                }

                // Limita a 4 escolhas
                if (count >= 4) {
                    break;
                }
            }

            if (valido && count < 4) {
                System.out.printf("Você escolheu %d certificações. Escolha mais %d.%n",
                        count, 4 - count);
            } else if (!valido) {
                System.out.println("Por favor, insira novamente.");
            }
        }

        // Exibe as certificações escolhidas
        System.out.println("Certificações escolhidas:");
        for (ProdutoAlimentar.Certificacao c : certificacoesEscolhidas) {
            if (c != null) {
                System.out.println("- " + c);
            }
        }

        return certificacoesEscolhidas;
    }

    public Fatura lerFatura(Cliente cliente) {
        int id = lerInt("Insere o ID: ");
        Calendar cal = lerData();

        /* Pedir Produtos */
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        boolean adicionarProduto;
        do {
            adicionarProduto = lerBoolean("Deseja adicionar um produto? ");
            if (adicionarProduto) {
                Produto novoProduto = lerProduto();
                produtos.add(novoProduto);
            }

        } while (adicionarProduto);

        return new Fatura(id, cal, cliente, produtos);
    }

    public Cliente criarCliente(String nome) {
        String contribuinte = lerString("Insira o número de contribuinte: ");

        int idx = lerEnum(Cliente.Localizacao.values());
        Cliente.Localizacao localizacao = Cliente.Localizacao.values()[idx];

        ArrayList<Fatura> faturas = new ArrayList<>(); // Lista de faturas vazia

        // Criar o cliente
        return new Cliente(nome, contribuinte, localizacao, faturas);
    }

    public void editarCliente(Cliente cliente) {
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
                case 1:
                    String novoNome = lerString("Novo nome: ");
                    cliente.setNome(novoNome);
                    break;
                case 2:
                    // FALTA VALIDACAO
                    String novoContribuinte = lerString("Novo contribuinte: ");
                    cliente.setContribuinte(novoContribuinte);
                    break;
                case 3:
                    System.out.print("Nova Localização: ");
                    int idx = lerEnum(Cliente.Localizacao.values());
                    Cliente.Localizacao novaLocalizacao = Cliente.Localizacao.values()[idx];
                    cliente.setLocalizacao(novaLocalizacao);
                    break;
                case 4:
                    System.out.print("Nova Fatura");
            }
        }
        while (input != 0);
    }
}