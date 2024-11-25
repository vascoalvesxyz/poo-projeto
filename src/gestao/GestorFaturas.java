package gestao;

import produto.Cliente;
import produto.Fatura;

import java.util.ArrayList;
import java.util.Calendar;

public class GestorFaturas extends Leitor implements Gestor<Fatura> {

    private final ArrayList<Fatura> faturas;
    private final Cliente cliente;

    public GestorFaturas(Cliente cliente) {
        this.faturas = new ArrayList<>();
        this.cliente = cliente;
    }

    public Fatura procurarPorNumero(int codigo) {
        for (Fatura f : faturas)
            if (f.getId() == codigo)
                return f;
        return null;
    }

    @Override
    public void criarOuEditar() {
        int id = lerInt("Insira o ID da fatura: ");

        Fatura fatura = procurarPorNumero(id);
        if (fatura == null) {
            criar(id);
        } else if (lerBoolean("Fatura já existe, deseja editar?")) {
            editar(fatura);
        }
    }

    public void criar(int id) {
        Calendar cal = lerData();
        Fatura fatura = new Fatura(id, cal, this.cliente);

        boolean adicionarProduto;
        do {
            fatura.getProdutos().criarOuEditar();
            adicionarProduto = lerBoolean("Deseja adicionar mais um produto? ");
        }
        while (adicionarProduto);
        adicionar(fatura);
    }

    public void editar(Fatura fatura) {
        //TODO
        System.out.println("Falta implementar");
    }

    @Override
    public void adicionar(Fatura fatura) {
        faturas.add(fatura);
    }

    @Override
    public void listar() {
        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura registada.");
        } else {
            for (Fatura fatura : this.faturas) {
                System.out.println(fatura.toString());
            }
        }
    }

}
