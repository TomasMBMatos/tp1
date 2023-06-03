package logic;

public class ContaOrdem extends Conta {
    public ContaOrdem(String deposito, String nome, String apelido, String cc) {
        super(deposito, null, nome, apelido, cc, "Ordem");
    }

    @Override
    public boolean podeLevantar(Double montante) {
        return banco.getSaldo(this) >= montante;
    }

    @Override
    public double juros() {
        return 0.9;
    }
}
