package gestao;
import produto.Produto;
import produto.ProdutoAlimentar;
import produto.ProdutoFarmaciaPrescrito;
import produto.ProdutoFarmaciaSemReceita;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorProdutos extends Menu implements Gestor<Produto> {

    private final ArrayList<Produto> produtos;

    public GestorProdutos() {
        this.produtos = new ArrayList<>();
    }

    @Override
    public Produto criar() {
        String resposta;
        do {
            resposta = lerString("O produto é alimentar ou farmaceutico? (a/f) ");
        } while (!resposta.equalsIgnoreCase("a") && !resposta.equalsIgnoreCase("f"));
        boolean isAlimentar = resposta.equalsIgnoreCase("a");

        Produto novoProduto;
        if (isAlimentar) {
            novoProduto = pedirProdutoAlimentar();
        } else {
            novoProduto = pedirProdutoFarmaceutico();
        }
        return novoProduto;
    }

    @Override
    public void editar(Produto obj) {
        return;
    }

    @Override
    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public void listar() {
        for (Produto produto : produtos)
            System.out.println(produto.toString());
    }

    public ArrayList<Produto> getProdutos() {
        return this.produtos;
    }

    private Produto pedirProdutoAlimentar() {
        int codigo = lerInt("Codigo: ");
        String nome = lerString("Nome: ");
        String descricao = lerString("Descrição: ");
        int quantidade = lerInt("Quantidade : ");
        int valor = lerInt("Valor Unitário: ");

        Produto produto = null;
        boolean biologico = lerBoolean("O produto é biologico? ");

        ProdutoAlimentar.Taxa taxa;
        System.out.println("""
                1 - Taxa Reduzida
                2 - Taxa Intermédia
                3 - Taxa Normal
                """);

        int inputInt = lerIntMinMax("Escolha uma taxa", 1, 3);

        switch (inputInt) {
            case 1 -> {
                ProdutoAlimentar.Certificacao[] certificacoes = lerCertificacoes();
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico, certificacoes);
            }
            case 2 -> {
                ProdutoAlimentar.Categoria categoria = null;
                System.out.print("""
                        1 - Congelados,
                        2 - Enlatados,
                        3 - Vinhos
                        """);
                while (categoria == null) {
                    int escolha = lerIntMinMax("Escolha uma categoria ", 1, 3);
                    if (escolha >= 1 && escolha <= 3) {
                        categoria = ProdutoAlimentar.Categoria.values()[escolha - 1];
                    }
                }
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico, categoria);
            }
            case 3 -> produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valor, biologico);
            default -> {
                System.out.println("Output impossível.");
                return null;
            }
        }
        return produto;
    }

    private ProdutoAlimentar.Certificacao[] lerCertificacoes() {
        System.out.println("""
                Certificações disponíveis:
                1 - ISO22000
                2 - FSSC22000
                3 - HACCP
                4 - GMP
                Insira até 4 certificações separadas por vírgulas (ex: 1,2,3):""");

        ProdutoAlimentar.Certificacao[] certificacoesEscolhidas = new ProdutoAlimentar.Certificacao[4];
        int count = 0;

        while (count < 4) {
            String entrada = lerString("Escolha suas certificações: ");
            String[] escolhas = entrada.split(",");

            boolean valido = true;
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

                ProdutoAlimentar.Certificacao certificacao = ProdutoAlimentar.Certificacao.values()[indice - 1];
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

                if (count >= 4) {
                    break;
                }
            }

            if (valido && count < 4) {
                System.out.printf("Você escolheu %d certificações. Escolha mais %d.%n", count, 4 - count);
            } else if (!valido) {
                System.out.println("Por favor, insira novamente.");
            }
        }

        System.out.println("Certificações escolhidas:");
        for (ProdutoAlimentar.Certificacao c : certificacoesEscolhidas) {
            if (c != null) {
                System.out.println("- " + c);
            }
        }

        return certificacoesEscolhidas;
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
                int escolha = lerIntMinMax("Escolha uma categoria ", 1, 5);
                if (escolha >= 1 && escolha <= 5) {
                    categoria = ProdutoFarmaciaSemReceita.Categoria.values()[escolha - 1];
                }
            }
            produto = new ProdutoFarmaciaSemReceita(codigo, nome, descricao, quantidade, valor, categoria);
        }
        return produto;
    }


}
