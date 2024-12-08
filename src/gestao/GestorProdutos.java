package gestao;

import io.Leitor;
import produto.*;
import produto.ProdutoFarmaciaSemReceita.Categoria;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe responsável pela gestão de produtos, permitindo criar, editar,
 * listar e procurar produtos de diferentes tipos.
 */
public class GestorProdutos extends Gestor<Produto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    /**
     * Construtor padrão que inicializa a lista de produtos.
     */
    public GestorProdutos() {
        super();
    }

    /**
     * Procura um produto pelo código único.
     *
     * @param codigo Código do produto a procurar.
     * @return O produto encontrado ou {@code null} se não for encontrado.
     */
    public Produto procurarPorCodigo(int codigo) {
        for (Produto p : array)
            if (p.getCodigo() == codigo) {
                return p;
            }
        return null;
    }

    /**
     * Permite criar ou editar um produto com base no código fornecido.
     * Se o produto não existir, é criado; caso contrário, pode ser editado.
     */
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

    /**
     * Edita as informações de um produto existente.
     * Este método deve ser implementado com os campos específicos para edição.
     *
     * @param item Produto a ser editado.
     */
    @Override
    public void editar(Produto item) {
        // Implementação personalizada para edição de produtos.
    }

    /**
     * Cria um novo produto com base no código e nas informações fornecidas pelo utilizador.
     *
     * @param codigo Código do produto a ser criado.
     */
    public void criar(int codigo) {
        String resposta;
        do {
            resposta = Leitor.lerString("O produto é alimentar ou farmaceutico? (a/f) ");
        } while (!resposta.equalsIgnoreCase("a") && !resposta.equalsIgnoreCase("f"));
        boolean isAlimentar = resposta.equalsIgnoreCase("a");

        Produto produto;
        if (isAlimentar) {
            produto = pedirProdutoAlimentar(codigo);
        } else {
            produto = pedirProdutoFarmaceutico(codigo);
        }
        adicionar(produto);
    }

    /**
     * Solicita ao utilizador as informações de um produto alimentar para criar um novo produto.
     *
     * @param codigo Código do produto.
     * @return O produto alimentar criado.
     */
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

    /**
     * Solicita ao utilizador as certificações de um produto alimentar.
     *
     * @return Lista de certificações selecionadas pelo utilizador.
     */
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
        } while (continuar);
        return certificacoesEscolhidas;
    }

    /**
     * Solicita ao utilizador as informações de um produto farmacêutico para criar um novo produto.
     *
     * @param codigo Código do produto.
     * @return O produto farmacêutico criado.
     */
    private Produto pedirProdutoFarmaceutico(int codigo) {
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
