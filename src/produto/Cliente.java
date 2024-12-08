package produto;

import gestao.GestorFaturas;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe que representa um cliente. Contém informações do cliente, como nome, contribuinte, localização
 * e as faturas associadas.
 */
public class Cliente implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * Nome do cliente.
     */
    private String nome;

    /**
     * Número de contribuinte do cliente.
     */
    private String contribuinte;

    /**
     * Localização do cliente, podendo ser Portugal, Madeira ou Açores.
     */
    private Localizacao localizacao;

    /**
     * Gestor de faturas associado ao cliente.
     */
    private final GestorFaturas faturas;

    /**
     * Construtor padrão que inicializa os atributos com valores padrão.
     */
    public Cliente() {
        this.nome = "";
        this.contribuinte = "";
        this.localizacao = Localizacao.PORTUGAL;
        this.faturas = new GestorFaturas();
    }

    /**
     * Construtor que inicializa o cliente com os dados fornecidos.
     *
     * @param nome         Nome do cliente.
     * @param contribuinte Número de contribuinte do cliente.
     * @param localizacao  Localização do cliente.
     */
    public Cliente(String nome, String contribuinte, Localizacao localizacao) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
        this.faturas = new GestorFaturas(this);
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return Nome do cliente.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome Nome a ser definido.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o gestor de faturas associado ao cliente.
     *
     * @return Gestor de faturas.
     */
    public GestorFaturas getFaturas() {
        return this.faturas;
    }

    /**
     * Obtém a localização do cliente.
     *
     * @return Localização do cliente.
     */
    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    /**
     * Define a localização do cliente.
     *
     * @param localizacao Localização a ser definida.
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Define o número de contribuinte do cliente.
     *
     * @param contribuinte Número de contribuinte a ser definido.
     */
    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    /**
     * Converte as informações do cliente para uma string formatada.
     *
     * @return Representação em string do cliente.
     */
    @Override
    public String toString() {
        return String.format("Nome: %s, Contribuinte: %s, Localização: %s", nome, contribuinte, localizacao);
    }

    /**
     * Converte as informações do cliente para um formato adequado para escrita em ficheiros.
     *
     * @return Representação em string para ficheiro.
     */
    public String toFile() {
        return String.format("CLIENTE;%d;%s;%s;%s\n", getFaturas().getArraySize(), nome, contribuinte, localizacao);
    }

    /**
     * Enum que representa as localizações possíveis de um cliente.
     */
    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        ACORES
    }
}
