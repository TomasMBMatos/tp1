import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Banco {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Integer> numContas = new ArrayList<>();

    Document doc;

    public Banco() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse("tp1/Gestao Banco/src/xml/banco.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCliente(Cliente cliente) {

    }

    public boolean registar(String username, String password) {
        Element clientes = (Element) doc.getElementsByTagName("clientes").item(0);
        Element cliente = doc.createElement("cliente");
        cliente.setAttribute("user", username);
        cliente.setAttribute("password", password);

        clientes.appendChild(cliente);

        return true;
    }

    public boolean login(String username, String password) {
        NodeList clientes = doc.getElementsByTagName("cliente");
        for(int i=0;i<clientes.getLength();i++) {
            Element cliente = (Element) clientes.item(i);
            if(!cliente.getAttribute("user").equals(username)) {
                continue;
            }
            if(!cliente.getAttribute("password").equals(password)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean existeConta(long numConta) {
        for(Cliente cliente: clientes) {
            if(cliente.getConta().getNumConta() == numConta)
                return true;
        }
        return false;
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

    private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
