package produto;

import gestao.GestorProdutos;
import io.Leitor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fatura implements Serializable {
    private static final long serialVersionUID = 4L;

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
        String res = String.format("=== ID: %02d, DATA: %s ===%n", id, sdf.format(data.getTime()));
        //int len = res.length();
        res += String.format("\tCLIENTE: %s\n", cliente.toString());
        res += String.format("\tPRODUTOS:\n");
        double total = 0;
        for (Produto produto : produtos.getProdutos()) {
            double IVA = produto.calcIva(cliente.getLocalizacao());
            double valorReal = produto.getValorUnitario() * (1+(IVA/100));
            double totalProduto = valorReal*produto.getQuantidade();
            total += totalProduto;
            res += String.format("\t- %s\n\t  IVA: %.1f%%, Valor Real: %.2f, Total: %.2f\n", produto.toString(), IVA, valorReal, totalProduto);
        }
        res += String.format("\tTOTAL: %.2f\n", total);
        res += "\n\n";
        return res;
    }

    public int getId() {
        return id;
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
