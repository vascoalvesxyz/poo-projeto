package gestao;

import java.util.ArrayList;

public abstract class Gestor<Tipo> {

    protected ArrayList<Tipo> array;
    protected Leitor leitor;

    public Gestor(ArrayList<Tipo> array, Leitor leitor) {
        this.array = array;
        this.leitor = leitor;
    }

    public abstract void criarOuEditar();

    public void listar() {
        for (Tipo obj : array) {
            System.out.println(obj.toString());
        }
    };

    protected void adicionar(Tipo obj) {
        array.add(obj);
    }

}
