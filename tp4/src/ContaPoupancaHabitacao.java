import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ContaPoupancaHabitacao extends Conta {
    private Date dataEntrada;
    private final SimpleDateFormat obj;
    public ContaPoupancaHabitacao( String data, String nome, String apelido, String cc) {
        super("0", data, nome, apelido, cc, "PoupancaHabitacao");

        obj = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dataEntrada = obj.parse(data);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        LocalDate.now();
    }

    @Override
    public boolean podeLevantar(Double montante) {
        boolean tempo = true;
        try {
            Date dataAux = obj.parse(LocalDate.now().toString());
            tempo = (dataAux.getTime() - dataEntrada.getTime()) / (1000*60*60*24L) <= 730;
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        boolean saldo = banco.getSaldo(this) >= montante;

        return tempo || saldo;
    }

    @Override
    public double juros() {
        if(banco.getSaldo(this)<1000) {
            return 0.9;
        }else {
            return 0.95;
        }
    }
}
