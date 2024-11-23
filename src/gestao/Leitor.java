package gestao;

import java.util.Calendar;
import java.util.Scanner;

public abstract class Leitor {
    private final Scanner scanner;

    public Leitor() {
        scanner = new Scanner(System.in);
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public int lerInt(String mensagem) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            if (entrada.matches("-?[0-9]+")) {
                numero = Integer.parseInt(entrada);
                valido = true;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
        return numero;
    }

    public int lerIntMinMax(String mensagem, int min, int max) {
        int res;
        mensagem = mensagem + String.format(" (%d-%d): ", min, max);
        do {
            res = lerInt(mensagem);
        } while (res < min || res > max);
        return res;
    }

    public boolean lerBoolean(String questao) {
        System.out.println(questao + "(s/n)");

        String inputStr;
        do {
            inputStr = scanner.nextLine();
        } while (!inputStr.equalsIgnoreCase("s") && !inputStr.equalsIgnoreCase("n"));

        return inputStr.equalsIgnoreCase("s");
    }

    public Calendar lerData() {
        System.out.print("Insere a Data (dd/mm/YY)): ");
        String[] dados = scanner.next().split("/");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(dados[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(dados[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dados[2]));
        return cal;
    }

    public void printEnum(Object[] valores) {
        System.out.println("Escolha uma das opções:");
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%d - %s%n", i + 1, valores[i].toString());
        }
    }

    public int lerEnum(Object[] valoresEnum) {
        printEnum(valoresEnum);
        int input = lerIntMinMax("Opção", 1, valoresEnum.length);
        return input - 1;
    }

}
