package utils;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLReadWrite extends Banco {
    public static OutputStream bancoOutput;
    public static FileOutputStream movimentosOutput;

    static {
        try {
            bancoOutput = new FileOutputStream("Gestao Banco/src/xml/banco.xml");

            movimentosOutput = new FileOutputStream("Gestao Banco/src/xml/movimentos_bancarios.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeXml(Document doc, File output) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");

            if (doc.getXmlEncoding() != null)
                transformer.setOutputProperty(OutputKeys.ENCODING, doc.getXmlEncoding());
            else
                transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            // pretty print XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}