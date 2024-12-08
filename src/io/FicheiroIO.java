package io;

import gestao.GestorClientes;
import gestao.GestorFaturas;
import gestao.GestorProdutos;
import produto.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe responsável por operações de entrada e saída relacionadas com ficheiros.
 * Inclui métodos para importar e exportar dados de clientes, faturas e produtos.
 */
public class FicheiroIO {

    /**
     * Separador utilizado nos ficheiros de texto para separar os campos.
     */
    static final String separador = ";";

    /**
     * Construtor padrão.
     */
    public FicheiroIO() {}

    /**
     * Importa dados de clientes para um gestor de clientes a partir de um ficheiro.
     * O tipo de ficheiro (texto ou objeto) é identificado pela extensão.
     *
     * @param gc       Gestor de clientes onde os dados serão carregados.
     * @param filename Nome do ficheiro a ser lido.
     */
    public void importarClientes(GestorClientes gc, String filename) {
        if (!existeFicheiro(filename)) {
            return;
        }

        String[] temp = filename.split("\\.");
        String ext = temp[temp.length - 1];

        ArrayList<Cliente> clientesImportados = new ArrayList<>();
        if (ext.equalsIgnoreCase("ser")) {
            clientesImportados = lerFicheiroObjetos(filename);
        } else if (ext.equalsIgnoreCase("txt")) {
            clientesImportados = lerFicheiroTexto(filename);
        } else {
            System.out.println("Não sei ler esse tipo de ficheiro.");
        }

        gc.getArray().addAll(clientesImportados);
    }

    /**
     * Verifica se um ficheiro existe.
     *
     * @param pathStr Caminho do ficheiro.
     * @return {@code true} se o ficheiro existir, {@code false} caso contrário.
     */
    public boolean existeFicheiro(String pathStr) {
        Path caminho = Paths.get(pathStr);
        return Files.exists(caminho);
    }

    /**
     * Lê um ficheiro de objetos e retorna uma lista de clientes.
     *
     * @param filename Nome do ficheiro de objetos.
     * @return Lista de clientes lida do ficheiro.
     */
    private ArrayList<Cliente> lerFicheiroObjetos(String filename) {
        try (
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            return (ArrayList<Cliente>) objIn.readObject();
        } catch (Exception e) {
            System.out.printf("Algo correu mal: %s\n", e);
        }
        return new ArrayList<>();
    }

