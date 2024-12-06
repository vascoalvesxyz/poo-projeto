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

public class FicheiroIO {

    Leitor leitor;

    public FicheiroIO(Leitor leitor) {
        this.leitor = leitor;
    }

    public void importarClientes(GestorClientes gc, String filename) {
        if (!existeFicheiro(filename)) return;

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

    public boolean existeFicheiro(String pathStr) {
        Path caminho = Paths.get(pathStr);
        return Files.exists(caminho);
    }

    private ArrayList<Cliente> lerFicheiroObjetos(String filename) {
        try (
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            ArrayList<Cliente> arr = (ArrayList<Cliente>) objIn.readObject();
            return arr;
        } catch (Exception e) {
            System.out.printf("Algo correu mal: %s\n", e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Cliente> lerFicheiroTexto(String filename) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        File f = new File(filename);

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            do {
                String[] dados = linha.split(",");
                if (dados[0].equals("CLIENTE")) {
                    System.out.printf("Importando Cliente: %s\n", dados[2]);
                    Cliente novoCliente = lerCliente(br, linha);
                    clientes.add(novoCliente);
                }
            } while ((linha = br.readLine()) != null);
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao abrir ficheiro de texto.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro de texto.");
        }

        return clientes;
    }

    // Tipo, Filhos, Cliente_Nome, Cliente_Contribuinte, Cliente_Localizacao
    private Cliente lerCliente(BufferedReader br, String linha) throws IOException {
        String[] dados = linha.split(",");
        Cliente clienteNovo = new Cliente(dados[2], dados[3], Cliente.Localizacao.valueOf(dados[4]), leitor);

        int nFaturas = Integer.parseInt(dados[1]);
        for (int i = 0; i < nFaturas; i++) {
           linha = br.readLine();
           Fatura novaFatura = lerFatura(br, linha, clienteNovo);
           clienteNovo.getFaturas().adicionar(novaFatura);
        }

        return clienteNovo;
    }

    // Tipo, Filhos, Fatura_ID, Fatura_Data
    private Fatura lerFatura(BufferedReader br, String linha, Cliente cliente) throws IOException {
        String[] dados = linha.split(",");
        Fatura novaFatura = new Fatura(Integer.parseInt(dados[2]), leitor.validarData(dados[3]), cliente, leitor);

        int nProdutos = Integer.parseInt(dados[1]);
        for (int i = 0; i < nProdutos; i++) {
            linha = br.readLine();
            Produto novoProduto = lerProduto(linha);
            novaFatura.getProdutos().adicionar(novoProduto);
        }

        return novaFatura;
    }

    //Produto_Tipo,Produto_Codigo,Produto_Nome,Produto_Descricao,Produto_Quantidade,Produto_ValorUnitario,EXTRA
    private Produto lerProduto(String input) {
        String[] dados = input.split(",");
        Produto produto = null;
        int produtoCodigo = Integer.parseInt(dados[1]), produtoQuantidade = Integer.parseInt(dados[4]);
        String produtoNome = dados[2], produtoDescricao = dados[3];
        double produtoValorUnitario = Double.parseDouble(dados[5]);
        switch (dados[0]) {
            case "ProdutoAlimentarTaxaReduzida" -> produto = new ProdutoAlimentarTaxaReduzida( produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]), lerCertificacoes(dados[7]) );
            case "ProdutoAlimentarTaxaIntermedia" -> produto = new ProdutoAlimentarTaxaIntermedia( produtoCodigo,  produtoNome,    produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]), ProdutoAlimentarTaxaIntermedia.Categoria.valueOf(dados[7]) );
            case "ProdutoAlimentarTaxaNormal" -> produto = new ProdutoAlimentarTaxaNormal( produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, Boolean.parseBoolean(dados[6]) );
            case "ProdutoFarmaciaPrescrito" -> produto = new ProdutoFarmaciaPrescrito( produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, dados[6] );
            case "ProdutoFarmaciaSemReceita" -> produto = new ProdutoFarmaciaSemReceita( produtoCodigo, produtoNome, produtoDescricao, produtoQuantidade, produtoValorUnitario, ProdutoFarmaciaSemReceita.Categoria.valueOf(dados[6]) );
            default -> System.out.println("Tipo de produto desconhecido: " + dados[5]);
        }

        return produto;
    }

    private ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> lerCertificacoes(String certificacoes) {
        String[] certificacoesDivididas = certificacoes.split("-");
        ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesLista = new ArrayList<>();
        for (String c : certificacoesDivididas) {
            certificacoesLista.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(c));
        }
        return certificacoesLista;
    }

    public void exportarClientesObj(GestorClientes gc, String filename) {
        // Serialize the object to the file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            ArrayList<Cliente> arrayClientes = gc.getArray();
            oos.writeObject(arrayClientes);
        } catch (IOException e) {
            System.err.println("Erro a serilizar: " + e.getMessage());
        }
    }

    public void exportarClientesTexto(GestorClientes gc, String filename) {
        if (existeFicheiro(filename)) {
            System.out.println("Ficheiro já existe!");
            return;
        }

        File f = new File(filename);

        try (
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
        ) {
            for (Cliente c : gc.getTodosClientes()) {
                escreverCliente(bw, c);
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel escrever o ficheiro: " + e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void escreverCliente(BufferedWriter bw, Cliente c) throws IOException {
        bw.write(c.toFile());
        GestorFaturas gf = c.getFaturas();
        for (Fatura f: gf.getArray()) {
            escreverFatura(bw, f);
        }
    }

    private void escreverFatura(BufferedWriter bw, Fatura f) throws IOException {
        bw.write(f.toFile());
        GestorProdutos gp = f.getProdutos();
        for (Produto p: gp.getArray()) {
            escreverProduto(bw, p);
        }
    }

    private void escreverProduto(BufferedWriter bw, Produto p) throws IOException {
        bw.write(p.toFile());
    }

}
