package io;

import java.util.Calendar;
import java.util.Scanner;

public class Leitor {
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

    // TODO change to try catch logic
    public int lerIntMinMax(String mensagem, int min, int max) {
        int res;
        mensagem = mensagem + String.format(" (%d-%d): ", min, max);
        do {
            res = lerInt(mensagem);
        }
        while (res < min || res > max);
        return res;
    }

    public boolean lerBoolean(String questao) {
        System.out.println(questao + "(s/n)");

        String inputStr;
        do {
            inputStr = scanner.nextLine();
        }
        while (!inputStr.equalsIgnoreCase("s") && !inputStr.equalsIgnoreCase("n"));

        return inputStr.equalsIgnoreCase("s");
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

    public Calendar lerData() {
        while (true) {
            try {
                String input = lerString("Insere a Data (dd/mm/yyyy): ");
                validarFormatoData(input);
                Calendar cal = analisarData(input);
                if (validarVeracidadeData(cal)) {
                    return cal;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Data inválida! Tente novamente.");
            }
        }
    }

    private void validarFormatoData(String data) {
        if (!data.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new IllegalArgumentException("Formato inválido! Use dd/mm/yyyy.");
        }
    }

    // Method to parse the date into a Calendar object
    private Calendar analisarData(String data) {
        String[] parts = data.split("/");

        int dia = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]) - 1; // Calendar months are 0-indexed
        int ano = Integer.parseInt(parts[2]);

        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DAY_OF_MONTH, dia);

        cal.getTime();

        return cal;
    }

    private boolean validarVeracidadeData(Calendar cal) {
        int ano = cal.get(Calendar.YEAR);
        int ANO_MAX = 2200, ANO_MIN = 1900;
        return ano <= ANO_MAX && ano >= ANO_MIN;
    }
}
