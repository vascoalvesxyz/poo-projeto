package produto;

import gestao.GestorProdutos;
import gestao.Leitor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fatura {

    private final int id;
    private final Calendar data;
    private final Cliente cliente;
    private final GestorProdutos produtos;

    public Fatura(int id, Calendar data, Cliente cliente, Leitor leitor) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produtos = new GestorProdutos(leitor);
    }

    /* Visualizar fatura: apresentar o número da fatura, os dados do cliente e, em seguida, listar
        os produtos da fatura. Para cada produto, apresentar os dados relevantes do produto, o
        valor total sem IVA, a taxa do IVA, o valor do IVA e o valor total com IVA. Após esta lista,
        apresentar da fatura o total sem IVA, o valor total do IVA e o valor total com IVA.
     */
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String res = String.format("== ID: %02d, Data: %s ==%n\n", id, sdf.format(data.getTime()));
        res += String.format("\t> Cliente:\n\t\t%s\n", cliente.toString());
        res += String.format("\t> Produtos:\n");
        for (Produto produto : produtos.getProdutos()) {
            res += "\t\t" + produto.toString();
        }
        res += "========================\n";
        return res;
    }

    public int getId() {
        return id;
    }

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    public void toFile() {
        String filename = String.format("fatura-%s-%s.txt", cliente.getNome(), data.getTime());
        try {
            File faturaFile = new File(filename);
            if (faturaFile.createNewFile()) {
                System.out.println("File created: " + faturaFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public GestorProdutos getProdutos() {
        return produtos;
    }

    //public Fatura[] import() { }
    //public void export() { }
}
