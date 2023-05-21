import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.XMLReadWrite;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

public abstract class Conta {
    final Banco banco = new Banco();
    private long numConta;

    private final String nome, apelido, cc, deposito, data;

    private final String tipo;

    public Conta(String deposito, String data,  String nome, String apelido, String cc, String tipo) {
        this.nome = nome;
        this.apelido = apelido;
        this.cc = cc;
        this.deposito = deposito;
        this.tipo = tipo;
        this.data = data;
        numConta = banco.createNumConta();
    }
    /* --------------- GETTERS AND SETTERS --------------- */
    public String getNome() {
        return nome;
    }
    public String getApelido() {
        return apelido;
    }
    public String getCc() {
        return cc;
    }
    public String getTipo() {
        return tipo;
    }
    public String getDeposito() {
        return deposito;
    }
    public long getNumConta() {
        return numConta;
    }
    public String getData() {
        return data;
    }
    public void setNumConta(long num) {this.numConta = num;}

    /* --------------------------------------------------- */



    public Document depositar(double montante, Document doc) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element conta = (Element) contas.item(i);
            if(Long.parseLong(conta.getElementsByTagName("numero").item(0).getTextContent()) == this.numConta) {
                String novoSaldo = String.valueOf((banco.getSaldo(this)+montante));
                conta.getElementsByTagName("saldo").item(0).setTextContent(novoSaldo);
            }
        }
        return doc;
    }

    public Document levantar(double montante, Document doc) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element conta = (Element) contas.item(i);
            if(Long.parseLong(conta.getElementsByTagName("numero").item(0).getTextContent()) == this.numConta) {
                String novoSaldo = String.valueOf((banco.getSaldo(this)-montante));
                conta.getElementsByTagName("saldo").item(0).setTextContent(novoSaldo);
            }
        }
        return doc;
    }

    public abstract boolean podeLevantar(Double montante);

    public abstract double juros();

    public String toString() {
        return String.format("%s %s, Tipo: %s", nome, apelido, tipo);
    }
}
