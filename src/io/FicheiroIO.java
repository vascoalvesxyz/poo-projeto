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
        String[] temp = filename.split(".");
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
            GestorClientes gestorClientes = (GestorClientes) objIn.readObject();
        } catch (Exception e) {
            System.out.printf("Algo correu mal: %s\n", e);
        }
        return null;
    }

    /* TODO */
    private ArrayList<Cliente> lerFicheiroTexto(String filename) {
        if (!existeFicheiro(filename)) {
            return null;
        }

        ArrayList<Cliente> clientes = new ArrayList<>();
        File f = new File(filename);
        String blocoAtual = "";
        Cliente ultimoCliente = null;
        Fatura ultimaFatura = null;
        Produto ultimoProduto = null;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                if (linha.equals("CLIENTE")) {
                    blocoAtual = "CLIENTE";
                } else if (linha.equals("FATURA")) {
                    blocoAtual = "FATURA";
                } else if (linha.equals("PRODUTO")) {
                    blocoAtual = "PRODUTO";
                } else if (blocoAtual.equals("CLIENTE")) {
                    ultimoCliente = lerCliente(linha);
                    clientes.add(ultimoCliente);
                } else if (blocoAtual.equals("FATURA")) {
                    if (ultimoCliente == null) {
                        System.out.println("O ficheiro de dados iniciais é inválido.");
                        return null;
                    }
                    ultimaFatura = lerFatura(linha, ultimoCliente);
                    ultimoCliente.getFaturas().adicionar(ultimaFatura);
                } else if (blocoAtual.equals("PRODUTO")) {
                    if (ultimaFatura == null) {
                        System.out.println("O ficheiro de dados iniciais é inválido.");
                        return null;
                    }
                    ultimoProduto = lerProduto(linha, ultimaFatura);
                    ultimaFatura.getProdutos().adicionar(ultimoProduto);
                }
                br.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao abrir ficheiro de texto.");
        } catch (IOException ex) {
            System.out.println("Erro a ler ficheiro de texto.");
        }

        return clientes;
    }

    //Cliente_Nome,Cliente_Contribuinte,Cliente_Localizacao
    private Cliente lerCliente(String input) {
        String[] dados = input.split(",");
        return new Cliente(dados[0], dados[1], Cliente.Localizacao.valueOf(dados[2]), leitor);
    }

    //Fatura_ID,Fatura_Data
    private Fatura lerFatura(String input, Cliente cliente) {
        String[] dados = input.split(",");
        return new Fatura(Integer.parseInt(dados[0]), leitor.validarData(dados[1]), cliente, leitor);
    }

    //Produto_Tipo,Produto_Codigo,Produto_Nome,Produto_Descricao,Produto_Quantidade,Produto_ValorUnitario,EXTRA
    private Produto lerProduto(String input, Fatura fatura) {
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
                        produtoCodigo,
                        produtoNome,
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
        String[] certificacoesDivididas = certificacoes.split(",");
        ArrayList<ProdutoAlimentarTaxaReduzida.Certificacao> certificacoesLista = new ArrayList<>();
        for (String c : certificacoesDivididas) {
            certificacoesLista.add(ProdutoAlimentarTaxaReduzida.Certificacao.valueOf(c));
        }
        return certificacoesLista;
    }
}
