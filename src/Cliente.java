public class Cliente {

    public enum Localizacao {
        PORTUGAL,
        MADEIRA,
        AÇORES
    }

    private String nome;
    private int contribuinte;
    private Localizacao localizacao;

    public Cliente(
            int nome,
            int contribuinte,
            int localizacao 
    ) {
        this.nome = nome;
        this.contribuinte = contribuinte;
        this.localizacao = localizacao;
    }
    
    
}
