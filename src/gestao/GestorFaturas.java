package gestao;

import produto.Cliente;
import produto.Fatura;
import produto.Produto;

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
        for (Fatura f: faturas)
            if (f.getId() == codigo)
                return f;
        return null;
    }

    @Override
    public Fatura criar() {
        int id = lerInt("Insere o ID: ");

        Fatura procuraPorId = procurarPorNumero(id);
        if (procuraPorId != null) {
            boolean desejaEditar = lerBoolean("Fatura já existe, deseja editar? ");
            if (desejaEditar) {
                editar(procuraPorId);
            }
            return procuraPorId;
        }

        Calendar cal = lerData();
        Fatura fatura = new Fatura(id, cal, this.cliente);

        boolean adicionarProduto;
        do {
            Produto novoProduto = fatura.getProdutos().criar();
            fatura.getProdutos().adicionar(novoProduto);
            adicionarProduto = lerBoolean("Deseja adicionar mais um produto? ");
        } while (adicionarProduto);
        adicionar(fatura);

        return fatura;
    }

    @Override
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
