package gestao;

import produto.Cliente;
import produto.Fatura;

import java.util.ArrayList;
import java.util.Calendar;

public class GestorFaturas implements Gestor<Fatura> {

    private final ArrayList<Fatura> faturas;
    private final Cliente cliente;
    private final Leitor leitor;

    public GestorFaturas(Cliente cliente, Leitor leitor) {
        this.faturas = new ArrayList<>();
        this.cliente = cliente;
        this.leitor = leitor;
    }

    public Fatura procurarPorNumero(int codigo) {
        for (Fatura f : faturas)
            if (f.getId() == codigo)
                return f;
        return null;
    }

    @Override
    public void criarOuEditar() {
        int id = leitor.lerInt("Insira o ID da fatura: ");

        Fatura fatura = procurarPorNumero(id);
        if (fatura == null) {
            criar(id);
        } else if (leitor.lerBoolean("Fatura já existe, deseja editar?")) {
            editar(fatura);
        }
    }

    public void criar(int id) {
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
