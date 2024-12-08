package produto;

import gestao.GestorProdutos;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fatura implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private int id;
    private Calendar data;
    private final Cliente cliente;
    private final GestorProdutos produtos;

    public Fatura(int id, Calendar data, Cliente cliente) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String res = String.format("=== ID: %d, DATA: %s ===%n", id, sdf.format(data.getTime()));
        //int len = res.length();
        res += String.format("\tCLIENTE: \n\t  %s\n", cliente.toString());
        res += "\tPRODUTOS:\n";
        double total = 0;
        for (Produto produto : produtos.getArray()) {
            double IVA = produto.calcIva(cliente.getLocalizacao());
            double valorReal = produto.getValorUnitario() * (1 + (IVA / 100));
            double totalProduto = valorReal * produto.getQuantidade();
            total += totalProduto;
            res = res.concat(String.format("\t- %s\n\t  IVA: %.1f%%, Valor Real: %.2f€, Total: %.2f€\n", produto, IVA, valorReal, totalProduto));
        }
        res += String.format("\tTOTAL: %.2f€\n", total);
        return res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String toFile() {
        String dataStr = String.format("%02d/%02d/%04d", data.get(Calendar.DAY_OF_MONTH), data.get(Calendar.MONTH) + 1, data.get(Calendar.YEAR));
        return String.format("FATURA;%d;%s;%s\n", getProdutos().getArraySize(), id, dataStr);
    }

    public GestorProdutos getProdutos() {
        return produtos;
    }

    //public Fatura[] import() { }
    //public void export() { }
}
