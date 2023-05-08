import java.util.Random;

public class Conta {
    Random rng = new Random();
    private double saldo = 0;
    private double juros = 0.1;
    private final long numConta;

    public Conta() {
        numConta = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getJuros() {
        return juros;
    }

    public long getNumConta() {
        return numConta;
    }

    public void levantar(double montante) {
        if(montante < saldo) {
            saldo -= montante;
        }
    }

    public void juros() {
        if(saldo<1000) {
            juros = 0.1;
        }else {
            juros = 0.05;
        }
    }
}
