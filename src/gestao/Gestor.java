package gestao;

import io.Leitor;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Gestor<Tipo> implements Serializable {

    protected ArrayList<Tipo> array;

    public Gestor() {
        this.array = new ArrayList<Tipo>();
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

    public Tipo selecionar() throws IllegalArgumentException {
        Leitor leitor = new Leitor();
        // Está vazio.
        if (array.isEmpty()) {
            throw new IllegalArgumentException("Array vazio.");
        }
        // Só exite 1 cliente
        else if (array.size() == 1) {
            return array.getFirst();
        }
        for (int i = 1; i <= array.size(); i++) {
            System.out.printf("%2d - %s\n", i, array.get(i-1).toString());
        }
        int idx = leitor.lerIntMinMax("Opção", 1, array.size() ) - 1;
        return array.get(idx);
    }

    protected void adicionar(Tipo obj) {
        array.add(obj);
    }

    public int getArraySize() {
        return array.size();
    }

    public ArrayList<Tipo> getArray() {
        return array;
    }

}
