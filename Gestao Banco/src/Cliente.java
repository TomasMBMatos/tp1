public class Cliente {
    private String nome, apelido, cc;
    private Conta conta;

    public Cliente(String nome, String apelido, String cc, Conta conta) {
        this.nome = nome;
        this.apelido = apelido;
        this.cc = cc;
        this.conta = conta;
    }

    public Conta getConta() {
        return conta;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }
    public String getCC() {
        return cc;
    }
}
