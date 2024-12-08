package io;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

public class Leitor implements Serializable {

    private static final Scanner scanner = new Scanner(System.in);

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static String lerDescricao(String mensagem) {
        String str;
        boolean isValido = false;
        do {
            str = lerString(mensagem);
            if (!str.contains(FicheiroIO.separador)) {
                isValido = true;
            } else {
                System.out.println("Descrição inválida. Por favor, insira uma descrição que não contenha vírgulas.");
            }
        } while (!isValido);
        return str;
    }

    public static String lerNome(String mensagem) {
        String str;
        boolean isValido = false;
        do {
            str = lerString(mensagem);
            // REGEX: Letra Maiuscula, Combinação de Letras e Espaço
            if (str.matches("^[A-ZÁÉÍÓÚÂÊÔÀÃÕÇ][a-záéíóúâêôàãõç ]*$")) {
                isValido = true;
            } else {
                System.out.println("Nome inválido. Por favor, insira um nome com caracteres alfabéticos e que comece com uma letra maiúscula.");
            }
        } while (!isValido);
        return str;
    }

    public static String lerContribuinte(String mensagem) {
        String str;
        boolean isValido = false;
        do {
            str = lerString(mensagem);
            // REGEX: Inicio da linha, Número com 9 digitos, fim da linha
            if (str.length() == 9 && str.matches("^[0-9]{9}$")) {
                isValido = true;
            } else {
                System.out.println("Contribuinte inválido. Por favor, insira um número com 9 dígitos.");
            }
        } while (!isValido);
        return str;
    }

    public static int lerUInt(String mensagem) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            String entrada = lerString(mensagem);

            if (entrada.matches("[0-9]+")) {
                numero = Integer.parseInt(entrada);
                valido = true;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
        return numero;
    }

    public static Double lerUDouble(String mensagem) {
        Double numero = 0.0;
        boolean valido = false;

        while (!valido) {
            String entrada = lerString(mensagem);

            entrada = entrada.replace(',', '.');

            /* REGEX: Número com pelo menos 1 digito,
            seguido, ou não, de um ponto e pelo menos 1 digito
             */
            if (entrada.matches("[0-9]+([,.][0-9]+)?")) {
                try {
                    numero = Double.parseDouble(entrada);
                    valido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, insira um número válido.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
            }
        }
        return numero;
    }

    public static int lerIntMinMax(String mensagem, int min, int max) {
        int res;
        mensagem = mensagem + String.format(" (%d-%d): ", min, max);
        boolean valido = false;
        do {
            res = lerUInt(mensagem);
            if (res >= min && res <= max) {
                valido = true;
            } else {
                System.out.println("Valor inválido. Por favor, insira um número dentro gama permitida.");
            }
        } while (!valido);
        return res;
    }

    public static boolean lerBoolean(String questao) {
        System.out.println(questao + "(s/n)");

        String inputStr;
        boolean valido = false;
        do {
            inputStr = scanner.nextLine();
            if (inputStr.equalsIgnoreCase("s") || inputStr.equalsIgnoreCase("n")) {
                valido = true;
            } else {
                System.out.println("Opção inválida. Por favor, insira um \"s\" ou um \"n\".");
            }
        } while (!valido);

        return inputStr.equalsIgnoreCase("s");
    }

    public static void printEnum(Object[] valores) {
        System.out.println("Escolha uma das opções:");
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%d - %s%n", i + 1, valores[i].toString());
        }
    }

    public static int lerEnum(Object[] valoresEnum) {
        printEnum(valoresEnum);
        int input = lerIntMinMax("Opção", 1, valoresEnum.length);
        return input - 1;
    }

    public static Calendar lerData() {
        while (true) {
            String input = lerString("Insere a Data (dd/mm/yyyy): ");
            Calendar cal = validarData(input);
            if (cal != null) {
                return cal;
            }
        }
    }

    public static Calendar validarData(String data) {
        try {
            validarFormatoData(data);
            Calendar cal = analisarData(data);
            if (validarVeracidadeData(cal)) {
                return cal;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Data inválida! Tente novamente.");
        }
        return null;
    }

    private static void validarFormatoData(String data) {
        if (!data.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new IllegalArgumentException("Formato inválido! Use dd/mm/yyyy.");
        }
    }

    private static Calendar analisarData(String data) {
        String[] parts = data.split("/");

        int dia = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]) - 1;
        int ano = Integer.parseInt(parts[2]);

        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DAY_OF_MONTH, dia);

        cal.getTime();

        return cal;
    }

    private static boolean validarVeracidadeData(Calendar cal) {
        int ano = cal.get(Calendar.YEAR);
        int ANO_MAX = 2200, ANO_MIN = 1900;
        return ano <= ANO_MAX && ano >= ANO_MIN;
    }
}