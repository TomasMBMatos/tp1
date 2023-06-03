package logic;

public class ContaMultibanco extends Conta {
        public ContaMultibanco(String deposito, String nome, String apelido, String cc) {
            super(deposito, null, nome, apelido, cc, "Multibanco");
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
