package gestao;

import io.Leitor;
import produto.*;
import produto.ProdutoFarmaciaSemReceita.Categoria;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GestorProdutos extends Gestor<Produto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    public GestorProdutos() {super();}

    public Produto procurarPorCodigo(int codigo) {
        for (Produto p : array)
            if (p.getCodigo() == codigo) {
                return p;
            }
        return null;
    }

    @Override
    public void criarOuEditar() {
        int codigo = Leitor.lerUInt("Insira o código do produto: ");

        Produto produto = procurarPorCodigo(codigo);
        if (produto == null) {
            criar(codigo);
        } else if (Leitor.lerBoolean("Produto já existe, deseja editar?")) {
            editar(produto);
        }
    }

    @Override
    public void editar(Produto item) {

    }

    public void criar(int codigo) {
        String resposta;
        do {
            resposta = Leitor.lerString("O produto é alimentar ou farmaceutico? (a/f) ");
        }
        while (!resposta.equalsIgnoreCase("a") && !resposta.equalsIgnoreCase("f"));
        boolean isAlimentar = resposta.equalsIgnoreCase("a");

        Produto produto;
        if (isAlimentar) {
            produto = pedirProdutoAlimentar(codigo);
        } else {
            produto = pedirProdutoFarmaceutico(codigo);
        }
        adicionar(produto);
    }

    private Produto pedirProdutoAlimentar(int codigo) {
        String nome = Leitor.lerNome("Nome: ");
        String descricao = Leitor.lerDescricao("Descrição: ");
        int quantidade = Leitor.lerUInt("Quantidade: ");
        double valor = Leitor.lerUDouble("Valor Unitário: ");

        Produto produto;
        boolean biologico = Leitor.lerBoolean("O produto é biologico? ");

        System.out.println("1 - Taxa Reduzida\n2 - Taxa Intermédia\n3 - Taxa Normal\n");
        int inputInt = Leitor.lerIntMinMax("Escolha uma taxa", 1, 3);

        switch (inputInt) {
            case 1 ->
                    produto = new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valor, biologico, lerCertificacoes());
            case 2 -> {
                int idx = Leitor.lerEnum(ProdutoAlimentarTaxaIntermedia.Categoria.values());
                ProdutoAlimentarTaxaIntermedia.Categoria categoria = ProdutoAlimentarTaxaIntermedia.Categoria.values()[idx];
                produto = new ProdutoAlimentarTaxaIntermedia(codigo, nome, descricao, quantidade, valor, biologico, categoria);
            }
            case 3 -> produto = new ProdutoAlimentarTaxaNormal(codigo, nome, descricao, quantidade, valor, biologico);
            default -> {
                System.out.println("Output impossível.");
                return null;
            }
        }
        return produto;
    }

    private ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> lerCertificacoes() {
        ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesEscolhidas = new ArrayList<>();
        boolean continuar = true;
        do {
            int idx = Leitor.lerEnum(ProdutoAlimentarTaxaReduzida.Certificacao.values());
            ProdutoAlimentarTaxaReduzida.Certificacao certificacaoNova = ProdutoAlimentarTaxaReduzida.Certificacao.values()[idx];
            // Verificar se a certificação já está na lista
            if (certificacoesEscolhidas.contains(certificacaoNova)) {
                System.out.println("Certificação já foi selecionada. Escolha outra.");
                continue;
            }
            certificacoesEscolhidas.add(certificacaoNova);
            if (certificacoesEscolhidas.size() >= 4) {
                break;
            }
            continuar = Leitor.lerBoolean("Quer adicionar mais certificações?");
        }
        while (continuar);
        return certificacoesEscolhidas;
    }

    private Produto pedirProdutoFarmaceutico(int codigo) {
        /* lerString() pois produtos farmaceuticos podem ter números */
        String nome = Leitor.lerString("Nome: ");
        String descricao = Leitor.lerDescricao("Descrição: ");
        int quantidade = Leitor.lerUInt("Quantidade: ");
        double valor = Leitor.lerUDouble("Valor Unitário: ");

        boolean temPrescricao = Leitor.lerBoolean("O produto tem prescrição? ");

        if (temPrescricao) {
            String medico = Leitor.lerString("Nome do médico que fez a receita: ");
            return new ProdutoFarmaciaPrescrito(codigo, nome, descricao, quantidade, valor, medico);
        } else {
            int idx = Leitor.lerEnum(ProdutoFarmaciaSemReceita.Categoria.values());
            Categoria categoria = ProdutoFarmaciaSemReceita.Categoria.values()[idx];
            return new ProdutoFarmaciaSemReceita(codigo, nome, descricao, quantidade, valor, categoria);
        }
    }
}