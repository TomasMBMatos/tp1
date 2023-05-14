import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ContaPoupancaHabitacao extends Conta {
    private double juros = 0.1;
    Date dataEntrada;
    public ContaPoupancaHabitacao(String deposito, String data) {
        super();
        this.setSaldo(Double.parseDouble(deposito));

        obj = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dataEntrada = obj.parse(data);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        LocalDate.now();
    }

    public void setSaldo(double saldo) {
        juros();
        super.depositar(saldo * juros);
    }

    public boolean podeLevantar() {
        try {
            Date dataAux = obj.parse(LocalDate.now().toString());
            return dataAux.getTime() - dataEntrada.getTime() <= 0;
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            return false;
        }
    }

    public double levantar(double montante) {
        if(podeLevantar()) {
            super.levantar(montante);
        }
        return 0;
    }

    public void juros() {
        if(getSaldo()<1000) {
            juros = 0.1;
        }else {
            juros = 0.05;
        }
    }

    public double getJuros() {
        return juros;
    }
}
