import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import utils.XMLReadWrite;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
