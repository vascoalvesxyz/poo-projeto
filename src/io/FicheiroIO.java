package io;

import gestao.GestorClientes;
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

    public ArrayList<Cliente> importarClientes(String filename) {
        if (!existeFicheiro(filename)) {
            return null;
        }
        String[] temp = filename.split("\\.");
        String ext = temp[temp.length - 1];
        if (ext.equalsIgnoreCase("ser")) {
            return lerFicheiroObjetos(filename);
        } else if (ext.equalsIgnoreCase("txt")) {
            return lerFicheiroTexto(filename);
        }
        System.out.println("Não existe sei como ler este ficheiro.");
        return null;
    }

    public boolean existeFicheiro(String pathStr) {
        Path caminho = Paths.get(pathStr);
        return Files.exists(caminho);
    }

    private ArrayList<Cliente> lerFicheiroObjetos(String filename) {
        if (!existeFicheiro(filename)) {
            return null;
        }
        try (
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            ArrayList<Cliente> arr = (ArrayList<Cliente>) objIn.readObject();
            return arr;
        } catch (Exception e) {
            System.out.printf("Algo correu mal: %s\n", e);
        }
        return null;
    }

    private ArrayList<Cliente> lerFicheiroTexto(String filename) {
        if (!existeFicheiro(filename)) {
            return null;
        }

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
            case "ProdutoAlimentarTaxaReduzida":
                produto = new ProdutoAlimentarTaxaReduzida(
                        produtoCodigo,
                        produtoNome,
                        produtoDescricao,
                        produtoQuantidade,
                        produtoValorUnitario,
                        Boolean.parseBoolean(dados[6]),
                        lerCertificacoes(dados[7])
                );
                break;
            case "ProdutoAlimentarTaxaIntermedia":
                produto = new ProdutoAlimentarTaxaIntermedia(
                        produtoCodigo,  // idx = 1
                        produtoNome,    // idx = 2
                        produtoDescricao,
                        produtoQuantidade,
                        produtoValorUnitario,
                        Boolean.parseBoolean(dados[6]),
                        ProdutoAlimentarTaxaIntermedia.Categoria.valueOf(dados[7])
                );
                break;
            case "ProdutoAlimentarTaxaNormal":
                produto = new ProdutoAlimentarTaxaNormal(
                        produtoCodigo,
                        produtoNome,
                        produtoDescricao,
                        produtoQuantidade,
                        produtoValorUnitario,
                        Boolean.parseBoolean(dados[6])
                );
                break;
            case "ProdutoFarmaciaPrescrito":
                produto = new ProdutoFarmaciaPrescrito(
                        produtoCodigo,
                        produtoNome,
                        produtoDescricao,
                        produtoQuantidade,
                        produtoValorUnitario,
                        dados[6]
                );
                break;
            case "ProdutoFarmaciaSemReceita":
                produto = new ProdutoFarmaciaSemReceita(
                        produtoCodigo,
                        produtoNome,
                        produtoDescricao,
                        produtoQuantidade,
                        produtoValorUnitario,
                        ProdutoFarmaciaSemReceita.Categoria.valueOf(dados[6])
                );
                break;
            default:
                System.out.println("Tipo de produto desconhecido: " + dados[5]);
                break;
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
}
