import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ContaPrazo extends Conta {
    private String data;
    private Date dataEntrada;
    private static final Banco banco = new Banco();
    public ContaPrazo(String data, String nome, String apelido, String cc) {
        super("0", data, nome, apelido, cc, "Prazo");
        this.data = data;

        SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dataEntrada = obj.parse(data);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        LocalDate.now();
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
