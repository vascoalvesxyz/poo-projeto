package gestao;

public interface Gestor<Tipo> {
    Tipo criar();
    void editar(Tipo obj);
    void adicionar(Tipo obj);
    void listar();
}
