import produto.Produto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Fatura {

    private final int id;
    private final Calendar data;
    private final Cliente cliente;
    private final ArrayList<Produto> produtos;

    public Fatura(int id, Calendar data, Cliente cliente, ArrayList<Produto> produtos) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produtos = produtos;
    }

    /* Visualizar fatura: apresentar o número da fatura, os dados do cliente e, em seguida, listar
        os produtos da fatura. Para cada produto, apresentar os dados relevantes do produto, o
        valor total sem IVA, a taxa do IVA, o valor do IVA e o valor total com IVA. Após esta lista,
        apresentar da fatura o total sem IVA, o valor total do IVA e o valor total com IVA.
     */
    public void print() {
        System.out.printf("== ID: %02d, Data: %s ==%n", id, data.toString());
        System.out.printf("\t> Cliente:\n\t\t%s%n", cliente.toString());
        System.out.println("\t> Produtos:");
        for (Produto produto : produtos) {
            System.out.println("\t\t" + produto.toString());
            System.out.println("\t\tPreço (ajustado ao IVA do cliente): " + produto.calcIva(cliente.getLocalizacao()));
        }
        System.out.println("========================");
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
            e.printStackTrace();
        }
    }

    //public Fatura[] import() { }
    //public void export() { }
}
