package gestao;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Gestor<Tipo> implements Serializable {

    protected ArrayList<Tipo> array;

    public Gestor() {
        this.array = new ArrayList<>();
    }

    public Gestor(ArrayList<Tipo> array) {
        this.array = array;
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
