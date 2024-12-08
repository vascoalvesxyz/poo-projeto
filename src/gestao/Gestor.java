package gestao;

import io.Leitor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe abstrata responsável pela gestão de objetos genéricos.
 * Oferece funcionalidades comuns como criar, editar, listar, selecionar,
 * adicionar e remover objetos de um array.
 *
 * @param <Tipo> Tipo genérico dos objetos a serem geridos.
 */
public abstract class Gestor<Tipo> implements Serializable {

    /**
     * Lista que armazena os objetos geridos.
     */
    protected ArrayList<Tipo> array;

    /**
     * Construtor padrão que inicializa o array vazio.
     */
    public Gestor() {
        this.array = new ArrayList<>();
    }

    /**
     * Construtor que inicializa o array com uma lista fornecida.
     *
     * @param array Lista de objetos do tipo genérico a ser utilizada.
     */
    public Gestor(ArrayList<Tipo> array) {
        this.array = array;
    }

    /**
     * Método abstrato para criar ou editar objetos.
     * A implementação específica deve ser fornecida pelas subclasses.
     */
    public abstract void criarOuEditar();

    /**
     * Método abstrato para editar um objeto específico.
     * A implementação específica deve ser fornecida pelas subclasses.
     *
     * @param item Objeto a ser editado.
     */
    protected abstract void editar(Tipo item);

    /**
     * Lista todos os objetos no array, imprimindo-os no formato textual.
     */
    public void listar() {
        for (Tipo obj : array) {
            System.out.println(obj.toString());
        }
    }

    /**
     * Permite selecionar um objeto do array através de uma interação com o utilizador.
     * Caso o array esteja vazio, lança uma exceção.
     * Caso exista apenas um objeto, retorna esse diretamente.
     * Caso contrário, apresenta uma lista numerada para escolha.
     *
     * @return O objeto selecionado pelo utilizador.
     * @throws IllegalArgumentException Se o array estiver vazio.
     */
    public Tipo selecionar() throws IllegalArgumentException {
        // Verifica se o array está vazio.
        if (array.isEmpty()) {
            throw new IllegalArgumentException("Array vazio.");
        }
        // Verifica se há apenas um elemento no array.
        else if (array.size() == 1) {
            return array.get(0); // Corrigido para array.get(0)
        }
        // Lista as opções para o utilizador selecionar.
        for (int i = 1; i <= array.size(); i++) {
            System.out.printf("%2d - %s\n", i, array.get(i - 1).toString());
        }
        // Lê a opção escolhida pelo utilizador.
        int idx = Leitor.lerIntMinMax("Opção", 1, array.size()) - 1;
        return array.get(idx);
    }

    /**
     * Adiciona um objeto ao array.
     *
     * @param obj Objeto a ser adicionado.
     */
    public void adicionar(Tipo obj) {
        array.add(obj);
    }

    /**
     * Remove um objeto do array.
     *
     * @param obj Objeto a ser removido.
     */
    public void remover(Tipo obj) {
        array.remove(obj);
    }

    /**
     * Obtém o tamanho atual do array.
     *
     * @return Número de elementos no array.
     */
    public int getArraySize() {
        return array.size();
    }

    /**
     * Obtém o array atual.
     *
     * @return O array com os objetos geridos.
     */
    public ArrayList<Tipo> getArray() {
        return array;
    }
}
