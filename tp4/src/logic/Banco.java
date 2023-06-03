package logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.XMLReadWrite;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Banco {
    Random rng = new Random();
    ArrayList<Cliente> clientes = new ArrayList<>();

    Document doc;
    File xmlFile;

    public Banco() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmlFile = new File("tp4/src/xml/banco.xml");
            this.doc = builder.parse(xmlFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }



    public void registar(Cliente cliente) {
        registar(cliente.username, cliente.password);
        clientes.add(cliente);
    }

    private void registar(String username, String password) {
        Element clientes = (Element) doc.getElementsByTagName("clientes").item(0);
        Element cliente = doc.createElement("cliente");
        cliente.setAttribute("user", username);
        cliente.setAttribute("password", password);

        clientes.appendChild(cliente);
        XMLReadWrite.writeXml(doc, xmlFile);
    }

    public boolean login(Cliente cliente) {
        return login(cliente.username, cliente.password);
    }

    private boolean login(String username, String password) {
        NodeList clientes = doc.getElementsByTagName("cliente");
        for(int i=0;i<clientes.getLength();i++) {
            Element cliente = (Element) clientes.item(i);
            if(cliente.getAttribute("user").equals(username) &&
                    cliente.getAttribute("password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void addConta(Cliente cliente, Conta conta) {
        NodeList users = this.doc.getElementsByTagName("cliente");
        for(int i=0; i<users.getLength();i++) {
            Element user = (Element) users.item(i);
            if(user.getAttribute("user").equals(cliente.username)) {
                Element eConta = doc.createElement("conta");
                eConta.setAttribute("nome", conta.getNome());
                eConta.setAttribute("apelido", conta.getApelido());
                eConta.setAttribute("tipo", conta.getTipo());
                if(conta.getTipo().equals("PoupancaHabitacao") || conta.getTipo().equals("Prazo")) {
                    Element data = doc.createElement("data");
                    data.setTextContent(conta.getData());
                    eConta.appendChild(data);
                }
                Element saldo = doc.createElement("saldo");
                saldo.setTextContent(conta.getDeposito());
                eConta.appendChild(saldo);

                Element numero = doc.createElement("numero");
                numero.setTextContent(String.valueOf(conta.getNumConta()));
                eConta.appendChild(numero);

                Element cc = doc.createElement("cc");
                cc.setTextContent(conta.getCc());
                eConta.appendChild(cc);

                Element movimentos = doc.createElement("movimentos");
                eConta.appendChild(movimentos);

                user.appendChild(eConta);
            }
        }
        XMLReadWrite.writeXml(this.doc, xmlFile);
    }

    public Conta removeConta(Conta conta) {
        NodeList contas = this.doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element eConta = (Element) contas.item(i);
            if(eConta.getElementsByTagName("numero").item(0).getTextContent().equals(String.valueOf(conta.getNumConta()))) {
                eConta.getParentNode().removeChild(eConta);
            }
        }
        XMLReadWrite.writeXml(this.doc, xmlFile);
        return null;
    }

    private void createMovimento(String tipo,String data, String valor, Conta conta) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0; i<contas.getLength();i++) {
            Element conta2 = (Element) contas.item(i);
            if (Long.parseLong(conta2.getElementsByTagName("numero").item(0).getTextContent()) == conta.getNumConta()) {
                Element movimentos = (Element) conta2.getElementsByTagName("movimentos").item(0);
                Element movimento = doc.createElement("movimento");

                movimento.setAttribute("tipo", tipo);
                movimento.setAttribute("data", data);
                movimento.setAttribute("valor", valor);

                movimentos.appendChild(movimento);
            }
        }
    }

    public boolean levantar(Double montante, Conta conta) {
        if(!conta.podeLevantar(montante)) {
            return false;
        }
        Document levantamento = conta.levantar(montante, doc);
        if(Objects.equals(conta.getTipo(), "Levantamento") || Objects.equals(conta.getTipo(), "Ordem")) {
            createMovimento(conta.getTipo(), LocalDate.now().toString(), String.valueOf(montante), conta);
        }
        XMLReadWrite.writeXml(levantamento, xmlFile);
        return true;
    }

    public void depositar(Double montante, Conta conta) {
        conta.depositar(montante*conta.juros(), doc);
        if(Objects.equals(conta.getTipo(), "Deposito") || Objects.equals(conta.getTipo(), "Ordem")) {
            createMovimento(conta.getTipo(), LocalDate.now().toString(), String.valueOf(montante), conta);
        }
        XMLReadWrite.writeXml(doc, xmlFile);
    }

    private boolean existeConta(long numConta) {
        for(Cliente cliente: clientes) {
            if(cliente.getConta().getNumConta() == numConta)
                return true;
        }
        return false;
    }

    public Conta getConta(Cliente cliente, int index) {
        Conta aux = null;
        NodeList clientes = doc.getElementsByTagName("cliente");
        for(int i=0;i< clientes.getLength();i++) {
            Element clt = (Element) clientes.item(i);
            if(cliente.username.equals(clt.getAttribute("user"))) {
                Element c = (Element) clientes.item(i);
                c = (Element) c.getElementsByTagName("conta").item(index);
                if(c == null) return null;
                // String deposito, String username, String nome, String apelido, String cc, String tipo
                String deposito = c.getElementsByTagName("saldo").item(0).getTextContent();
                String nome = c.getAttribute("nome");
                String apelido = c.getAttribute("apelido");
                String cc = c.getElementsByTagName("cc").item(0).getTextContent();
                String tipo = c.getAttribute("tipo");
                String numero = c.getElementsByTagName("numero").item(0).getTextContent();


                switch (tipo) {
                    case "PoupancaHabitacao" -> {
                        String data = c.getElementsByTagName("data").item(0).getTextContent();
                        aux = new ContaPoupancaHabitacao(data, nome, apelido, cc);
                        aux.setNumConta(Long.parseLong(numero));
                        return aux;
                    }
                    case "Ordem" -> {
                        aux = new ContaOrdem(deposito, nome, apelido, cc);
                        aux.setNumConta(Long.parseLong(numero));
                        return aux;
                    }
                    case "Multibanco" -> {
                        aux = new ContaMultibanco(deposito, nome, apelido, cc);
                        aux.setNumConta(Long.parseLong(numero));
                        return aux;
                    }
                    case "Prazo" -> {
                        String data = c.getElementsByTagName("data").item(0).getTextContent();
                        aux = new ContaPrazo(data, nome, apelido, cc);
                        aux.setNumConta(Long.parseLong(numero));
                        return aux;
                    }
                }
            }
        }
        return aux;
    }

    public Cliente getCliente(long numConta) {
        for(Cliente cliente: clientes) {
            if(cliente.getConta().getNumConta() == numConta) {
                return cliente;
            }
        }
        return null;
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public double getSaldo(Conta conta) {
        NodeList contas = doc.getElementsByTagName("conta");
        for(int i=0;i<contas.getLength();i++) {
            Element cnt = (Element) contas.item(i);
            if(Long.parseLong(cnt.getElementsByTagName("numero").item(0).getTextContent()) == conta.getNumConta()) {
                return Double.parseDouble(cnt.getElementsByTagName("saldo").item(0).getTextContent());
            }
        }
        return 0;
    }

    public double getSaldoTotal(Cliente cliente) {
        double saldoTotal = 0;
        NodeList contas = getContas(cliente);
        for(int i=0;i<contas.getLength();i++) {
            Element cnt = (Element) contas.item(i);
            String saldo = cnt.getElementsByTagName("saldo").item(0).getTextContent();
            saldoTotal += Double.parseDouble(saldo);
        }
        return saldoTotal;
    }

    public long createNumConta() {
        NodeList numeros = doc.getElementsByTagName("numero");
        long novoNumero = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
        for(int i=0; i<numeros.getLength();i++) {
            Element numero = (Element) numeros.item(i);
            if(Long.parseLong(numero.getTextContent()) == novoNumero) {
                novoNumero = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
            }
        }
        return novoNumero;
    }

    public NodeList getContas(Cliente cliente) {
        NodeList clientes = doc.getElementsByTagName("cliente");
        for(int i=0;i<clientes.getLength();i++) {
            Element clt = (Element) clientes.item(i);
            if(cliente.username.equals(clt.getAttribute("user"))) {
                return ((Element) clientes.item(i)).getElementsByTagName("conta");
            }
        }
        return null;
    }

    public ArrayList<String> getContasArray(Cliente cliente) {
        ArrayList<String> nomesConta = new ArrayList<>();
        NodeList contas = getContas(cliente);
        for(int i=0;i<contas.getLength();i++) {
            Element conta = (Element) contas.item(0);
            nomesConta.add(conta.getAttribute("nome"));
        }
        return nomesConta;
    }
}
