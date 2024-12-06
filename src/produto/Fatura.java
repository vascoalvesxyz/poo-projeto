package produto;

import gestao.GestorProdutos;
import io.Leitor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fatura implements Serializable {
    private static final long serialVersionUID = 4L;

    private final int id;
    private final Calendar data;
    private final Cliente cliente;
    private final GestorProdutos produtos;


    public Fatura() {
        this.id = 0;
        this.data = Calendar.getInstance();
        this.cliente = new Cliente();
        this.produtos = new GestorProdutos();
    }

    public Fatura(int id, Calendar data, Cliente cliente, Leitor leitor) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produtos = new GestorProdutos();
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
        res += String.format("\tCLIENTE: \n\t  %s\n", cliente.toString());
        res += String.format("\tPRODUTOS:\n");
        double total = 0;
        for (Produto produto : produtos.getProdutos()) {
            double IVA = produto.calcIva(cliente.getLocalizacao());
            double valorReal = produto.getValorUnitario() * (1 + (IVA / 100));
            double totalProduto = valorReal * produto.getQuantidade();
            total += totalProduto;
            res += String.format("\t- %s\n\t  IVA: %.1f%%, Valor Real: %.2f€, Total: %.2f€\n", produto.toString(), IVA, valorReal, totalProduto);
        }
        res += String.format("\tTOTAL: %.2f€\n", total);
        return res;
    }

    public int getId() {
        return id;
    }

    public String toFile() {
        String dataStr = String.format("%02d/%02d/%04d", data.get(Calendar.DAY_OF_MONTH), data.get(Calendar.MONTH) + 1, data.get(Calendar.YEAR));
        return String.format("FATURA,%d,%s,%s\n", getProdutos().size(), id, dataStr);
    }

    public GestorProdutos getProdutos() {
        return produtos;
    }

    //public Fatura[] import() { }
    //public void export() { }
}
