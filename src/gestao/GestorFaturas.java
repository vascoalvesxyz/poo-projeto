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

    public Fatura procurarPorNumero(int codigo) {
        for (Fatura f : array)
            if (f.getId() == codigo) {
                return f;
            }
        return null;
    }

    @Override
    public void criarOuEditar() {
        Leitor leitor = new Leitor();
        int id = leitor.lerInt("Insira o ID da fatura: ");

        Fatura fatura = procurarPorNumero(id);
        if (fatura == null) {
            criar(id);
        } else if (leitor.lerBoolean("Fatura já existe, deseja editar?")) {
            editar(fatura);
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
        //TODO
        Leitor leitor = new Leitor();
        System.out.println("Falta implementar");
    }

}
