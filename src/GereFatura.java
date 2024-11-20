import java.util.ArrayList;

abstract class FaturasArray {

    private ArrayList<Fatura> Faturas = new ArrayList<Fatura>();

    abstract protected void sort();
    abstract protected void print();
}