    /**
     * Lê um ficheiro de texto e retorna uma lista de clientes.
     *
     * @param filename Nome do ficheiro de texto.
     * @return Lista de clientes lida do ficheiro.
     */
    private ArrayList<Cliente> lerFicheiroTexto(String filename) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        File f = new File(filename);

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            do {
                String[] dados = linha.split(";");
                if (dados[0].equals("CLIENTE")) {
                    System.out.printf("Importando Cliente: %s\n", dados[2]);
                    Cliente novoCliente = lerCliente(br, linha);
                    clientes.add(novoCliente);
                }
            }
            while ((linha = br.readLine()) != null);
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao abrir ficheiro de texto.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro de texto.");
        }

        return clientes;
    }

    /**
     * Lê os dados de um cliente a partir de um ficheiro de texto.
     *
     * @param br    BufferedReader para leitura do ficheiro.
     * @param linha Linha atual do ficheiro.
     * @return Um objeto {@code Cliente} com os dados lidos.
     * @throws IOException Se ocorrer um erro de leitura.
     */
    private Cliente lerCliente(BufferedReader br, String linha) throws IOException {
        String[] dados = linha.split(separador);
        Cliente clienteNovo = new Cliente(dados[2], dados[3], Cliente.Localizacao.valueOf(dados[4]));

        int nFaturas = Integer.parseInt(dados[1]);
        for (int i = 0; i < nFaturas; i++) {
            linha = br.readLine();
            Fatura novaFatura = lerFatura(br, linha, clienteNovo);
            clienteNovo.getFaturas().adicionar(novaFatura);
        }

        return clienteNovo;
    }

    /**
     * Lê os dados de uma fatura a partir de um ficheiro de texto.
     *
     * @param br      BufferedReader para leitura do ficheiro.
     * @param linha   Linha atual do ficheiro.
     * @param cliente Cliente associado à fatura.
     * @return Um objeto {@code Fatura} com os dados lidos.
     * @throws IOException Se ocorrer um erro de leitura.
     */
    private Fatura lerFatura(BufferedReader br, String linha, Cliente cliente) throws IOException {
        String[] dados = linha.split(separador);
        Fatura novaFatura = new Fatura(Integer.parseInt(dados[2]), Leitor.validarData(dados[3]), cliente);

        int nProdutos = Integer.parseInt(dados[1]);
        for (int i = 0; i < nProdutos; i++) {
            linha = br.readLine();
            Produto novoProduto = lerProduto(linha);
            novaFatura.getProdutos().adicionar(novoProduto);
        }

        return novaFatura;
    }

    /**
     * Lê os dados de um produto a partir de uma linha de texto.
     *
     * @param input Linha com os dados do produto.
     * @return Um objeto {@code Produto} com os dados lidos.
     */
    private Produto lerProduto(String input) {
        String[] dados = input.split(separador);
        Produto produto = null;
        int produtoCodigo = Integer.parseInt(dados[1]), produtoQuantidade = Integer.parseInt(dados[4]);
        String produtoNome = dados[2], produtoDescricao = dados[3];
        double produtoValorUnitario = Double.parseDouble(dados[5].replace(",", "."));
        switch (dados[0]) {
            case "ProdutoAlimentarTaxaReduzida" ->
                    produto = new ProdutoAlimentarTaxaReduzida(produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]), lerCertificacoes(dados[7]));
            case "ProdutoAlimentarTaxaIntermedia" ->
                    produto = new ProdutoAlimentarTaxaIntermedia(produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]), ProdutoAlimentarTaxaIntermedia.Categoria.valueOf(dados[7]));
            case "ProdutoAlimentarTaxaNormal" ->
                    produto = new ProdutoAlimentarTaxaNormal(produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]));
            case "ProdutoFarmaciaPrescrito" ->
                    produto = new ProdutoFarmaciaPrescrito(produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, dados[6]);
            case "ProdutoFarmaciaSemReceita" ->
                    produto = new ProdutoFarmaciaSemReceita(produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, ProdutoFarmaciaSemReceita.Categoria.valueOf(dados[6]));
            default -> System.out.println("Tipo de produto desconhecido: " + dados[5]);
        }

        return produto;
    }

    /**
     * Lê as certificações de um produto a partir de uma string.
     *
     * @param certificacoes String com as certificações separadas por "-".
     * @return Lista de certificações lidas.
     */
    private ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> lerCertificacoes(String certificacoes) {
        String[] certificacoesDivididas = certificacoes.split("-");
        ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesLista = new ArrayList<>();
        for (String c : certificacoesDivididas) {
            certificacoesLista.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(c));
        }
        return certificacoesLista;
    }

    /**
     * Exporta os dados dos clientes para um ficheiro de objetos.
     *
     * @param gc       Gestor de clientes cujos dados serão exportados.
     * @param filename Nome do ficheiro onde os dados serão armazenados.
     */
    public void exportarClientesObj(GestorClientes gc, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            ArrayList<Cliente> arrayClientes = gc.getArray();
            oos.writeObject(arrayClientes);
        } catch (IOException e) {
            System.err.println("Erro a serilizar: " + e.getMessage());
        }
    }

    /**
     * Exporta os dados dos clientes para um ficheiro de texto.
     *
     * @param gc       Gestor de clientes cujos dados serão exportados.
     * @param filename Nome do ficheiro onde os dados serão armazenados.
     */
    public void exportarClientesTexto(GestorClientes gc, String filename) {
        if (existeFicheiro(filename)) {
            System.out.println("Ficheiro já existe!");
            return;
        }

        File f = new File(filename);

        try (
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (Cliente c : gc.getArray()) {
                escreverCliente(bw, c);
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel escrever o ficheiro: " + e);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Escreve os dados de um cliente num ficheiro de texto.
     *
     * @param bw BufferedWriter para escrever no ficheiro.
     * @param c  Cliente cujos dados serão escritos.
     * @throws IOException Se ocorrer um erro ao escrever.
     */
    private void escreverCliente(BufferedWriter bw, Cliente c) throws IOException {
        bw.write(c.toFile());
        GestorFaturas gf = c.getFaturas();
        for (Fatura f : gf.getArray()) {
            escreverFatura(bw, f);
        }
    }

    /**
     * Escreve os dados de uma fatura num ficheiro de texto.
     *
     * @param bw BufferedWriter para escrever no ficheiro.
     * @param f  Fatura cujos dados serão escritos.
     * @throws IOException Se ocorrer um erro ao escrever.
     */
    private void escreverFatura(BufferedWriter bw, Fatura f) throws IOException {
        bw.write(f.toFile());
        GestorProdutos gp = f.getProdutos();
        for (Produto p : gp.getArray()) {
            escreverProduto(bw, p);
        }
    }

    /**
     * Escreve os dados de um produto num ficheiro de texto.
     *
     * @param bw BufferedWriter para escrever no ficheiro.
     * @param p  Produto cujos dados serão escritos.
     * @throws IOException Se ocorrer um erro ao escrever.
     */
    private void escreverProduto(BufferedWriter bw, Produto p) throws IOException {
        bw.write(p.toFile());
    }
}
