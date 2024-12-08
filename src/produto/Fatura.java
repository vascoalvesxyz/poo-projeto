package produto;

import gestao.GestorProdutos;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Classe que representa uma fatura. Contém informações como ID, data, cliente associado e produtos.
 */
public class Fatura implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    /**
     * ID único da fatura.
     */
    private int id;

    /**
     * Data da emissão da fatura.
     */
    private Calendar data;

    /**
     * Cliente associado à fatura.
     */
    private final Cliente cliente;

    /**
     * Gestor de produtos associados à fatura.
     */
    private final GestorProdutos produtos;

    /**
     * Construtor que inicializa a fatura com os dados fornecidos.
     *
     * @param id      ID da fatura.
     * @param data    Data da emissão da fatura.
     * @param cliente Cliente associado à fatura.
     */
    public Fatura(int id, Calendar data, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.produtos = new GestorProdutos();
    }

    /**
     * Obtém o ID da fatura.
     *
     * @return ID da fatura.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da fatura.
     *
     * @param id ID a ser definido.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define a data da fatura.
     *
     * @param data Data a ser definida.
     */
    public void setData(Calendar data) {
        this.data = data;
    }

    /**
     * Obtém o gestor de produtos associados à fatura.
     *
     * @return Gestor de produtos.
     */
    public GestorProdutos getProdutos() {
        return produtos;
    }

    /**
     * Converte as informações da fatura para uma string formatada.
     *
     * @return Representação em string da fatura.
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String res = String.format("=== ID: %d, DATA: %s ===%n", id, sdf.format(data.getTime()));
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

    /**
     * Converte as informações da fatura para um formato adequado para escrita em ficheiros.
     *
     * @return Representação em string para ficheiro.
     */
    public String toFile() {
        String dataStr = String.format("%02d/%02d/%04d", data.get(Calendar.DAY_OF_MONTH), data.get(Calendar.MONTH) + 1, data.get(Calendar.YEAR));
        return String.format("FATURA;%d;%s;%s\n", getProdutos().getArraySize(), id, dataStr);
    }
}
