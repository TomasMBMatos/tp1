import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ContaMultibanco extends Conta {
        private String deposito;
        private Document doc;

        public ContaMultibanco(String deposito) {
            super();
            this.deposito = deposito;
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse("xml/movimentos_bancarios.xml");
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }

        private void createMovimento(Document d, String tipo,String data, String valor) {
            Element movimento = d.createElement("movimento");

            movimento.setAttribute("tipo", tipo);
            movimento.setAttribute("data", data);
            movimento.setAttribute("valor", valor);

            doc.appendChild(movimento);
        }

        public double levantar(double montante) {
            double res = super.levantar(montante);

            String data = LocalDate.now().toString();
            String valor = String.valueOf(montante);
            createMovimento(doc, "levantar", data, valor);

            return res;
        }
}