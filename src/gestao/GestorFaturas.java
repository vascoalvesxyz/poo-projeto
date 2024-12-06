package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Fatura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class GestorFaturas extends Gestor<Fatura> implements Serializable {
    private static final long serialVersionUID = 3L;

    private final Cliente cliente;

    public GestorFaturas() {
        super();
        this.cliente = new Cliente();
    }

    public GestorFaturas(Cliente cliente) {
        super(new ArrayList<Fatura>());
        this.cliente = cliente;
    }

    public Fatura procurarPorNumero(int codigo) throws IllegalArgumentException {
        for (Fatura f : array)
            if (f.getId() == codigo) {
                return f;
            }
        throw new IllegalArgumentException();
    }

    @Override
    public void criarOuEditar() {
        Leitor leitor = new Leitor();
        int id = leitor.lerInt("Insira o ID da fatura: ");

        try {
            Fatura fatura = procurarPorNumero(id);
            if (leitor.lerBoolean("Fatura já existe, deseja editar?")) {
                editar(fatura);
            }
        } catch (IllegalArgumentException e) {
            criar(id);
        }
    }

    @Override
    public void adicionar(Fatura fatura) {
        array.add(fatura);
    }

    public void criar(int id) {
        Leitor leitor = new Leitor();
        Calendar cal = leitor.lerData();
        Fatura fatura = new Fatura(id, cal, cliente, leitor);

        boolean adicionarProduto;
        do {
            fatura.getProdutos().criarOuEditar();
            adicionarProduto = leitor.lerBoolean("Deseja adicionar mais um produto? ");
        }
        while (adicionarProduto);
        adicionar(fatura);
    }

    public void editar(Fatura fatura) {
        Leitor leitor = new Leitor();
    }

}
