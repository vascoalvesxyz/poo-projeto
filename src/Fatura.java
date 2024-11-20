import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Fatura {

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    int id;
    Calendar data;
    ArrayList<Produto> produtos;

    public Fatura(int id, Calendar data, ArrayList<Produto> produtos) {
        this.id = id;
        this.data = data;
        this.produtos = produtos;
    }

    public void print() {
        System.out.println(String.format("== ID: %02d, Data: %s ==", id, data.toString()));
        for (Produto produto : produtos) {
            System.out.println(produto.toString());
        }
        System.out.println("========================");
    }
    //public Fatura[] import() { }
    //public void export() { }
}
