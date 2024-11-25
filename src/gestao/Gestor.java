package gestao;

public interface Gestor<Tipo> {

    void criarOuEditar();

    void adicionar(Tipo obj);

    void listar();

}
