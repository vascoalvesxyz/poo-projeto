package io;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Classe utilitária para leitura de dados do utilizador a partir da consola.
 * Contém métodos para validar e garantir o formato correto dos dados introduzidos.
 */
public class Leitor {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lê uma string inserida pelo utilizador.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return A string inserida pelo utilizador.
     */
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    /**
     * Lê uma descrição válida. Não permite o uso de vírgulas.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return A descrição válida inserida pelo utilizador.
     */
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

    /**
     * Lê e valida um nome. O nome deve começar com letra maiúscula e conter apenas letras e espaços.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return O nome válido inserido pelo utilizador.
     */
    public static String lerNome(String mensagem) {
        String str;
        boolean isValido = false;
        do {
            str = lerString(mensagem);
            if (validarNome(str)) {
                isValido = true;
            } else {
                System.out.println("Nome inválido. Por favor, insira um nome com caracteres alfabéticos e que comece com uma letra maiúscula.");
            }
        } while (!isValido);
        return str;
    }

    /**
     * Valida os Nomes inseridos pelo utilizador e no ficheiro de texto.
     * @return boolean
     */
    protected static boolean validarNome(String str) {
        return str.matches("^[A-ZÁÉÍÓÚÂÊÔÀÃÕÇ][a-záéíóúâêôàãõç]*([ ]([A-ZÁÉÍÓÚÂÊÔÀÃÕÇ][a-záéíóúâêôàãõç]*))*$");
    }


    /**
     * Lê e valida um número de contribuinte. Deve conter exatamente 9 dígitos.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return O contribuinte válido inserido pelo utilizador.
     */
    public static String lerContribuinte(String mensagem) {
        String str;
        boolean isValido = false;
        do {
            str = lerString(mensagem);
            if (validarContribuinte(str)) {
                isValido = true;
            } else {
                System.out.println("Contribuinte inválido. Por favor, insira um número com 9 dígitos.");
            }
        } while (!isValido);
        return str;
    }

    /**
     * Função que valida o contribuinte tanto no ficheiro de texto como input do utilizador.
     * @return boolean
     */
    protected static boolean validarContribuinte(String str) {
        return (str.length() == 9 && str.matches("^[0-9]{9}$"));
    }

    /**
     * Lê um número inteiro positivo.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return O número inteiro positivo introduzido.
     */
    public static int lerUInt(String mensagem) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            String entrada = lerString(mensagem);

            if (validarUint(entrada)) {
                numero = Integer.parseInt(entrada);
                valido = true;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
        return numero;
    }

    /**
     * Valida se o string inserido pelo utilizador corresponde a um número inteiro positivo.
     * @return boolean
     */
    protected static boolean validarUint(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * Lê um número decimal positivo.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @return O número decimal válido introduzido.
     */
    public static Double lerUDouble(String mensagem) {
        double numero = 0.0;
        boolean valido = false;

        while (!valido) {
            String entrada = lerString(mensagem);

            entrada = entrada.replace(',', '.');

            if (validarUDouble(entrada)) {
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

    /**
     * Valida o input de doubles unsigned.
     * @return boolean
     */
    protected static boolean validarUDouble(String str) {
        return str.matches("[0-9]+([,.][0-9]+)?");
    }

    /**
     * Lê um número inteiro dentro de um intervalo.
     *
     * @param mensagem Mensagem a ser exibida ao utilizador.
     * @param min      Valor mínimo permitido.
     * @param max      Valor máximo permitido.
     * @return O número inteiro válido dentro do intervalo.
     */
    public static int lerIntMinMax(String mensagem, int min, int max) {
        int res;
        mensagem = mensagem + String.format(" (%d-%d): ", min, max);
        boolean valido = false;
        do {
            res = lerUInt(mensagem);
            if (res >= min && res <= max) {
                valido = true;
            } else {
                System.out.println("Valor inválido. Por favor, insira um número dentro da gama permitida.");
            }
        } while (!valido);
        return res;
    }

    /**
     * Lê uma resposta booleana (sim/não) do utilizador.
     *
     * @param questao Pergunta a ser exibida ao utilizador.
     * @return {@code true} para "s" (sim) e {@code false} para "n" (não).
     */
    public static boolean lerBoolean(String questao) {
        System.out.println(questao + " (s/n)");

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

    /**
     * Imprime as opções disponíveis de um enum.
     *
     * @param valores Array de valores do enum.
     */
    public static void printEnum(Object[] valores) {
        System.out.println("Escolha uma das opções:");
        for (int i = 0; i < valores.length; i++) {
            System.out.printf("%d - %s%n", i + 1, valores[i].toString());
        }
    }

    /**
     * Lê uma opção selecionada de um enum.
     *
     * @param valoresEnum Array de valores do enum.
     * @return O índice da opção escolhida.
     */
    public static int lerEnum(Object[] valoresEnum) {
        printEnum(valoresEnum);
        int input = lerIntMinMax("Opção", 1, valoresEnum.length);
        return input - 1;
    }

    /**
     * Lê e valida uma data no formato "dd/mm/yyyy".
     *
     * @return Um objeto {@code Calendar} representando a data válida.
     */
    public static Calendar lerData() {
        while (true) {
            String input = lerString("Insira a Data (dd/mm/yyyy): ");
            Calendar cal = validarData(input);
            if (cal != null) {
                return cal;
            }
        }
    }

    /**
     * Valida uma data no formato "dd/mm/yyyy".
     *
     * @param data Data em string para validação.
     * @return Um objeto {@code Calendar} se a data for válida; caso contrário, {@code null}.
     */
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

    /**
     * Valida o formato da data "dd/mm/yyyy".
     *
     * @param data Data em string.
     * @throws IllegalArgumentException Se o formato for inválido.
     */
    private static void validarFormatoData(String data) {
        if (!data.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new IllegalArgumentException("Formato inválido! Use dd/mm/yyyy.");
        }
    }

    /**
     * Analisa e converte uma data em string para um objeto {@code Calendar}.
     *
     * @param data Data em string.
     * @return Um objeto {@code Calendar} representando a data.
     */
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

    /**
     * Verifica se a data está dentro de um intervalo válido (1900-2200).
     *
     * @param cal Objeto {@code Calendar} com a data.
     * @return {@code true} se a data for válida; caso contrário, {@code false}.
     */
    private static boolean validarVeracidadeData(Calendar cal) {
        int ano = cal.get(Calendar.YEAR);
        int ANO_MAX = 2200, ANO_MIN = 1900;
        return ano <= ANO_MAX && ano >= ANO_MIN;
    }
}
