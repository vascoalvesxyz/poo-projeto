public class Fatura {

    public enum Categoria {
        BELEZA,
        BEM_ESTAR,
        BEBES,
        ANIMAIS,
        OUTRO
    }

    int id;
    Data data;
    Produto[] produtos;

    //public String toString() { }
    //public Fatura[] import() { }
    //public void export() { }
}
