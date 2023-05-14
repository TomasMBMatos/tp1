import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Conta {
    Random rng = new Random();

    private final long numConta;

    Document doc;

    SimpleDateFormat obj;

    public Conta() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse("xml/banco.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        numConta = createNumConta();
    }

    private long createNumConta() {
        NodeList numeros = doc.getElementsByTagName("numero");
        long novoNumero = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
        for(int i=0; i<numeros.getLength();i++) {
            Element numero = (Element) numeros.item(i);
            if(Long.parseLong(numero.getTextContent()) == novoNumero) {
                novoNumero = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
            }
        }
        Element ENumeroConta = doc.getElementById("numeroConta");
        Element ENumero = doc.createElement("numero");
        ENumero.setTextContent(String.valueOf(novoNumero));
        ENumeroConta.appendChild(ENumero);

        return novoNumero;
    }

    public double getSaldo() {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0;i<contas.getLength();i++) {
            Element conta = (Element) contas.item(i);
            if(Long.parseLong(conta.getAttribute("numero")) == this.numConta) {
                return Double.parseDouble(conta.getAttribute("saldo"));
            }
        }
        return 0;
    }

    public long getNumConta() {
        return numConta;
    }

    public double levantar(double montante) {
        if(montante < getSaldo()) {
            return levantar(montante, doc);
        }
        return 0;
    }

    public double depositar(double montante) {
        return depositar(montante, doc);
    }

    public double depositar(double montante, Document doc) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element conta = (Element) contas.item(i);
            if(Long.parseLong(conta.getAttribute("numero")) == this.numConta) {
                String novoSaldo = String.valueOf((getSaldo()+montante));
                conta.setAttribute("saldo", novoSaldo);
                return montante;
            }
        }
        return 0;
    }

    public double levantar(double montante, Document doc) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element conta = (Element) contas.item(i);
            if(Long.parseLong(conta.getAttribute("numero")) == this.numConta) {
                String novoSaldo = String.valueOf((getSaldo()-montante));
                conta.setAttribute("saldo", novoSaldo);
                return montante;
            }
        }
        return 0;
    }
}
