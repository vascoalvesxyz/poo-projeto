package gestao;

import io.Leitor;

import java.util.ArrayList;

public abstract class Gestor<Tipo> {

    protected ArrayList<Tipo> array;
    protected Leitor leitor;

    public Gestor() {
        this.array = new ArrayList<>();
        this.leitor = new Leitor();
    }

    public Gestor(ArrayList<Tipo> array, Leitor leitor) {
        this.array = array;
        this.leitor = leitor;
    }

    public abstract void criarOuEditar();

    public void listar() {
        for (Tipo obj : array) {
            System.out.println(obj.toString());
        }
    }

    protected void adicionar(Tipo obj) {
        array.add(obj);
    }

    public int size() {
        return array.size();
    }

    public ArrayList<Tipo> getArray() {
        return array;
    }

}
