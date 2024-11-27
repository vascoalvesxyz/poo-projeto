package gestao;

import produto.*;
import produto.ProdutoFarmaciaSemReceita.Categoria;

import java.util.ArrayList;

public class GestorProdutos extends Gestor<Produto> {

    public GestorProdutos(Leitor leitor) {
        super(new ArrayList<Produto>(), leitor);
    }

    public Produto procurarPorCodigo(int codigo) {
        for (Produto p : array)
            if (p.getCodigo() == codigo)
                return p;
        return null;
    }

    @Override
    public void criarOuEditar() {
        int codigo = leitor.lerInt("Insira o código do produto: ");

        Produto produto = procurarPorCodigo(codigo);
        if (produto == null) {
            criar(codigo);
        } else if (leitor.lerBoolean("Produto já existe, deseja editar?")) {
            editar(produto);
        }
    }

    public void criar(int codigo) {
        String resposta;
        do {
            resposta = leitor.lerString("O produto é alimentar ou farmaceutico? (a/f) ");
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

    public void editar(Produto obj) {
        return;
    }

    public ArrayList<Produto> getProdutos() {
        return this.array;
    }

    private Produto pedirProdutoAlimentar(int codigo) {
        String nome = leitor.lerString("Nome: ");
        String descricao = leitor.lerString("Descrição: ");
        int quantidade = leitor.lerInt("Quantidade: ");
        int valor = leitor.lerInt("Valor Unitário: ");

        Produto produto = null;
        boolean biologico = leitor.lerBoolean("O produto é biologico? ");

        System.out.println("1 - Taxa Reduzida\n 2 - Taxa Intermédia\n 3 - Taxa Normal\n");
        int inputInt = leitor.lerIntMinMax("Escolha uma taxa", 1, 3);

        switch (inputInt) {
            case 1 -> produto = new ProdutoAlimentarTaxaReduzida(codigo, nome, descricao, quantidade, valor, biologico, lerCertificacoes());
            case 2 -> {
                int idx = leitor.lerEnum(ProdutoAlimentarTaxaIntermedia.Categoria.values());
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
            int idx = leitor.lerEnum(ProdutoAlimentarTaxaReduzida.Certificacao.values());
            ProdutoAlimentarTaxaReduzida.Certificacao certificacaoNova = ProdutoAlimentarTaxaReduzida.Certificacao.values()[idx];
            // Verificar se a certificação já está na lista
            if (certificacoesEscolhidas.contains(certificacaoNova)) {
                System.out.println("Certificação já foi selecionada. Escolha outra.");
                continue;
            }
            certificacoesEscolhidas.add(certificacaoNova);
            if (certificacoesEscolhidas.size() >= 4) break;
            continuar = leitor.lerBoolean("Quer adicionar mais certificações?");
        }
        while (continuar);
        return certificacoesEscolhidas;
    }

    private Produto pedirProdutoFarmaceutico(int codigo) {
        String nome = leitor.lerString("Nome: ");
        String descricao = leitor.lerString("Descrição: ");
        int quantidade = leitor.lerInt("Quantidade: ");
        int valor = leitor.lerInt("Valor Unitário: ");

        boolean temPrescricao = leitor.lerBoolean("O produto tem prescrição? ");

        if (temPrescricao) {
            String medico = leitor.lerString("Nome do médico que fez a receita: ");
            return new ProdutoFarmaciaPrescrito(codigo, nome, descricao, quantidade, valor, medico);
        } else {
            int idx = leitor.lerEnum(ProdutoFarmaciaSemReceita.Categoria.values());
            Categoria categoria = ProdutoFarmaciaSemReceita.Categoria.values()[idx];
            return new ProdutoFarmaciaSemReceita(codigo, nome, descricao, quantidade, valor, categoria);
        }
    }
}