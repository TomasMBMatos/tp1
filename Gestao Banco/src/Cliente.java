import java.util.ArrayList;

public class Cliente {
    private String nome, apelido, cc;
    private Conta conta;
    private ArrayList<Conta> contas;

    public Cliente(String nome, String apelido, String cc, Conta conta) {
        this.nome = nome;
        this.apelido = apelido;
        this.cc = cc;
        this.conta = conta;
        contas.add(conta);
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
    public ArrayList<Conta> getContas() {
        return contas;
    }
}
