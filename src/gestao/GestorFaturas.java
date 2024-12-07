package gestao;

import io.Leitor;
import produto.Cliente;
import produto.Fatura;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class GestorFaturas extends Gestor<Fatura> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private final Cliente cliente;

    public GestorFaturas() {
        super();
        this.cliente = new Cliente();
    }

    public GestorFaturas(Cliente cliente) {
        super(new ArrayList<>());
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
        Fatura fatura = new Fatura(id, cal, cliente);

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
        int input;
        do {
            System.out.println("""
                    1 - Editar ID.
                    2 - Editar Data.
                    0 - Sair.
                    """);
            input = leitor.lerIntMinMax("Opção: ", 0, 2);

            switch (input) {
                case 1 -> {
                    int novoId;
                    boolean terminado = false;
                    do {
                        novoId = leitor.lerInt("Novo id: ");
                        try {
                            procurarPorNumero(novoId);
                            System.out.println("Já existe uma fatura com esse ID. Por favor, insira outro.");
                        } catch (IllegalArgumentException e) {
                            fatura.setId(novoId);
                            terminado = true;
                        }
                    }
                    while (!terminado);
                }
                case 2 -> {
                    Calendar novaData = leitor.lerData();
                    fatura.setData(novaData);
                }
            }
        }
        while (input != 0);
    }

}
